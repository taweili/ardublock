//example 5
// analogSampling 500hz **** FOR ARM & AVR ***** 
// doesnt use any interrupt. use the default SCoop cycle to triger timer
// jitter will depend on the cycletime which is printed every 500ms

#include <Arduino.h>

#include <TimerUp.h>

#include <SCoop.h>


TimerUp T500ms(500);          // rollover every 500ms

defineFifo(fifo1,int16_t,20); // able to store 40ms of samples for Analog1 if needed 

defineFifo(fifo2,int16_t,20)  // 400ms max (20x20) for Analog2

vui32 count=0;

defineTimerRun(sampling,2)    // 2ms = 500hz right ?
{ fifo1.putInt(analogRead(1));
  count++; 
  if (count >= 10) { count =0; // every 20ms
     fifo2.putInt(analogRead(2));  }
  }


defineTask(task1) // treat ana 
vi32 avgAna1=0;
vui32 avgAna2 = 0;
float scaleAna2=0.0;         // the real user value computed in an event

void task1::setup() { avgAna1=0; avgAna2=0; scaleAna2=0; T500ms=0; }

void task1::loop()  { 

  if (fifo1) while (fifo1) { uint32_t val=fifo1.getInt(); avgAna1 += val - (avgAna1 >> 4); } // overage mean of the 16 last value

  if (fifo2) {
     while (fifo2) { uint32_t val=fifo2.getInt(); avgAna2 += val - (avgAna2 >> 2); } // overage mean of the 4 last value
     scaleAna2 = (avgAna2 / 16.0 * 1.75 + 0.25)/4.0;  }
  
  if (T500ms.rollOver()) {
     SCp("avg ana1 = ");    
	 SCp(avgAna1 >> 4);     
     SCp(", scaleAna2 = "); 
	 SCp(scaleAna2);        
     
#if SCoopTIMEREPORT > 0
	 SCp(", cycle time = ");SCp((mySCoop.cycleMicros >> SCoopTIMEREPORT));
	 SCp(", max time = ");SCp((mySCoop.maxCycleMicros ));mySCoop.maxCycleMicros =0;
#endif
     SCpln("");
   }   
}

void setup() { 

  SCbegin(57600); 
  
  mySCoop.start(250,0);  // force 250us cycle time and 0 in main yield. very aggressive for AVR but ok 
                    
  while (1) yield();  // by this way we are in total control of what the program does
  }

void loop() { 
  // nothing to do here then
}
<<<<<<< HEAD

=======

>>>>>>> upstream/master
