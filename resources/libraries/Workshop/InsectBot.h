#ifndef __INSECT_BOT_H__
#define __INSECT_BOT_H__

#if ARDUINO >= 100
#include "Arduino.h"
#include "Print.h"
#else
#include "WProgram.h"
#endif




#include <Servo.h>

class InsectBot
{
private:
  Servo frontServo;
  Servo rearServo;

  /* Servo motors - global variables */
  int walkSpeed; // How long to wait between steps in milliseconds (change this to increase (-) or decrease (+) the walking speed)
  int centerPos;
  int frontRightUp;
  int frontLeftUp;
  int backRightForward;
  int backLeftForward;
  int centerTurnPos;
  int frontTurnRightUp;
  int frontTurnLeftUp;
  int backTurnRightForward;
  int backTurnLeftForward;
  int sensorPin;
  /* Danger distance */
  int dangerDistance; 	// adjust the value + for decreasing the danger distance 

  int distance;
  void updateDistance(void);
  
  bool isSetup;
  void setup(void);
  void lazySetup();
  
public:
  InsectBot(void);

  int getDistance(void);

  bool isInDanger(void);

  void goForward(void);
  void goBackRight(void);
  void turnLeft(void);

  void blinkLed(void);
};

#endif


