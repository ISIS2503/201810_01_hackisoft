import paho.mqtt.client as mqtt
from datetime import datetime
import time
import schedule

horamensaje = 0
def on_message(client, userdata, message):
        #print("message received " ,str(message.payload.decode("utf-8")))
        #print("message topic=",message.topic)
    #print("message qos=",message.qos)
    #print("message retain flag=",message.retain)
        #print("________________")
        mensaje = str(message.payload.decode("utf-8"))
        print(mensaje)
        if(mensaje != "check"):
            global horamensaje
        #tiempo1 = str(datetime.date(datetime.now()))
            horamensaje = datetime.now().second
        else:
            check()

def check():
    #tiempo2 = str(datetime.date(datetime.now()))
    horaactual = datetime.now().second
    newTime = horaactual-horamensaje
    if(int(newTime) > 3):
        client.publish("UnidadResidencial/Inmueble/Alarmas","CERRADURA FUERA DE LINEA")


broker_address="172.24.42.52"
#broker_address="iot.eclipse.org"
print("creating new instance")
client = mqtt.Client("MonitorHub") #create new instance
client.on_message=on_message #attach function to callback
print("connecting to broker")
client.connect(broker_address, 8083) #connect to broker
#client.loop_start() #start the loop
print("Subscribing to topic","UnidadResidencial/Inmueble/Alarmas")
client.subscribe("UnidadResidencial/Inmueble/Alarmas")
client.subscribe("UnidadResidencial/Inmueble/Hub")
schedule.every(3).seconds.do(check)
schedule.run_pending()
#print("Publishing message to topic","house/bulbs/bulb1")
#client.publish("UnidadResidencial/Inmueble/Configuracion","crear:01:1234")
#print("TEST")
#client.publish("UnidadResidencial/Inmueble/NuevoCodigo","TEST")
#time.sleep(1)
client.loop_forever()