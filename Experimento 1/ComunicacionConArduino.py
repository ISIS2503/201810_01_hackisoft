from flask import Flask, app, request, make_response
from flask_mqtt import Mqtt
from werkzeug.exceptions import abort, BadRequest

localhost = "172.24.41.52"
app = Flask(__name__)
app.config['MQTT_BROKER_URL'] = "172.24.42.52"
app.config['MQTT_BROKER_PORT'] = 8083
mqtt = Mqtt(app)


#Codigo que se ejecuta cuando un mensaje llega por mqtt
def on_message(client, userdata, message):
    print("message received: " ,str(message.payload.decode("utf-8")))
    print("message topic: " ,message.topic)
    print("____________________________")

#def enviarNuevoCodigo(codigo):
#    client.loop_start()
#    client.publish("UnidadResidencial/Inmueble/Configuracion", codigo)
#    client.loop_stop()

#def enviarActualizacion(codigo1, codigo2):
#    client.loop_start()
#    client.publish("UnidadResidencial/Inmueble/Actualizar", codigo1+","+codigo2)
#    client.loop_stop()

#def eliminarCodigo(codigo):
#    client.loop_start()
#    client.publish("UnidadResidencial/Inmueble/EliminarCodigo", codigo)
#    client.loop_stop()

#def eliminarCodigos():
#    client.loop_start()
#    client.publish("UnidadResidencial/Inmueble/EliminarCodigos", "true")
#    client.loop_stop()

#def compararCodigos(codigo):
#    client.loop_start()
#    client.publish("UnidadResidencial/Inmueble/Comparar", codigo)
#    client.loop_stop()


#print("Creando cliente")
#Creacion de un cliente para procesar lo referente a mqtt
#client = mqtt.Client("cliente1")
#client2= mqtt.Client("cliente2")
#Asignacion de la instruccion al cliente
#client.on_message=on_message
#client2.on_message=on_message
#print("Conectando al broker")
#client.subscribe("UnidadResidencial/Inmueble/NuevoCodigo")
#El cliente se conecta a mi maquina que corre mosquitto (172.24.42.52)
#client.connect(localhost)

@app.route('/nuevocodigo/<string:codigo>', methods=['POST'])
def post_nuevocodigo(codigo):
    m = list(codigo)
    if(len(m) != 4):
        return make_response("El código debe ser de 4 dígitos!", 400)
    if request.method == 'POST':
        mqtt.publish("UnidadResidencial/Inmueble/Configuracion", "crear:" + codigo)
        return "Código añadido!"

@app.route('/actualizarcodigo/<string:codigo1>/<string:codigo2>' , methods=['PUT'])
def put_actualizacodigo(codigo1,codigo2):
    m = list(codigo1)
    n = list(codigo2)
    if (len(m) != 4 or len(n) != 4):
        return make_response("El código debe ser de 4 dígitos!", 400)
    if request.method == 'PUT':
        mqtt.publish("UnidadResidencial/Inmueble/Configuracion", "actualizar:"+codigo1+","+codigo2)
        return "Código actualizado!"

@app.route('/eliminarcodigo/<string:codigo>', methods=['DELETE'])
def delete_eliminarcodigo(codigo):
    m = list(codigo)
    if (len(m) != 4):
        return make_response("El código debe ser de 4 dígitos!", 400)
    if request.method == 'DELETE':
        mqtt.publish("UnidadResidencial/Inmueble/Configuracion", "eliminar:"+codigo)
        return "Código eliminado!"

@app.route('/eliminarloscodigos/', methods=['DELETE'])
def delete_eliminartodosloscodigos():
    if request.method == 'DELETE':
        mqtt.publish("UnidadResidencial/Inmueble/Configuracion", "eliminar:todo")
        return "Todos los códigos han sido eliminados!"

if __name__ == '__main__':
    app.run(debug=True)
    print("CONECTADO")


#@app.route('compararcodigos/<String:codigo>', methods=['GET'])
#def get_compararcodigos(codigo):
#    if request.method == 'GET':
#        compararCodigos(codigo)
#        return



#client2.connect(localhost)
#Iniciar un loop corriente
#client.loop_start()
#client2.loop_start()
#client.loop_forever()
#Se suscribe el cliente principal al topico para escuchar los mensajes que llegan
#print("Suscribiendo al topico")
#client.subscribe("alta/piso1/local1")
#client.subscribe("UnidadResidencial/Inmueble/Alarmas")
#client.subscribe("UnidadResidencial/Inmueble/Configuracion")

#Prueba con el mismo cliente para publicar un mensaje y comprobar impresion
#print("Publicando en el topico")
#client.publish("alta/piso1/local1", "TEST")
#client.publish("UnidadResidencial/Inmueble/Alarmas", "prueba de alarma!")
#client.publish("UnidadResidencial/Inmueble/Configuracion", "prueba de alarma!")
#client.publish("UnidadResidencial/Inmueble/NuevaAlarma", c)
#Loop para que el script corra indefinidamente e imprima todos los mensajes que lleguen al topic
#client.loop_stop()
#Prueba para ver que se impriman los mensajes cada vez que alguien publica un mensaje en el topic
#client2.loop_start()
#for i in range(0, 5):
#   client2.publish("alta/piso1/local1", "TEST")
#   time.sleep(2)
#client2.loop_stop()
#Instruccion para detener controladamente el loop
#client.loop_stop()



