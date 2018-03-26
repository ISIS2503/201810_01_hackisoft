#include <Keypad.h>
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
String KEY = "1234";
//Specified password #2
String KEYB = "7896";

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
  if(currentKey.endsWith("#")&&currentKey.length()<=KEY.length()) {
    currentKey = "";
    //Serial.println("Attempt deleted");
  }

  //If current key matches the key length
  if (currentKey.length()== KEY.length()) {
    if(currentKey == KEY|| currentKey==KEYB) {
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
  }else if(currentKey.length()> KEY.length()){
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
  }
  
  delay(100);
}

void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}


