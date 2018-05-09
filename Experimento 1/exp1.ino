#include <Keypad.h>

//Entrega3 test_security_EEPROM_arduino.ino
#include <EEPROM.h>

//DECLARACIONES PARA HORARIOS
int horarios_inicio[20];
int horarios_fin[20];
int horaActual = -1;

//DECLARACIONES PARA LA COMUNICACIÓN SERIAL
String ALARMA = "ALARMA";
#define SIZE_BUFFER_DATA       50
boolean stringComplete = false;
String inputString = "";
char bufferData [SIZE_BUFFER_DATA];
int cl[20];

//DECLARACIONES PARA EL HEALTCHECK
#define HEALTCHECK  "HEALTCHECK"
#define FRECUENCIA_MUESTREO   30
int intervalo_muestreo = 0;

//DECLARACIONES DEL POTENCIOMETRO

const int NIVEL_APROPIADO = 410;
const int SONIDO_BATERIA_BAJA = 20;
const int FRECUENCIA_SONIDO = 300;
int potenciometro = A2;  //pin del potenciómetro
long lectura;          //lectura de valores del potenciómetro
int ledBateria = A1;
int contSonido = 0;
int contFrecuencia = 30;

//DECLARACIONES DE RGB LED

int redPin= 13;
int greenPin = 12;
int bluePin = 10;

//DECLARACIONES DEL SENSOR

int ledPin = A0;//14;                // choose the pin for the LED
int inputPin = 2;               // choose the input pin (for PIR sensor)
int pirState = LOW;             // we start, assuming no motion detected
int val = 0;    

//DECLARACIONES DEL PULSADOR

//Button pin
const int CONTACT_PIN = 11;                // variable for reading the pin status

//Attribute that defines the button state
boolean buttonState;

//Current time when the button is tapped
long currTime;

//DECLARACIONES KEYPAD

//Specified password
//String KEY = "1234";
//Specified password #2
//String KEYB = "7896";
const int TAM_CLAVE = 4;

//Time in milliseconds which the system is locked
const int LOCK_TIME = 30000;

//Keypad rows
const byte ROWS = 4; 

//Keypad columns
const byte COLS = 3;

//Maximum number of attempts allowed
const byte maxAttempts = 3;

//Keypad mapping matrix
char hexaKeys[ROWS][COLS] = {
  {
    '1', '2', '3'
  }
  ,
  {
    '4', '5', '6'
  }
  ,
  {
    '7', '8', '9'
  }
  ,
  {
    '*', '0', '#'
  }
};

//Keypad row pins definition
byte rowPins[ROWS] = {
  9, 8, 7, 6
}; 

//Keypad column pins definition
byte colPins[COLS] = {
  5, 4, 3
};

//Keypad library initialization
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

//Current key variable
String currentKey;
//
//Door state
boolean open;
//Number of current attempts
byte attempts;
//If the number of current attempts exceeds the maximum allowed
boolean block;

//Contador de tiempo abierto de la puerta
int tiempoAbierto = 0;
void setup() {
  Serial.begin(9600);
  currentKey = "";
  open = false;
  attempts = 0;
  block = false;
  
  pinMode(ledPin, OUTPUT); 
  pinMode(inputPin, INPUT);     // declare sensor as input
  
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  pinMode(CONTACT_PIN,INPUT);
  
  setColor(0, 0, 255); 
  buttonState = false;
  
  pinMode(potenciometro, INPUT); // declaracion de la variable del potenciómetro
  pinMode(ledBateria,OUTPUT);
}

void loop() {
  char customKey;

  //Button input read and processing 
  if(!block && !buttonState) {
    if(digitalRead(CONTACT_PIN)) {
      currTime = millis();
      buttonState = true;
      setColor(0, 255, 0);
      open = true;
      attempts = 0;
      Serial.println(ALARMA + ":Puerta abierta!!");
    }
  } else if (!block && buttonState) {
    if(digitalRead(CONTACT_PIN)) {
      if((millis()-currTime)>=30000) {
        setColor(255, 0, 0);
        Serial.println(ALARMA + ":Puerta abierta demasiado tiempo!!");
      }
    }else{
      setColor(0, 0, 255);
      open = false;
      buttonState = false;
      Serial.println(ALARMA + ":Puerta cerrada!!");
    }
  } 
  
  if(!block) {
    customKey = customKeypad.getKey();
    
  } else if (block) {
    Serial.println(ALARMA + ":Numero de intentos excedido");
    setColor(255, 0, 0); // Color rojo por estar bloqueado
    while(true);
  }

  //Verification of input and appended value
  if (customKey) {  
    
    currentKey+=String(customKey);
    //Serial.println(currentKey);
    setColor(0, 255, 255); // Color amarillo por oprimir tecla
    delay(100);
    setColor(0, 0, 255); // Color azul por defecto
  }

  //If the current key contains '*' and door is open
  if(open && currentKey.endsWith("*")) {
    open = false;
    Serial.println(ALARMA + ":Puerta cerrada");
    digitalWrite(10,LOW);
    currentKey = "";
    tiempoAbierto = 0;
    setColor(0, 0, 255); // Color azul por defecto
  }
  //If the current key contains '#' reset attempt
  if(currentKey.endsWith("#")&&currentKey.length()<=TAM_CLAVE) {
    currentKey = "";
    //Serial.println(ALARMA + ":Intento eliminado");
  }

  //If current key matches the key length
  if (currentKey.length()== TAM_CLAVE) {
    if(compareKey(currentKey) && estaEnHorario(currentKey.toInt())) {
      digitalWrite(10,HIGH);
      open = true;
      Serial.println(ALARMA + ":Puerta abierta!!");
      
      if (tiempoAbierto > 30000){
        setColor(255, 0, 0); // Color rojo por estar abierta más de 30 seg
      } else {
        setColor(0, 255, 0); // Color verde por estar abierta
        tiempoAbierto += 100;
      }
      attempts = 0;
    } else if (!estaEnHorario(currentKey.toInt())){
      Serial.println(ALARMA + ":Horario no permitido");
    } else {
      attempts++;
      currentKey = "";
      Serial.println(ALARMA + ":Numero de intentos. "+String(attempts));
      setColor(255, 0, 0); // Color rojo por intento erróneo
      delay(1000);
      setColor(0, 0, 255); // Color azul por stand-by
    }
  }else if(currentKey.length()> TAM_CLAVE){
    setColor(0, 255, 0); // Color verde por estar abierta
    Serial.println(ALARMA + ":Puerta abierta!!");
  }
  if(attempts>=maxAttempts) {
    currentKey = "";
    attempts = 0;
    Serial.println(ALARMA + ":Sistema bloqueado");
    setColor(255, 0, 0); // Color rojo por estar bloqueado
    delay(LOCK_TIME);
    Serial.println(ALARMA + ":Sistema desbloqueado");
    setColor(0, 0, 255); // Color azul por defecto
  }

  val = digitalRead(inputPin);  // read input value
  if (val == HIGH) {            // check if the input is HIGH
    digitalWrite(ledPin, HIGH);  // turn LED ON
    if (pirState == LOW) {
      // we have just turned on
      Serial.println(ALARMA + ":Movimiento detectado!");
      // We only want to print on the output change, not state
      pirState = HIGH;
    }
  } else {
    digitalWrite(ledPin, LOW); // turn LED OFF
    if (pirState == HIGH){
      // we have just turned of
      //Serial.println(ALARMA + ":Movimiento terminado!");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }
  /*
  lectura = analogRead(potenciometro);
  //Serial.println(ALARMA + ":Valores potenciometro: " + String(lectura));

  if (lectura < NIVEL_APROPIADO){
    digitalWrite(ledBateria, HIGH);  // turn LED ON 
    
    if (contSonido < SONIDO_BATERIA_BAJA && contFrecuencia == 30){
      setColor(255, 0, 0); // Color rojo por estar con batería baja
      contSonido++;
      Serial.println(ALARMA + ":Bateria baja");
    } else if (contSonido >= SONIDO_BATERIA_BAJA) {
      setColor(0, 0, 255); // Color azul por defecto
      contSonido = 0;
      contFrecuencia = 0;
    } else {
      contFrecuencia++;
    }
  } else {
    setColor(0, 0, 255); // Color azul por defecto
    digitalWrite(ledBateria, LOW); // turn LED OFF
    contSonido = 0;
    contFrecuencia = 30;
  }*/

  receiveData();
  processData();
  healtcheck();
  
  delay(100);
}

void healtcheck(){
  if (intervalo_muestreo >= FRECUENCIA_MUESTREO){
    Serial.println(HEALTCHECK);
    intervalo_muestreo = 0;
  }

  intervalo_muestreo++;
}

void processData() {
  if (stringComplete) {
    String comandos[5];
    //Serial.println(inputString);
    processCommand(comandos, inputString);
    /*Serial.println("Valor 1: " + comandos[0]);
    Serial.println("Valor 2: " + comandos[1]);
    Serial.println("Valor 3: " + comandos[2]);
    Serial.println("Valor 4: " + comandos[3]);
    Serial.println("Valor 5: " + comandos[4]);*/
    if (comandos[0] == "crear"){
      addPassword(comandos[2].toInt(), comandos[1].toInt());
      adicionarHorario(comandos[3].toInt(), comandos[4].toInt(), comandos[1].toInt());
    } else if (comandos[0] == "actualizar"){
      updatePassword(comandos[2].toInt(), comandos[1].toInt());
      actualizarHorario(comandos[3].toInt(), comandos[4].toInt(), comandos[1].toInt());
    } else if (comandos[0] == "eliminar"){
      if (comandos[1] == "todo"){
        deleteAllPasswords();
        eliminarHorarios();
      } else {
        deletePassword(comandos[1].toInt());
        eliminarHorario(comandos[1].toInt());
      }
    } else if (comandos[0] == "hora"){
      horaActual = comandos[1].toInt();
    }
    
    inputString = "";
    stringComplete = false;
  }
}

//Metodo que adiciona un horario
void adicionarHorario(int inicio, int fin, int pos){
  horarios_inicio[pos] = inicio;
  horarios_fin[pos] = fin;
}

//Metodo que actualiza un horario
void actualizarHorario(int inicio, int fin, int pos){
  horarios_inicio[pos] = inicio;
  horarios_fin[pos] = fin;
}

//Metodo que elimina un horario
void eliminarHorario(int pos){
  horarios_inicio[pos] = -1;
  horarios_fin[pos] = -1;
}

//Metodo que elimina un horario
void eliminarHorarios(){
  for (int i = 0; i < 20; i++){
    horarios_inicio[i] = -1;
    horarios_fin[i] = -1;
  }
}

boolean estaEnHorario(int clave){
  int index = -1;
  for (int i = 0; i < 20; i++){
    if (cl[i] == clave){
      index = i;
      i = 20;
    }
  }
  
  return horarios_inicio[index] <= horaActual && horaActual <= horarios_fin[index];
}

// Methods that divides the command by parameters
void processCommand(String result[], String command) {
  int index = 0;
  int i = 0;
  String separador = ":";
  index = command.indexOf(separador);
  
  while(index > -1){
    result[i] = command.substring(0, index);
    command = command.substring(index + 1);
    index = command.indexOf(separador);
  
    i++;
    if (index == -1 && command != ""){
      result[i] = command;
    }
  }
}

void receiveData() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {
      inputString.toCharArray(bufferData, SIZE_BUFFER_DATA);
      stringComplete = true;
    }
  }
}

//Entrega3-test_security_EEPROM_arduino.ino
// Method that compares a key with stored keys
boolean compareKey(String key) {
  int acc = 3;
  int codif, arg0, arg1; 
  for(int i=0; i<3; i++) {
    codif = EEPROM.read(i);
    while(codif!=0) {
      if(codif%2==1) {
        arg0 = EEPROM.read(acc);
        arg1 = EEPROM.read(acc+1);
        String compose = "";
        arg1*=256;
        arg1+= arg0;
        if(sizeof(String(arg1)) == 1) {
          compose = "000"+String(arg0);
        }
        else if(sizeof(String(arg1)) == 2) {
          compose = "00"+String(arg0);
        }
        else if(sizeof(String(arg1))==3) {
          compose = "0"+String(arg1);
        }
        else {
          compose = String(arg1);
        }

        if(compose==key) {
          return true;
        }
      }
      acc+=2;
      codif>>=1;
    }
    acc=(i+1)*16+3;
  }
  return false;
}

//Method that adds a password in the specified index
void addPassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j |= i;
  EEPROM.write(location,j);
  cl[index] = val;
}

//Method that updates a password in the specified index
void updatePassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
  cl[index] = val;
}

//Method that deletes a password in the specified index
void deletePassword(int index) {
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j ^= i;
  EEPROM.write(location,j);
  cl[index] = -1;
}

//Method that deletes all passwords
void deleteAllPasswords() {
  //Password reference to inactive
  EEPROM.write(0,0);
  EEPROM.write(1,0);
  EEPROM.write(2,0);

  for (int i = 0; i < 20; i++){
    cl[i] = -1;  
  }
}


void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}
