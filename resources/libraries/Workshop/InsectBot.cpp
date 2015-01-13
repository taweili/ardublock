#include "InsectBot.h"

InsectBot::InsectBot(void)
{
    delayWalk = 2; /* 2 milliseconds between 1° step (movement is from 50° to 120° for front and rear legs) */
    delayTurn = 3; /* 3 milliseconds between 1° step */
    frontAngle = 90;
    rearAngle = 90;
    midAngle = 90;

    /* Danger distance, small distance == bigger number (300 ~= 20 cm, 500 ~= 10 cm) */
    dangerDistance = 450;

    isSetup = false;
}

void InsectBot::lazySetup()
{
    if (!isSetup) {
        setup();
        isSetup = true;
    }
}

void InsectBot::setup(void)
{
    frontLeg.attach(9);
    rearLeg.attach(10);
    midLeg.attach(11);
    frontLeg.write(frontAngle);
    rearLeg.write(rearAngle);
    midLeg.write(midAngle);

    pinMode(distanceSensor, A1);
    pinMode(lightSensorLeft, A2);
    pinMode(lightSensorRight, A0);

    delay(2000);
}

bool InsectBot::isInDanger(void)
{
    int i, count = 5, sum = 0;

    lazySetup();

    for (i = 0; i < count; i++) {
        sum += analogRead(distanceSensor);
    }

    return (sum > count * dangerDistance); /* the bigger the number is, the nearer we are */
}

void InsectBot::goForward(void)
{
    lazySetup();

    for (midAngle = 70; midAngle < 100; midAngle +=1) {
        midLeg.write(midAngle);
        delay(delayWalk);
    }
    for (frontAngle = 120; frontAngle > 50; frontAngle -= 1) {
        frontLeg.write(frontAngle);
        rearLeg.write(frontAngle);
        delay(delayWalk);
    }
    for (midAngle = 100; midAngle > 70; midAngle -=1){
        midLeg.write(midAngle);
        delay(delayWalk);
    }
    for (frontAngle = 50; frontAngle < 120; frontAngle += 1) {
        frontLeg.write(frontAngle);
        rearLeg.write(frontAngle);
        delay(delayWalk);
    }
}

void InsectBot::goBackward(void)
{
    lazySetup();

    for (midAngle = 70; midAngle < 100; midAngle +=1) {
        midLeg.write(midAngle);
        delay(delayWalk);
    }
    for (frontAngle = 50; frontAngle < 120; frontAngle += 1) {
        frontLeg.write(frontAngle);
        rearLeg.write(frontAngle);
        delay(delayWalk);
    }

    for (midAngle = 100; midAngle > 70; midAngle -=1){
        midLeg.write(midAngle);
        delay(delayWalk);
    }
    for (frontAngle = 120; frontAngle > 50; frontAngle -= 1) {
        frontLeg.write(frontAngle);
        rearLeg.write(frontAngle);
        delay(delayWalk);
    }
}

void InsectBot::turnLeft(void)
{
    lazySetup();

    rearLeg.write(90);
    for (midAngle = 70; midAngle < 110; midAngle += 1) {
        midLeg.write(midAngle);
        delay(delayTurn);
    }
    for (frontAngle = 70; frontAngle < 110; frontAngle +=1) {
        frontLeg.write(frontAngle);
        delay(delayTurn);
    }
    for (rearAngle = 110; rearAngle > 70; rearAngle -=1) {
        rearLeg.write(rearAngle);
        delay(delayTurn);
    }
    for (midAngle = 110; midAngle > 70; midAngle -= 1) {
        midLeg.write(midAngle);
        delay(delayTurn);
    }
    for (frontAngle = 110; frontAngle > 70; frontAngle -=1) {
        frontLeg.write(frontAngle);
        delay(delayTurn);
    }
    for (rearAngle = 70; rearAngle < 110; rearAngle +=1) {
        rearLeg.write(rearAngle);
        delay(delayTurn);
    }
}

void InsectBot::turnRight(void)
{
    lazySetup();

    frontLeg.write(90);
    for (midAngle = 70; midAngle < 110; midAngle += 1) {
        midLeg.write(midAngle);
        delay(delayTurn);
    }
    for (rearAngle = 70; rearAngle < 110; rearAngle +=1) {
        rearLeg.write(rearAngle);
        delay(delayTurn);
    }
    for (frontAngle = 110; frontAngle > 70; frontAngle -=1) {
        frontLeg.write(frontAngle);
        delay(delayTurn);
    }
    for (midAngle = 110; midAngle > 70; midAngle -= 1) {
        midLeg.write(midAngle);
        delay(delayTurn);
    }
    for (rearAngle = 110; rearAngle > 70; rearAngle -=1) {
        rearLeg.write(rearAngle);
        delay(delayTurn);
    }
    for (frontAngle = 70; frontAngle < 110; frontAngle +=1) {
        frontLeg.write(frontAngle);
        delay(delayTurn);
    }
}

/* vi: set autoindent expandtab softtabstop=4 shiftwidth=4 : */
