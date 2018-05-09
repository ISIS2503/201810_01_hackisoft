import paho.mqtt.client as mqtt #import the client1
import time
import smtplib
############
def on_disconnect(client, userdata, rc):
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login("nicolassana@gmail.com", "contrasenia")
    msg = "HUB FUERA DE LINEA"
    server.sendmail("nicolassana@gmail.com", "n.sanabria10@uniandes.edu.co", msg)
    server.quit()
    print("Correo enviado al propietario")

def on_message(client, userdata, message):
        print("message received " ,str(message.payload.decode("utf-8")))
        print("message topic=",message.topic)
    #print("message qos=",message.qos)
    #print("message retain flag=",message.retain)
        print("________________")
########################################


broker_address="172.24.42.52"
#broker_address="iot.eclipse.org"
print("creating new instance")
client = mqtt.Client("Hub") #create new instance
client.on_message=on_message #attach function to callback
client.on_disconnect=on_disconnect
print("connecting to broker")
client.connect(broker_address, 8083) #connect to broker
#client.loop_start() #start the loop
print("Subscribing to topic","UnidadResidencial/Inmueble/Alarmas")
client.subscribe("UnidadResidencial/Inmueble/Alarmas")
#print("Publishing message to topic","house/bulbs/bulb1")
#client.publish("UnidadResidencial/Inmueble/Configuracion","crear:01:1234")
#print("TEST")
#client.publish("UnidadResidencial/Inmueble/NuevoCodigo","TEST")
#time.sleep(1)
client.loop_forever()
#for x in range(0,5):
#    client.publish("UnidadResidencial/Inmueble/NuevoCodigo", "TEST")
#    time.sleep(4) # wait
#client.loop_stop() #stop the loop