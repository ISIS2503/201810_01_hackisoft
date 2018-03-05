import paho.mqtt.client as mqtt 
localhost = "172.24.42.52" 
import time 
#Codigo que se ejecuta cuando un mensaje llega por mqtt 
def on_message(client, userdata, message):
     print("message received " ,str(message.payload.decode("utf-8")))
     print("message topic=" ,message.topic)  
	 
 print("Creando cliente") 
 #Creacion de un cliente para procesar lo referente a mqtt 
 client = mqtt.Client("cliente1") 
 #client2= mqtt.Client("cliente2") 
 #Asignacion de la instruccion al cliente 
 client.on_message=on_message 
 #client2.on_message=on_message 
 print("Conectando al broker") 
 #El cliente se conecta a mi maquina que corre mosquitto (172.24.42.52) 
 client.connect(localhost) 
 #client2.connect(localhost) 
 #Iniciar un loop corriente 
 #client.loop_start() 
 #Loop para que el script corra indefinidamente e imprima todos los mensajes que lleguen al topic 
 client.loop_forever() 
 #Se suscribe el cliente principal al topico para escuchar los mensajes que llegan 
 print("Suscribiendo al topico") 
 client.subscribe("alta/piso1/local1") 
 #Prueba con el mismo cliente para publicar un mensaje y comprobar impresion 
 print("Publicando en el topico") 
 client.publish("alta/piso1/local1", "TEST") 
 #Tiempo de espera para que el script pueda procesar la llegada de varios mensajes seguidos sin problemas 
 time.sleep(2) 
 #Prueba para ver que se impriman los mensajes cada vez que alguien publica un mensaje en el topic 
 #for i in range(0, 5): 
 #    
 client2.publish("alta/piso1/local1", "TEST") #    time.sleep(2) 
 #Instruccion para detener controladamente el loop 
 #client.loop_stop()  