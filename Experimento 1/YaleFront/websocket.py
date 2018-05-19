import json
import threading
from threading import Lock
from flask import Flask, render_template
from flask_socketio import SocketIO, emit
from kafka import KafkaConsumer
import paho.mqtt.client as mqtt

# Set this variable to "threading", "eventlet" or "gevent" to test the
async_mode = None

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_thermalcomfort'
socketio = SocketIO(app, async_mode=async_mode)
thread = None
thread_lock = Lock()


# Ruta del dashboard
@app.route('/')
def index():
    return render_template('index_ws.html', async_mode=socketio.async_mode)

def on_message(client, userdata, message):
    mensaje = message.payload.decode('utf-8')
    socketio.emit('mensajes', str(mensaje), namespace='/thermalcomfort')
    print("MENSAJE RECIBIDO: "+ mensaje)

broker_address="172.24.42.52"

# Consumidor del topic de Kafka "alta.piso1.local1". Cada valor recibido se envía a través del websocket.
def background_thread_websocket():
    client1 = mqtt.Client("Cliente de alarmas")
    #client2 = mqtt.Client("Cliente de fallos")
    client1.on_message = on_message
    #client2.on_message = on_message
    client1.connect(broker_address, 8083)
    #client2.connect(broker_address, 8083)
    client1.subscribe("UnidadResidencial/Inmueble/Alarmas")
    client1.subscribe("UnidadResidencial/Inmueble/Fallos")
    client1.loop_forever()
    #client2.loop_forever()
    #consumer = KafkaConsumer('UnidadResidencial.Inmueble.Alarmas', group_id='temperature', bootstrap_servers=['localhost:8090'])
    #for message in consumer:
    #    #json_data = json.loads(message.value.decode('utf-8'))
    #    #sensetime = json_data['sensetime']
    #    #sense = json_data['temperature']
    #    alarma= message.value.decode('utf-8')

    #    #payload = {
    #    #    'time': sensetime,
    #    #    'value': sense['data']
    #    #}
    #    socketio.emit('mensajes', str(alarma),
                      #namespace='/thermalcomfort')


    #consumer2 = KafkaConsumer('UnidadResidencial.Inmueble.Fallos', group_id='temperature', bootstrap_servers=['localhost:8090'])
    #for message in consumer2:
    #    #json_data = json.loads(message.value.decode('utf-8'))
    #    #sensetime = json_data['sensetime']
    #    #sense = json_data['temperature']
    #    fallo = message.value.decode('utf-8')

    #    #payload = {
    #    #    'time': sensetime,
    #    #    'value': sense['data']
    #    #}
    #    socketio.emit('mensajes', str(fallo),
    #                      namespace='/thermalcomfort')


# Rutina que se ejecuta cada vez que se conecta un cliente de websocket e inicia el conmunidor de Kafka
@socketio.on('connect', namespace='/thermalcomfort')
def test_connect():
    global thread
    with thread_lock:
        if thread is None:
            thread = socketio.start_background_task(target=background_thread_websocket)
    emit('mensajes', "Connected!!!")
    print("CONECTADO")


# Iniciar el servicio en el puerto 8086
if __name__ == '__main__':
    socketio.run(app, host='0.0.0.0', port=8086, debug=True)