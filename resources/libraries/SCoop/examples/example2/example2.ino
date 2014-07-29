/*** EXAMPLE 2 ***/
// a very expensive led blinking example using most of SCoop objects, 
// just as a tuto in fact to illustrate possibilities offered by the library

#include <TimerUp.h>
#include <TimerDown.h>
#include <IOFilter.h>
#include <SCoop.h>                   // include all libraries provided in the SCoop project

Output led(LED_BUILTIN);                 // easy declaration of our led
Output led2(12);                         // easy declaration of our led
InputFilter  button(1,200,100);          // 100ms to On, 200ms to Off

TimerDown T1( 8,1000);                   // from x to 0 every 1000ms
TimerUp   T2(TimerUpMillionSec, 1000);   // from 0 to 1 million, every 1000ms
TimerUp   T3(TimerUpBillionMs);          // from 0 to 1 billion every ms
TimerUp   T4;                            // from 0 to 1 billion every ms

  vbool askOff=false;

  defineEventRun(LedOn)
  { T3.resume(); led = HIGH;  askOff=true; };

  defineEvent(LedOff) // another way of defining events
  void LedOff::setup()  { } // we are now forced to declare the setup() method...
  void LedOff::run()    { T3.pause(); led = LOW;  trace("inside Event ledOff"); }

  defineEventBegin(LedNoChange) // another last way of defining events
    void run() { led = led;  }  // !@^@!
  defineEventEnd(LedNoChange)

  vbool tic5Second=false;
  
  defineTimerRun(sec,5000) 
  { trace("inside timer 5sec"); tic5Second=true; LedOn=true; } // set the event ledOn
  
  defineTimer(unused1)         // another way of defining timers
  void unused1::setup() { }
  void unused1::run()   { }
  
  defineTimerBegin(unused2)   // another way of defining timers
    void run()   { }
  defineTimerEnd(unused2)
  
  defineFifo(fifo,uint16_t,10) // use to pass waiting time parameters to the second task, just as an example

  void pinPulse() {  // create a 20ms pulse on pin12, without blocking scheduler, but protecting itself from renetrancy
     SCp(">>entering pinPulse  at T4=");SCpln(T4);
	 yieldPROTECT(); // as soon as the macro is used, the reentrant flag is check and set.
     SCp(">>executing pinPulse at T4=");SCpln(T4);
     digitalWrite(12,HIGH);sleep(10);digitalWrite(12,LOW);sleep(10); 
	 SCp(">>leaving pinPulse   at T4=");SCpln(T4);
	 }               // the code is protected until the end of this bloc code. the flag is cleared automatically
  
  vbool waitFinished=false;

  defineTask(taskLed)
  void taskLed::setup(){ fifo.flush(); }
  void taskLed::loop() 
  { trace("taskLed loop");
    sleepUntil(askOff);       // wait the boolean askOff to become true
    fifo.putInt(499);         // push in the fifo.
    fifo.putInt(501);         // push in the fifo.
    pinPulse();               // just to demonstrate concurent access to resources
	yieldATOMIC {             // another way to protect code that might call yield() 
	led2=1; delayMicroseconds(3000); led2=0; } //but this one stop the scheduler during 3ms ! 
	sleepUntil(waitFinished); // wait for the second task
    LedOff.set(); }           // set the event called LeddOff so we can expect the led to become LOW  


  defineTaskLoop(waiting) {
    SCoopDelay timer;
	trace("waiting loop");
    while (fifo==0) yield(); // wait for something to arrive in the fifo
    pinPulse();               // just to demonstrate concurent access to resources.
	                          // we launch pinpulse almost at the same time than the previous task
	uint16_t time;
    while (fifo) {
      fifo.get(&time);        // could use time = fifo.getInt() instead
      timer = time; while (timer) yield(); // same as sleep(time), just as an example
	  }
    waitFinished=true;        // synchronize other task
  }

#if SCoopANDROIDMODE > 0  
  SCoopTask * AndroidTask;
  void SchedulerLoop()
  { SCpln("DYNAMIC task (android scheduler style)");
    mySCoop.Task->sleep(4000); }
  
  void addSchedulerLoop() 
  { AndroidTask = Scheduler.startLoop(SchedulerLoop,256); } // demonstrate compatibility with android scheduler style
#else
#warning "please set the SCoopANDROIDMODE to 1 in SCoop.h if you want also to test dynamic task as with Android Scheduler.h"
#endif  
  
  char cc;

  void setup()
  { T3.pause(); T3.reset();  // with this sequence we are sure that T3 is not counting and is equal to 0
  
    SCbegin(57600); { SCoopDelay small(100); while (small); } // for some synchro of usb serial...
    
#if SCoopANDROIDMODE > 0  
	addSchedulerLoop();
#endif  
    
	mySCoop.start(1000,100);     // start the scheduler with 1ms total cycle time including 100ms for the main loop

    SCpln("press a key to display its code (k will kill android task)");
  }
  
  
  void loop()
  { if (T1==0) { T1=8; // wait 8 seconds
         SCp("*** stack left taskLed = "); SCp(taskLed.stackLeft()); SCp(", waiting = "); SCpln(waiting.stackLeft()); 
#if SCoopTIMEREPORT > 0
         SCp("******* average yield time taskled ="); SCp(taskLed.yieldMicros>> SCoopTIMEREPORT); 
		 SCp(", waiting = "); SCpln(waiting.yieldMicros>> SCoopTIMEREPORT); 
         SCp("******* average cycle time = ");SCp((mySCoop.cycleMicros >> SCoopTIMEREPORT));
	     SCp(", quantum = ");SCp(mySCoop.startQuantum);
		 SCp(" (max=");SCp(mySCoop.maxCycleMicros);SCpln(")");
	     mySCoop.maxCycleMicros = 0;
		// the max time will reflect the 3ms atomic code in the taskLed task :)
#endif	 
         SCp("led has been HIGH for  = "); SCp(T3);SCpln("ms");
       }

      if (Serial.available()) {
         cc = Serial.read();
         SCp("*** at T2 = ");SCp(T2);SCp("second, you pressed the key code : ");SCpln((int)cc);
#if SCoopANDROIDMODE == 2  
		if (cc == 'k') { 
           SCpln("<killing AndroidLoop() and reducing cycle time>");
		   if (AndroidTask) { AndroidTask->kill(); AndroidTask = NULL; }
		   else { SCpln("NO DYNAMIC TASKS REGSITERED! "); }
        }
		if (cc == 'a') { 
     	   SCpln("adding AndroidLoop() and increasing cycle time");
           if (AndroidTask==NULL) { addSchedulerLoop(); }
			else { SCpln("ONE DYNAMIC TASKS ALREADY REGSITERED! "); }
		}
#endif
		 }
  mySCoop.yield();     // switch to next elligible task or event or timer
  }                
