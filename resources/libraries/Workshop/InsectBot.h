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
    Servo frontLeg;
    Servo rearLeg;
    Servo midLeg;

    int delayWalk;
    int delayTurn;

    byte frontAngle;
    byte rearAngle;
    byte midAngle;

    int dangerDistance;

    /* Analog sensors */
    int distanceSensor;
    int lightSensorLeft;        /* not yet implemented */
    int lightSensorRight;       /* not yet implemented */

    bool isSetup;
    void setup(void);
    void lazySetup();
  
public:
    InsectBot(void);

    bool isInDanger(void);

    void goForward(void);
    void goBackward(void);
    void turnLeft(void);
    void turnRight(void);
};

#endif

/* vi: set autoindent expandtab softtabstop=4 shiftwidth=4 : */
