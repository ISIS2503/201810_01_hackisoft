import paho.mqtt.client as mqtt #import the client1
import time
############
def on_message(client, userdata, message):
    print("message received " ,str(message.payload.decode("utf-8")))
    print("message topic=",message.topic)
    #print("message qos=",message.qos)
    #print("message retain flag=",message.retain)
    print("_________________________________________")
########################################
broker_address="172.24.42.52"
#broker_address="iot.eclipse.org"
print("creating new instance")
client = mqtt.Client("Cliente1") #create new instance
client.on_message=on_message #attach function to callback
print("connecting to broker")
client.connect(broker_address, 8083) #connect to broker
client.loop_start() #start the loop
print("Subscribing to topic","UnidadResidencial/Inmueble/Alarmas")
client.subscribe("UnidadResidencial/Inmueble/Alarmas")
print("Publishing message to topic","UnidadResidencial/Inmueble/Alarmas")
client.publish("house/bulbs/bulb1","TEST")
for x in range(0,25):
    client.publish("UnidadResidencial/Inmueble/Alarmas", "healthcheck")
    time.sleep(2) # wait
client.loop_stop() #stop the loop
