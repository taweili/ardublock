#include <Servo.h>
#include "InsectBot.h"

InsectBot insect;
int _ABVAR_1_a;

void setup()
{
}

void loop()
{
  if (insect.isInDanger())
  {
    insect.blinkLed();
    insect.turnLeft();
    for (_ABVAR_1_a=1; _ABVAR_1_a<= ( 5 ); ++_ABVAR_1_a )
    {
      insect.goBackRight();
    }
  }
  else
  {
    insect.goForward();
  }
}



