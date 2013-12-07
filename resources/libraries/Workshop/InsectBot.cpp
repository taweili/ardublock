#include "InsectBot.h"

InsectBot::InsectBot(void)
{
  walkSpeed = 500; // How long to wait between steps in milliseconds (change this to increase (-) or decrease (+) the walking speed)
  centerPos = 90;
  frontRightUp = 70;
  frontLeftUp = 110;
  backRightForward = 70;
  backLeftForward = 110;
  centerTurnPos = 81;
  frontTurnRightUp = 70;
  frontTurnLeftUp = 110;
  backTurnRightForward = 70;
  backTurnLeftForward = 110;
  sensorPin = A1;

  /* Danger distance */
  dangerDistance = 350; 	// adjust the value + for decreasing the danger distance 
  isSetup = false;
}

void InsectBot::lazySetup()
{
  if (!isSetup)
  {
    setup();
    isSetup = true;
  }
}

void InsectBot::setup(void)
{
  frontServo.attach(9);
  rearServo.attach(10);
  pinMode(sensorPin, INPUT);
  frontServo.write(centerPos);
  rearServo.write(centerPos);
}

void InsectBot::updateDistance(void)
{
  lazySetup();
  int i;
  int distanceCollection[5];
  int distanceCheck;

  for (i=0; i<5; ++i)
  {
    distanceCheck = analogRead(sensorPin);
    distanceCollection[i] = distanceCheck;

  }

  distance = (distanceCollection[0] + distanceCollection[1] + distanceCollection[2] + distanceCollection[3] + distanceCollection[4]) / 5;
  delay(20);
}

int InsectBot::getDistance(void)
{
  lazySetup();
  updateDistance();

  return distance;
}

bool InsectBot::isInDanger(void)
{
  lazySetup();
  updateDistance();
  return (distance > dangerDistance);
}

void InsectBot::goForward(void)
{
  lazySetup();
  frontServo.write(frontRightUp);
  rearServo.write(backLeftForward);
  delay(110);
  frontServo.write(centerPos);
  rearServo.write(centerPos);
  delay(90);
  frontServo.write(frontLeftUp);
  rearServo.write(backRightForward);
  delay(110);
  frontServo.write(centerPos);
  rearServo.write(centerPos);
  delay(90);
  delay(walkSpeed);
}

void InsectBot::goBackRight(void)
{
  lazySetup();
  frontServo.write(frontRightUp);
  rearServo.write(backRightForward-6);
  delay(110);
  frontServo.write(centerPos);
  rearServo.write(centerPos-6);
  delay(90);
  frontServo.write(frontLeftUp+9);
  rearServo.write(backLeftForward-6);
  delay(110);
  frontServo.write(centerPos);
  rearServo.write(centerPos);
  delay(90);
}

void InsectBot::turnLeft(void)
{
  lazySetup();
  frontServo.write(frontTurnRightUp);
  rearServo.write(backTurnLeftForward);
  delay(110);
  frontServo.write(centerTurnPos);
  rearServo.write(centerTurnPos);
  delay(90);
  frontServo.write(frontTurnLeftUp);
  rearServo.write(backTurnRightForward);
  delay(110);
  frontServo.write(centerTurnPos);
  rearServo.write(centerTurnPos);
  delay(90);
}

void InsectBot::blinkLed(void)
{
  lazySetup();
  for (int i=0; i<5; ++i)
  {
    digitalWrite(13, HIGH);
    delay(50);
    digitalWrite(13, LOW);
    delay(100);
  }
}




