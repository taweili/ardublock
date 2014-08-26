//example 4
// analogSampling 1khz **** ONLY FOR AVR ***** 
// as it uses timer0 capture overflow isr.
// ARM version with PIT will be devlop later :)

#include <TimerUp.h>

#include <SCoop.h>

#ifdef SCoop_ARM
#error "not for ARM as it use AVR ISR"
#endif

TimerUp T500ms(500);           // rollover evry 500ms

defineFifo(fifo1,int16_t,200); // able to store 200ms of samples for Analog1 if needed (like if writing SD card)

defineFifo(fifo2,int16_t,20)   // 400ms max (20x20) for Analog2

vui32 avgAna2 = 0;
float scaleAna2=0.0;           // the real user value computed in an event

defineEventRun(event20ms)      // this event is trigger by the timer0 overflow interupt isr below
{ while (fifo2) { 
   int16_t val; 
   fifo2.get(&val); 
   avgAna2 += val - (avgAna2 >>2); } // overage mean of the 4 last value
 scaleAna2 = (avgAna2 / 16.0 * 1.75 + 0.25)/4.0;
}

vui32 count=0;

ISR(TIMER0_COMPA_vect) {       // same rate as Timer0: every 1024us (16mhz)
  fifo1.putInt(analogRead(1));
  count++; 
  if (count >= 20) { count =0; // every 20ms
     fifo2.putInt(analogRead(2)); 
     event20ms=true; } // trigger the event for further calculation
}

vi32 avgAna1;
defineTask(task1) // treat ana 1 : average mean over 16 last samples and print value every 500ms

void task1::setup() { avgAna1=0; T500ms=0; }

void task1::loop()  { 
  if (fifo1) { int16_t val; fifo1.get(&val); avgAna1 += val - (avgAna1 >> 4); }
  if (T500ms.rollOver()) {
     SCp("avg ana1 = ");SCp(avgAna1 >> 4);  
     SCp(", scaleAna2 = ");SCp(scaleAna2); 
#if SCoopTIMEREPORT > 0
     SCp(", cycle time = ");SCp((mySCoop.cycleMicros >> SCoopTIMEREPORT));
	 SCp(", max = ");SCp((mySCoop.maxCycleMicros >> SCoopTIMEREPORT)); mySCoop.maxCycleMicros = 0;
#endif	 
	 SCp(", stackleft = ");SCpln(stackLeft());
   }   
}


void setup() { 

  SCbegin(57600); 
  
  mySCoop.start(500); // start the scheduler with a goal of 500us per whole cycle. adjust quantum accordingly
                      // this "garantee" that the event (or timers) will be treated within this time window
  TIMSK0  |= (1 << OCIE0A);  // enable TIMER0 COMPA Interupt
  
  while (1) yield();  // by this way we are in total control of what the program does
  }

void loop() { 
  }
<<<<<<< HEAD

=======

>>>>>>> upstream/master
