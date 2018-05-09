import paho.mqtt.client as mqtt
from datetime import datetime
import time


broker_address="172.24.42.52"
#broker_address="iot.eclipse.org"
print("creating new instance")
client = mqtt.Client("ActualizarHora") #create new instance
client.on_message=on_message #attach function to callback
print("connecting to broker")
client.connect(broker_address, 8083) #connect to broker
client.loop_start() #start the loop
#print("Subscribing to topic","UnidadResidencial/Inmueble/Alarmas")
#client.subscribe("UnidadResidencial/Inmueble/Alarmas")
#client.subscribe("UnidadResidencial/Inmueble/Hub")
#print("Publishing message to topic","house/bulbs/bulb1")
client.publish("UnidadResidencial/Inmueble/Configuracion","TEST")
for x in range(0,60):
	horaactual = datetime.now().hour
	minutoactual = datetime.now().minute
	client.publish("UnidadResidencial/Inmueble/Configuracion", horaactual+":"+minutoactual)
	time.sleep(60)
client.loop_stop()