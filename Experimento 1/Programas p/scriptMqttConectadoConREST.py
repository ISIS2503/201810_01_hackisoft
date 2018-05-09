import paho.mqtt.client as mqtt
import time
import json
import socket
#from socket import socket, AF_INET, SOCK_DGRAM, SOCK_STREAM, socket()

localhost = "172.24.42.52"
servidor = "172.24.42.109"
puerto = 10000
elsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
elsocket.connect((servidor, puerto))

#Codigo que se ejecuta cuando un mensaje llega por mqtt
def on_message(client, userdata, message):
    print("message received: " ,str(message.payload.decode("utf-8")))
    print("message topic: " ,message.topic)
    print("____________________________")
    #Toma el contenido del mensaje
    m = str(message.payload.decode("utf-8"))
    #Divide la ruta del tópico con /
    mensaje = message.topic.split("/")
    #Asigna a cada variable el valor de una parte del tópico
    unidad = mensaje[0]
    residencia = mensaje[1]
    alarma = mensaje[2]
    #Formatea a json
    #j = json.dumps([{'unidadresidencial': unidad, 'residencia': residencia, 'alarma': alarma, 'contenidoalarma': m}])
    j = "[" + "{" + "unidadresidencial: " + unidad +", " + "residencia: " + residencia +", " +"alarma: " + alarma +", " +"contenidoalarma: " +m+"}"+"]"
    #Envia el json al servidor
    elsocket.send(j.encode("utf-8"))
    #print(j)

print("Creando cliente")
#Creacion de un cliente para procesar lo referente a mqtt
client = mqtt.Client("REST")
#client2= mqtt.Client("cliente2")
#Asignacion de la instruccion al cliente
client.on_message=on_message
#client2.on_message=on_message
print("Conectando al broker")
#El cliente se conecta a mi maquina que corre mosquitto (172.24.42.52)
client.connect(localhost, 8083)
#client2.connect(localhost)
#Iniciar un loop corriente
#client.loop_start()
#client2.loop_start()
#client.loop_forever()
#Se suscribe el cliente principal al topico para escuchar los mensajes que llegan
print("Suscribiendo al topico")
client.subscribe("alta/piso1/local1")
client.subscribe("UnidadResidencial/Inmueble/Alarmas")
client.subscribe("UnidadResidencial/Inmueble/Configuracion")

#Prueba con el mismo cliente para publicar un mensaje y comprobar impresion
print("Publicando en el topico")
client.publish("alta/piso1/local1", "TEST")
client.publish("UnidadResidencial/Inmueble/Alarmas", "prueba de alarma!")
client.publish("UnidadResidencial/Inmueble/Configuracion", "prueba de alarma!")
#Tiempo de espera para que el script pueda procesar la llegada de varios mensajes seguidos sin problemas
time.sleep(2)
#Loop para que el script corra indefinidamente e imprima todos los mensajes que lleguen al topic
client.loop_forever()
#Prueba para ver que se impriman los mensajes cada vez que alguien publica un mensaje en el topic
#client2.loop_start()
#for i in range(0, 5):
#   client2.publish("alta/piso1/local1", "TEST")
#   time.sleep(2)
#client2.loop_stop()
#Instruccion para detener controladamente el loop
#client.loop_stop()



