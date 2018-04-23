#include <Keypad.h>

//Entrega3 test_security_EEPROM_arduino.ino
#include <EEPROM.h>

//DECLARACIONES PARA LA COMUNICACIÓN SERIAL
#define SIZE_BUFFER_DATA       50
boolean stringComplete = false;
String inputString = "";
char bufferData [SIZE_BUFFER_DATA];

//DECLARACIONES DEL POTENCIOMETRO

const int NIVEL_APROPIADO = 410;
const int SONIDO_BATERIA_BAJA = 2;
const int FRECUENCIA_SONIDO = 30;
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
int indicePass = 0;

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
      Serial.println("Door opened!!");
    }
  } else if (!block && buttonState) {
    if(digitalRead(CONTACT_PIN)) {
      if((millis()-currTime)>=30000) {
        setColor(255, 0, 0);
        Serial.println("Door opened too much time!!");
      }
    }else{
      setColor(0, 0, 255);
      open = false;
      buttonState = false;
      Serial.println("Door closed!!");
    }
  } 
  
  if(!block) {
    customKey = customKeypad.getKey();
    
  } else if (block) {
    Serial.println("Number of attempts exceeded");
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
    Serial.println("Door closed");
    digitalWrite(10,LOW);
    currentKey = "";
    tiempoAbierto = 0;
    setColor(0, 0, 255); // Color azul por defecto
  }
  //If the current key contains '#' reset attempt
  if(currentKey.endsWith("#")&&currentKey.length()<=TAM_CLAVE) {
    currentKey = "";
    //Serial.println("Attempt deleted");
  }

  //If current key matches the key length
  if (currentKey.length()== TAM_CLAVE) {
    if(compareKey(currentKey)) {
      digitalWrite(10,HIGH);
      open = true;
      Serial.println("Door opened!!");
      
      if (tiempoAbierto > 30000){
        setColor(255, 0, 0); // Color rojo por estar abierta más de 30 seg
      } else {
        setColor(0, 255, 0); // Color verde por estar abierta
        tiempoAbierto += 100;
      }
      attempts = 0;
    }
    else {
      attempts++;
      currentKey = "";
      Serial.println("Number of attempts: "+String(attempts));
      setColor(255, 0, 0); // Color rojo por intento erróneo
      delay(1000);
      setColor(0, 0, 255); // Color azul por stand-by
    }
  }else if(currentKey.length()> TAM_CLAVE){
    setColor(0, 255, 0); // Color verde por estar abierta
    Serial.println("Door opened!!");
  }
  if(attempts>=maxAttempts) {
    currentKey = "";
    attempts = 0;
    Serial.println("System locked");
    setColor(255, 0, 0); // Color rojo por estar bloqueado
    delay(LOCK_TIME);
    Serial.println("System unlocked");
    setColor(0, 0, 255); // Color azul por defecto
  }

  val = digitalRead(inputPin);  // read input value
  if (val == HIGH) {            // check if the input is HIGH
    digitalWrite(ledPin, HIGH);  // turn LED ON
    if (pirState == LOW) {
      // we have just turned on
      Serial.println("Motion detected!");
      // We only want to print on the output change, not state
      pirState = HIGH;
    }
  } else {
    digitalWrite(ledPin, LOW); // turn LED OFF
    if (pirState == HIGH){
      // we have just turned of
      //Serial.println("Motion ended!");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }
/*
  lectura = analogRead(potenciometro);
  Serial.println("Valores potenciometro: " + String(lectura));

  if (lectura < NIVEL_APROPIADO){
    digitalWrite(ledBateria, HIGH);  // turn LED ON 
    
    if (contSonido < SONIDO_BATERIA_BAJA && contFrecuencia == 30){
      setColor(255, 0, 0); // Color rojo por estar con batería baja
      contSonido++;
      Serial.println("Low battery");
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

  if (Serial.available() > 0){
      int bytesEntrada = Serial.read();
      Serial.print("Recibido del servidor: ");
      Serial.println(char(bytesEntrada));
  }

  receiveData();
  processData();
  
  delay(100);
}

void processData() {
  if (stringComplete) {
    String* comandos;
    processCommand(comandos, inputString);

    if (comandos[0] == "crear"){
      addPassword(atoi(comandos[1]), indicePass);
      indicePass++;
    } else if (comandos[0] == "actualizar"){
      updatePassword(atoi(comandos[1]), indicePass);
    } else if (comandos[0] == "eliminar"){
      if (comandos[1] == "todo"){
        deleteAllPasswords();
      } else {
        deletePassword(atoi(comandos[1]));
      }
    }
    
    inputString = "";
    stringComplete = false;
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
        arg1 = EEPROM.read(acc+1)*256;
        arg1+= arg0;
        if(String(arg1)==key) {
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

// Methods that divides the command by parameters
void processCommand(String* result, String command) {
  char buf[sizeof(command)];
  String vars = "";
  vars.toCharArray(buf, sizeof(buf));
  char *p = buf;
  char *str;
  int i = 0;
  while ((str = strtok_r(p, ";", &p)) != NULL) {
    // delimiter is the semicolon
    result[i++] = str;
  }
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
}

//Method that updates a password in the specified index
void updatePassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
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
}

//Method that deletes all passwords
void deleteAllPasswords() {
  //Password reference to inactive
  EEPROM.write(0,0);
  EEPROM.write(1,0);
  EEPROM.write(2,0);
}


void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}

