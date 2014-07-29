
/*
 Multiple Blinks

 Demonstrates the use of the SCoop library for AVR or ARM platform
 
 Hardware required :
 * LEDs connected to pins 11, 12, and 13

 created 8 Oct 2012
 by Cristian Maglie
 Modified by 
 Scott Fitzgerald 19 Oct 2012
 
 This example code is in the public domain
 
 http://arduino.cc/en/Tutorial/MultipleBlinks
 
 Modified by Fabrice Oudert 8 Jan 2013 
 https://code.google.com/p/arduino-scoop-cooperative-scheduler-arm-avr/
 
 Another alternative for defining the 3 tasks. same behaviour and result as original program
 
*/

// Include Scheduler since we want to manage multiple tasks.
#include <SCoop.h>  // instead of original #include <Scheduler.h>

#if defined(SCoopANDROIDMODE) && (SCoopANDROIDMODE == 1)
#else
#error "to run this example with SCoop you MUST set the parameter SCoopANDROIDMODE to 1 at the begining of SCoop.h"
#endif

int led1 = LED_BUILTIN;
int led2 = 12;
int led3 = 11;


void setup() {
  Serial.begin(9600);

  // Setup the 3 pins as OUTPUT
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);

  // Add "loop2" and "loop3" to scheduling.
  // "loop" is always started by default.
  // Scheduler.startLoop(loop2);
  // Scheduler.startLoop(loop3);
  
  mySCoop.start(); // this is needed with SCoop, not with Android original Scheduler
}

// Task no.1: blink LED with 1 second delay.
void loop() {
uint32_t timer1=0;

  digitalWrite(led1, HIGH);

  // IMPORTANT:
  // When multiple tasks are running 'delay' passes control to
  // other tasks while waiting and guarantees they get executed.
  mySCoop.delay(1000);

  digitalWrite(led1, LOW);

  mySCoop.delay(1000);

}

// Task no.2: blink LED with 0.1 second delay.
defineTaskLoop(loop2) {

  digitalWrite(led2, HIGH);
  Scheduler.delay(100);    // can also use the class name Scheduler !

  digitalWrite(led2, LOW);
  Scheduler.delay(100);
}

// Task no.3: accept commands from Serial port
// '0' turns off LED
// '1' turns on LED
defineTaskLoop(loop3) {
  if (Serial.available()) {
    char c = Serial.read();
    if (c=='0') {
      digitalWrite(led3, LOW);
      Serial.println("Led turned off!");
    }
    if (c=='1') {
      digitalWrite(led3, HIGH);
      Serial.println("Led turned on!");
    }
  }

  // IMPORTANT:
  // We must call 'yield' at a regular basis to pass
  // control to other tasks.
  // yield(); // not needed with SCoop, already included in the library , at the end of each loop
}
