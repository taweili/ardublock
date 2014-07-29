
//*** performance1 ***//
// time measurement for yield()
// the goal of this example is to evaluate the time taken by calling yield() in 2 scenarios:
// first scenario, yield do almost nothing (typically until the time quantum is reached)
// second scenario : yield always switch to next task.

#include <SCoop.h>

vui32  count1=0, count2=0, count3=0, count4=0;;

static inline void justCount32() __attribute__((always_inline));
static inline void justCount32(){ 
count1++;count2++;  // each count needs 1,25uS on AVR 16MHZ (maximum 8 millions in 10 seconds then )
count3++;count4++;  // these 32 counts represent a 40us effective CPU work in the loop
count1++;count2++;  // then the call to yield is made at the end of the loop to check against quantum time (400us in this example)
count3++;count4++;  // imediately back to the beginign of the loop if the time spent is not > 400us.
count1++;count2++;  // basically this loop should be executed 10 times every 400us (on AVR)
count3++;count4++;  
count1++;count2++;  
count3++;count4++;  
count1++;count2++;             
count3++;count4++;  
count1++;count2++;  
count3++;count4++;  
count1++;count2++;
count3++;count4++;              
count1++;count2++; 
count3++;count4++; }

#define CycleTime 24000 // 24 ms = 8 ms per task before switching, almost 0/imediate in main loop

#if defined(SCoop_AVR)
#define countPerTask 32.0  // 40us CPU time for 32 counts
#define justCount() justCount32()
#else
#define countPerTask 128.0 // 8,4us CPU time for 128 counts.
#define justCount() justCount32();justCount32();justCount32();justCount32()
#endif

defineTaskLoop(task1) {  while (true) { justCount(); yield(); } }
defineTaskLoop(task2) {  while (true) { justCount(); yield(); } }
defineTaskLoop(task3) {  while (true) { justCount(); yield(); } }

void setup(){

SCoopDelay timer;         // a basic Virtual timer existing in the SCoop library :)
int32_t cycle=0;
float difCount=0.0, nbYield=0.0,maxTotal=0.0, cycleTotal = 0.0;

  SCbegin(57600);  SCpln("please wait 10sec... ");

  count1=0;count2=0;count3=0;count4=0;
  timer = 10000;
  while (timer) 
  { justCount32();justCount32();justCount32();justCount32();justCount32();   // this loop takes ~400us on AVR 
    justCount32();justCount32();justCount32();justCount32();justCount32(); } 
    maxTotal = (count1+count2+count3+count4);  
  SCp("max count = ");SCp(maxTotal);          // print the total number of "count" made in 10seconds
  SCp(" at FCPU = ");SCpln(F_CPU);
  
  do {
     cycle = CycleTime - cycle; SCpln("...");   // start with longest time quantum
	 
	 mySCoop.start(cycle,0);         // 0 in main loop provides the best perfomance for this configuration
	 
	 SCp("task quantum = "); SCp(mySCoop.startQuantum);SCp("us, ");
	 SCp(SCoopNumberTask);SCp("+1tasks, ");SCp(countPerTask);SCp("counts per tasks");
	 SCpln(" : please wait 10sec ...");
     	 
	 count1=0;count2=0;count3=0;count4=0;
     timer = 10000; 
	 
	 do { justCount(); yield();                    // do same thing as other tasks	      
		} while(timer);                            // during 10sec
     
	 cycleTotal = (count1+count2+count3+count4);   // total number of counts during the 10 seconds
	 difCount   = (maxTotal-cycleTotal);           // number of counts lost due to scheduler work
	 nbYield    = (cycleTotal / countPerTask);     // number of calls to yield(), one is done at the end of each task::loop()
     SCp("cycle count = "); SCp(cycleTotal); SCp(", delta = ");SCp(difCount); SCp(" (");SCp((difCount*100)/maxTotal);SCpln("%)");
	 SCp("nb  yield() = "); SCp(nbYield); SCp(", avg yield() = "); 
	 SCp( 10000000.0 / maxTotal * difCount / nbYield );SCp("us ");
     if (cycle) { SCpln(" -> MINIMUM"); } else { SCpln(" -> MAXIMUM"); }

#if SCoopTIMEREPORT > 0
long x,y,z,t;
    z = (mySCoop.cycleMicros >>SCoopTIMEREPORT);
    SCp("cycle time      = ");SCp(z);SCpln("us =100% ");
    SCp("inside task1    = ");SCp(x=(task1.yieldMicros>>SCoopTIMEREPORT)); SCp("us =");SCp((x*100)/z);  t=x;  SCp("%, stack left: ");SCpln(task1.stackLeft());
    SCp("inside task2    = ");SCp(x=(task2.yieldMicros>>SCoopTIMEREPORT)); SCp("us =");SCp((x*100)/z);  t+=x; SCp("%, stack left: ");SCpln(task2.stackLeft());    
    SCp("inside task3    = ");SCp(x=(task3.yieldMicros>>SCoopTIMEREPORT)); SCp("us =");SCp((x*100)/z);  t+=x; SCp("%, stack left: ");SCpln(task3.stackLeft());
    SCp("Scheduler time  = ");t=z-t;SCp(t);SCp("us =%");SCpln((t*100)/z);
#else
   #warning "you must setup SCoopTIMEREPORT with a value between 1 and 4 to include timing variable and average time measurement"
#endif    
  } while (cycle); SCpln("done."); while (1);
}

void loop()
{ }


