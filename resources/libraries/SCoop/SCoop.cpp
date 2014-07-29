/*****************************************************************************/
/* SCOOP LIBRARY / AUTHOR FABRICE OUDERT / GNU GPL V3                        */
/* https://code.google.com/p/arduino-scoop-cooperative-scheduler-arm-avr/    */
/* VERSION 1.2  NEW YEAR PACK 10/1/2013                                      */
/* ENJOY AND USE AT YOUR OWN RISK  :)                                        */
/* SHOULD READ USER GUIDE FIRST (@\_/@)                                      */
/*****************************************************************************/


#include "SCoop.h"
#define SCINM SCoopInstanceNickName


/********* GLOBAL VARIABLE *******/

SCoopEvent*   SCoopFirstItem = NULL;           // has to be initialized here. hold a pointer on the whole list of task/event/timer...
SCoopEvent*   SCoopFirstTaskItem = NULL;       // has to be initialized here. points to the first of all tasks registered in the list
uint8_t       SCoopNumberTask = 0;             // hold the number of task registered. used to calculate quantum in start(xxx)


SCoop  SCoopInstanceNickName;                  // then we can use the library in the main sketch directly
#define SCINM SCoopInstanceNickName            // just a local nickname...
#if SCoopANDROIDMODE >= 1
SCoop& ArduinoSchedulerNickName = SCINM;       // this will create another identifier for the same object instance
#endif

/********* ASSEMBLY / LETS GET STARTED WITH THE COMPLEX THINGS **********/
// original idea for switching stack pointer taken out from ChibiOS. 
// Credit to the author. now slightly modified. 
// http://forum.pjrc.com/threads/540-ChibiOS-RTand-FreeRTOS-for-Teensy-3-0
//
// original idea for micros() optimization taken from CORE_TEENSY
// credit to Paul http://www.pjrc.com/teensy/
//************************************************************************/

#if defined(SCoop_ARM) && (SCoop_ARM == 1)

static void SCoopSwitch(uint8_t **newSP, uint8_t **oldSP) __attribute__((naked,noinline)) ;
static void SCoopSwitch(uint8_t **newSP, uint8_t **oldSP)
{ asm volatile ("push    {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, lr}" : : : "memory");
  asm volatile ("str     sp, [%[oldsp], #0]  \n\t"
                "ldr     sp, [%[newsp], #0]" : : [newsp] "r" (newSP), [oldsp] "r" (oldSP));
  asm volatile ("pop     {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, pc}" : : : "memory");
};

static inline uint32_t SCoopGetSP() __attribute__ ((always_inline)) ;
uint32_t SCoopGetSP() { register uint32_t val; asm ("mov     %[temp],sp" : [temp] "=r" (val)); return val; }

#define ARM_ATOMIC ASM_ATOMIC 
#define AVR_ATOMIC 

#define SCoopMicros()   ((micros_t)micros())     // overloading the standard micros()

#endif

#if defined(SCoop_AVR) && (SCoop_AVR == 1)

static void SCoopSwitch(void *newSP, void *oldSP) __attribute__((naked,noinline));
static void SCoopSwitch(void *newSP, void *oldSP)
{ asm volatile ("push r2  \n\t push r3  \n\t push r4  \n\t push r5  \n\t push r6  \n\t push r7  \n\t push r8  \n\t push r9  \n\t push r10 \n\t"
                "push r11 \n\t push r12 \n\t push r13 \n\t push r14 \n\t push r15 \n\t push r16 \n\t push r17 \n\t push r28 \n\t push r29");
  
  asm volatile ("movw    r28, %[oldsp]" : : [oldsp] "r" (oldSP));
  asm volatile ("in      r2, 0x3d"); // SPL
  asm volatile ("in      r3, 0x3e"); // SPH
  asm volatile ("std     Y+0, r2");  // store the current SP into the pointer oldSP
  asm volatile ("std     Y+1, r3");

  asm volatile ("movw    r28, %[newsp]" : : [newsp] "r" (newSP));
  asm volatile ("ldd     r2, Y+0");  // restore the SP from the pointer newSP
  asm volatile ("ldd     r3, Y+1");
  asm volatile ("in      r4, 0x3f"); // save SREG
  asm volatile ("cli             "); // just to be safe on playing with stack ptr :) (useless with xmega)
  asm volatile ("out     0x3e, r3"); // SPH
  asm volatile ("out     0x3d, r2"); // SPL
  asm volatile ("out     0x3f, r4"); // restore SREG asap (same approach as in setjmp.S credit to Marek Michalkiewicz)
  
  asm volatile ("pop r29 \n\t pop r28 \n\t pop r17 \n\t pop r16 \n\t pop r15 \n\t pop r14 \n\t pop r13 \n\t pop r12 \n\t pop r11 \n\t"
                "pop r10 \n\t pop r9  \n\t pop r8  \n\t pop r7  \n\t pop r6  \n\t pop r5  \n\t pop r4  \n\t pop r3  \n\t pop r2");
  asm volatile ("ret");  };

#define SCoopGetSP() (uint16_t)SP              // direct read access to SP register is possible

#define AVR_ATOMIC for ( uint8_t sreg_save __attribute__((__cleanup__(__iRestore))) = SREG, __ToDo = __iCliRetVal() ; __ToDo ;  __ToDo = 0 )
static inline void __iRestore(const  uint8_t *__s) { SREG = *__s; asm volatile ("" ::: "memory"); }
static inline uint8_t __iCliRetVal(void) { noInterrupts(); return 1; }

#define ARM_ATOMIC 

/******** NEW MICROS METHOD BASED ON CORE_TEENSY / LIMITED TO 16 BITS **********/
/* Copyright (c) 2008-2010 PJRC.COM, LLC
 * for the Teensy and Teensy++
 * http://www.pjrc.com/teensy/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

#if F_CPU == 16000000L
  #define TIMER0_MICROS_INC  	4
#elif F_CPU == 8000000L
  #define TIMER0_MICROS_INC  	8
#elif F_CPU == 4000000L
  #define TIMER0_MICROS_INC  	16
#elif F_CPU == 2000000L
  #define TIMER0_MICROS_INC  	32
#elif F_CPU == 1000000L
  #define TIMER0_MICROS_INC  	64
#else
#define SCoopMicros()   ((micros_t)micros())     // using the standard micros()
#warning "CPU Frequence not supported by the new micros() function"
#endif

#if defined(CORE_TEENSY)
extern volatile unsigned long timer0_micros_count;   // this variable is incremented by timer 0 overflow
static inline micros_t SCoopMicros16(void) __attribute__((always_inline));
static inline micros_t SCoopMicros16(void) // same as standrad PJRC micros, but in 16 bits and with inlining
{	register micros_t out; 
	asm volatile(
		"in	__tmp_reg__, __SREG__"			"\n\t"
		"cli"								"\n\t"
		"in	%A0, %2"						"\n\t"
		"in	__zero_reg__, %3"				"\n\t"
		"lds	%B0, timer0_micros_count"	"\n\t"
		"out	__SREG__, __tmp_reg__"		"\n\t"
		"sbrs	__zero_reg__, %4"			"\n\t"
		"rjmp	L_%=_skip"					"\n\t"
		"cpi	%A0, 255"					"\n\t"
		"breq	L_%=_skip"					"\n\t"
		"subi	%B0, 256 - %1"				"\n\t"
	"L_%=_skip:"							"\n\t"
		"clr	__zero_reg__"				"\n\t"
		"clr	__tmp_reg__"				"\n\t"
#if F_CPU == 16000000L || F_CPU == 8000000L || F_CPU == 4000000L
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
#if F_CPU == 8000000L || F_CPU == 4000000L
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
#endif
#if F_CPU == 4000000L
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
#endif
		"or	%B0, __tmp_reg__"				"\n\t"
#endif
#if F_CPU == 1000000L || F_CPU == 2000000L
		"lsr	%A0"						"\n\t"
		"ror	__tmp_reg__"				"\n\t"
		"lsr	%A0"						"\n\t"
		"ror	__tmp_reg__"				"\n\t"
#if F_CPU == 2000000L
		"lsr	%A0"						"\n\t"
		"ror	__tmp_reg__"				"\n\t"
#endif
		"or	%B0, %A0"						"\n\t"
		"mov	%A0, __tmp_reg__"			"\n\t"
#endif
		: "=r" (out)
		: "M" (TIMER0_MICROS_INC),
		  "I" (_SFR_IO_ADDR(TCNT0)),
		  "I" (_SFR_IO_ADDR(TIFR0)), 
		  "I" (TOV0) : "r0" ); return out; }
#define SCoopMicros()   (SCoopMicros16())     // overloading the standard micros()
		  
#else // end of CORE_TEENSY. Now same job for the Arduino wiring.c library
extern volatile unsigned long timer0_overflow_count; // use this variable which is incremented at each overflow
static inline micros_t SCoopMicros16(void) __attribute__((always_inline));
static inline micros_t SCoopMicros16(void) // same as standrad PJRC micros, but in 16 bits and with inlining
{	register micros_t out ;
	asm volatile(
		"in	__tmp_reg__, __SREG__"			"\n\t"
		"cli"								"\n\t"
		"in	%A0, %2"						"\n\t"
		"in	__zero_reg__, %3"				"\n\t"
		"lds	%B0, timer0_overflow_count"	"\n\t"
		"out	__SREG__, __tmp_reg__"		"\n\t"
		"sbrs	__zero_reg__, %4"			"\n\t"
		"rjmp	L_%=_skip"					"\n\t"
		"cpi	%A0, 255"					"\n\t"
		"breq	L_%=_skip"					"\n\t"
#if F_CPU == 16000000L 
		"subi	%B0, 1"						"\n\t"
#elif F_CPU == 8000000L 
		"subi	%B0, 2"						"\n\t"
#endif		
	"L_%=_skip:"							"\n\t"
		"clr	__zero_reg__"				"\n\t"
		"clr	__tmp_reg__"				"\n\t"
#if F_CPU == 16000000L || F_CPU == 8000000L
		"lsl	%B0"						"\n\t"
		"lsl	%B0"						"\n\t"
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
#if F_CPU == 8000000L 
		"lsl	%B0"						"\n\t"
		"lsl	%A0"						"\n\t"
		"rol	__tmp_reg__"				"\n\t"
#endif
		"or	%B0, __tmp_reg__"				"\n\t"
#endif
		: "=r" (out)
		: "M" (TIMER0_MICROS_INC),
		  "I" (_SFR_IO_ADDR(TCNT0)),
		  "I" (_SFR_IO_ADDR(TIFR0)),
		  "I" (TOV0)
		: "r0" ); return out; }
#define SCoopMicros()   (SCoopMicros16())     // overloading the standard micros()
#endif // core_teensy

#endif

/********* SCOOPEVENT METHODS *******/

SCoopEvent::SCoopEvent()
{ init(NULL);                                  // initialize specific object variable
  registerThis(SCoopEventType); }              // add to list and set state to Constructed
         

  void SCoopEvent::registerThis(uint8_t type)
{ pNext = SCoopFirstItem;                      // memorize the latest item registered
  SCoopFirstItem = this;                       // point the latest item to this one
  itemType = type;                             // just to memorize the object type, as we use polymorphism 
  state = SCoopCONSTRUCTED; }                  // we are in the list and ready for a formal "init", either in the skecth or as a constructor extension
    

SCoopEvent::SCoopEvent(SCoopFunc_t func)
{ init(func); 
  registerThis(SCoopEventType); }
  
SCoopEvent::~SCoopEvent()                      // destructor : remove item from the list
{ unregisterThis(); 
if (SCoopFirstTaskItem == this) SCoopFirstTaskItem = pNext; // we do not need to change this if this is not the first task 
// below section should be in Task Destructor, but didnt work there, probleme with chaining... so I put it here...
if ((itemType == SCoopDynamicTask) || (itemType == SCoopTaskType)) {
    SCINM.targetCycleMicros -= reinterpret_cast<SCoopTask*>(this)->quantumMicros; // reduce target cycle time
	SCoopNumberTask--;	
#if SCoopYIELDCYCLE == 0	
	if (SCoopNumberTask>0) { SCINM.quantumMicrosReal = quantumMicros / SCoopNumberTask; }
#endif	
#if SCoopANDROIDMODE >=2
if (itemType == SCoopDynamicTask)  { 
   free(reinterpret_cast<SCoopTask*>(this)->pStackAddr); }
#endif
	}
}                          // remove from list
  
void SCoopEvent::unregisterThis()              // remove item from SCoop list (needed for local objects)
{SCoopEvent * ptr = SCoopFirstItem;            // lets try first one
  if (ptr == this) SCoopFirstItem = ptr->pNext;// if this item is the last one registered
  else 
  do { if (ptr->pNext==this) {                 // if the next is the one to remove
           ptr->pNext = ptr->pNext->pNext;     // skip it
           break; } }                          // we are done
  while ((ptr=ptr->pNext));                    // try next item until we find the end of the list (NULL)
  state = SCoopTERMINATED; 
  };

void SCoopEvent::init(SCoopFunc_t func)        // called by constructor, after registration
{ userFunc = func;                             // hook call for the "run", if not overriden by a virtual void in child object
  if (func != NULL) state = SCoopNEW;          // this object is ready to get started or even launched (as launch() also call start())
};


void SCoopEvent::start() {                     // launched by scheduler when calling SCINM.start()
  ifSCoopTRACE(2,"Event::start");
  if (state >= SCoopNEW) {
     setup();                                  // call the setup function, and do something only if a derived object has been created with this method.
     state = SCoopRUNNABLE; };                       
}


bool SCoopEvent::launch() {                    // launch or switch into this item or derived
//ifSCoopTRACE(2,"Event::launch");             // removed. too much printing !

  if (state & SCoopPAUSED) return false;       // check if item is suspended or not
  if (!(state & SCoopTRIGGER)) return false;
  SCoopATOMIC {
  state = SCoopRUNNING;                        // this also clear the trigger flag at the same time :)
  run(); 
  state = SCoopRUNNABLE; }                     // an event shouldnt pause itself so lets go with RUNNABLE
  return true;                                 // has been launched
 };   


#if SCoopTRACE > 0
void SCoopEvent::traceThis() {                 // declare the trace functions for debuging or printing some info by user
     SCp("this=");SCphex((ptrInt)this & 0xFFFF);
     int x = (ptrInt) SCoopGetSP();            // only place where we use SP register, just to see its value
	 SCp(" SP=");SCphex( x & 0xFFFF); }  
void SCoopEvent::trace(char * xx) { 
     traceThis();SCp(" ");SCpln(xx); }
#endif


void SCoopEvent::pause()                       // pausing an event just set the state to PAUSED
{ if (state >= SCoopRUNNABLE) { state |= SCoopPAUSED; } };

void SCoopEvent::resume()                      // resuming an event just clear the flag PAUSED ... might be not enough for user code
{ if (state & SCoopPAUSED) state &= ~SCoopPAUSED; };

bool SCoopEvent::paused()                      // just return the pause flag status
{ if (state & SCoopPAUSED) return true; else return false; } 


/********* SCoopDelay CLASS *******/               // a basic virtual timer implementation. sort of "timerdown"
                                               // very small size code in each method. compiler will probably inline and clone everything
SCoopDelay::SCoopDelay()                
{ reset(); }
  
SCoopDelay::SCoopDelay(SCDelay_t reload) 
{ set(setReload(reload)); }

SCDelay_t  SCoopDelay::setReload(SCDelay_t reload) 
{ return (reloadValue = reload); }

SCDelay_t SCoopDelay::getReload()    
{ return reloadValue; }

void SCoopDelay::initReload()       
{ set(reloadValue); }

void SCoopDelay::reload()           
{ timeValue += reloadValue; }

bool SCoopDelay::reloaded()
{ if (elapsed()) {
     reload();
	 return true; }
  return false; }

void SCoopDelay::reset()
{ set(0); }

SCDelay_t SCoopDelay::set(SCDelay_t time)
{ return (timeValue = time + SCoopDelayMillis()); };

SCDelay_t SCoopDelay::get()
{ register SCDelay_t temp =(timeValue - SCoopDelayMillis()); 
  if (temp <0) return 0; else return temp; }

SCDelay_t SCoopDelay::add(SCDelay_t time)
{ timeValue += time; return time; }

SCDelay_t SCoopDelay::sub(SCDelay_t time)
{ timeValue -= time; return time; }

bool SCoopDelay::elapsed()
{ return (get() == 0); }


/********* SCoopDelayUS CLASS *******/               // a basic virtual timer implementation. sort of "timerdown"
                                               // very small size code in each method. compiler will probably inline and clone everything
SCoopDelayus::SCoopDelayus()                
{ reset(); }
  
SCoopDelayus::SCoopDelayus(micros_t reload) 
{ set(setReload(reload)); }

micros_t  SCoopDelayus::setReload(micros_t reload) 
{ return (reloadValue = reload); }

micros_t SCoopDelayus::getReload()    
{ return reloadValue; }

void SCoopDelayus::initReload()       
{ set(reloadValue); }

void SCoopDelayus::reload()           
{ timeValue += reloadValue; }

bool SCoopDelayus::reloaded()
{ if (elapsed()) {
     reload();
	 return true; }
  return false; }

void SCoopDelayus::reset()
{ set(0); }

micros_t SCoopDelayus::set(micros_t time)
{ return (timeValue = time + SCoopMicros()); };

micros_t SCoopDelayus::get()
{ register micros_t temp =(timeValue - SCoopMicros()); 
  if (temp <0) return 0; else return temp; }

micros_t SCoopDelayus::add(micros_t time)
{ timeValue += time; return time; }

micros_t SCoopDelayus::sub(micros_t time)
{ timeValue -= time; return time; }

bool SCoopDelayus::elapsed()
{ return (get() == 0); }



/********* SCoopTimer METHODS *******/


SCoopTimer::SCoopTimer() : SCoopEvent()
{initBasic(); init(0,NULL); };

SCoopTimer::SCoopTimer(SCDelay_t period) : SCoopEvent()
{initBasic(); init(period, NULL); };

SCoopTimer::SCoopTimer(SCDelay_t period, SCoopFunc_t func) : SCoopEvent()
{initBasic(); init( period, func); }

void SCoopTimer::initBasic() {
  counter    = -1; 
  userFunc   = NULL;
  itemType   = SCoopTimerType; };

void SCoopTimer::init(SCDelay_t period, SCoopFunc_t func) {
  timer.setReload(period); timer.reset(); 
  userFunc = func; 
  if (func != NULL) 
     state = SCoopNEW; } // we can use this NEW state as the user function is now defined


void SCoopTimer::start() { 
  ifSCoopTRACE(3,"Timer::start");
  SCoopEvent::start(); 
  timer.initReload();   // make sure the timer is starting with the reload period value
  }


bool SCoopTimer::launch() 
{ if ((counter == 0) || (timer.getReload() == 0)) return false;
  if (timer.reloaded()) {
       
//ifSCoopTRACE(3,"Timer::launch/run");  // removed too much printing

	 state |= SCoopTRIGGER;
	 register bool launched = SCoopEvent::launch();
	 if ((launched ) && (counter > 0)) counter--;   
       	     
	     return launched; }                    // rearm next run time so timers are NOT desynchronized by pause(). (my default choice)       
    
  return false; };


SCDelay_t SCoopTimer::getTimeToRun() 
{ if ((counter == 0) || (timer.getReload() == 0)) return -1;
  return timer.get(); };


void SCoopTimer::setTimeToRun(SCDelay_t time)
{ timer.set(time); };


void SCoopTimer::schedule(SCDelay_t time, SCoopTimerCount_t count)
{ timer.set(timer.setReload(time)); counter = count; };


void SCoopTimer::schedule(SCDelay_t time)
{ schedule(time,-1); };


/********* SOME BASIC FUNCTIONS *******/

void SCoopMemFill(uint8_t *startp, uint8_t *endp, uint8_t v) 
{ if (startp) while (startp < endp) *startp++ = v; };  
    
ptrInt SCoopMemSearch(uint8_t *startp, uint8_t *endp, uint8_t v) 
{ uint8_t *ptr = startp;
  while (ptr < endp) if (*ptr++ != v) break;
  return ((ptrInt)ptr-(ptrInt)startp-1); 
};

/********* SCoopTASK METHODS *******/

// CONSTRUCTORS

SCoopTask::SCoopTask() : SCoopEvent()
{ initBasic(); }           


SCoopTask::SCoopTask(SCoopStack_t* stack, ptrInt size) : SCoopEvent()
{ initBasic(); init(stack,size); }     


SCoopTask::SCoopTask(SCoopStack_t* stack, ptrInt size, SCoopFunc_t func) : SCoopEvent()
{ initBasic(); init(stack,size,func); }     


void SCoopTask::initBasic() {
   SCoopNumberTask++;
   pStackAddr = NULL;
   pStack     = NULL;
   userFunc   = NULL;
   register SCoopEvent* ptr = pNext;           // point on the previous item registered in the standard item list (if any)    
   pNext = SCoopFirstTaskItem;                 // register in the task list     
   SCoopFirstTaskItem = this; 
   if (ptr != pNext) {                         // if there was another (non task) item in the list before the previous task
      SCoopFirstItem = ptr;                    // mark this item as now being the first 
      while (ptr->pNext != pNext) ptr = ptr->pNext;  // search last event or item
	  ptr->pNext = this; }                     // now points to the new task			 
   itemType = SCoopTaskType; }


void SCoopTask::init(SCoopStack_t* stack, ptrInt size)       
{ pStackAddr = (uint8_t*)stack; 
  pStack = (uint8_t*)stack + ((size-sizeof(SCoopStack_t))         // prepare task stack to the top of the space provided
#if defined(SCoop_ARM) && (SCoop_ARM == 1)
  & ~7
#endif
  );
  SCoopMemFill((uint8_t*)stack, pStack, 0x55); // fill with 0x55 patern in order to calculate StackLeft later
};

	
void SCoopTask::init(SCoopStack_t* stack, ptrInt size, SCoopFunc_t func) // only this one can be called by user
{ init(stack,size); 
  userFunc = func; 
  if (func != NULL) 
     state = SCoopNEW; }                       // we have a stack and a user function so we can "start" later.


SCoopTask::~SCoopTask(){ }                     // destructor to remove task from list .. doesnt really work with "delete"



/********* ONLY USED IF SCoopTRACE DEFINED *******/

#if SCoopTRACE > 0
void SCoopTask::trace(char * xx) { 
     SCoopEvent::traceThis();
     SCp(" @Stack=");SCphex((ptrInt)pStackAddr & 0xFFFF); 
     SCp(" pStack=");SCphex((ptrInt)pStack & 0xFFFF);      
     SCp(" ");SCpln(xx); }
#endif


/******** START and LAUNCH SECTION ****************/


void SCoopTask::start() {
 ifSCoopTRACE(3,"Task::start");
 if (pStack) {                                       // sanity check if stack has been allocated by user or constructor ...
     if ((state & SCoopNEW)) {                       // if the task context is not yet set
       ASM_ATOMIC {                                  // de activate interrupt so we can use the stack content for further copy/paste
	     SCoopSwitch(&SCINM.mainEnv,&SCINM.mainEnv);   // simulate switching but with current context : back to same place !                                               
                                     
	     if (state & SCoopRUNNABLE) {                // this will be executed only when we comeback here with a backToTask the very first time
	         startFirstLoop();                       // quite equivalent to a "setjmp" mechanism
            };                                       // never come back here then.
         
         { register uint8_t* pEnd = SCINM.mainEnv;    // 
		   register uint8_t* pSource = (uint8_t*) SCoopGetSP(); // start from the stack
		   do { *pStack-- = *pSource--; }            // we copy the stack context to the newly pStack space,  
		   while (pSource != pEnd); }                // this includes the previous return adress (@!@)
                                                     // so we ll endup just below the previous call to scoopswitch above (@\_/@)		                                             
        };                                           // we can restore interrupts as we are finished with critical stack handling
		};                                           // continue forward to launch setup
     SCoopEvent::start();                            // call the user setup function (if defined in derived object) and set object RUNNABLE     
	 quantumMicros = SCINM.startQuantum;             // initialize quantum time provided by start (xx) or by user or by default
	 SCINM.targetCycleMicros += quantumMicros;       // cumulate time to calculate target cycle time
	 prevMicros = SCoopMicros();                     // memorize time , to calculate time spent in the task and in the cycle
     timer = 0; }                                    // this will enable imediate user call to sleepSync to work properly  
} // end start()


void SCoopTask::startFirstLoop() {                   // will execute this function the first call to backToTask() made by yield()
#if SCoopTIMEREPORT > 0
  yieldMicros    = 0; maxYieldMicros = 0; 
#endif  
  state = SCoopRUNNING;                   
  while (true)  {                                    // a SCoop task will never end ...
     if (!(state & SCoopPAUSED)) {
         loop();                                     // call user function (derived virtual loop or user adress)
		 yieldInline(quantumMicros); }               // try to switch task if we reach the end of the user loop
     else yield(0);                                  // switch imediately to next task (or scheduler) if we are paused
    }
}


bool SCoopTask::launch() {
//  ifSCoopTRACE(3,"task::launch")	
	if (state & SCoopRUNNABLE) {                   // make sure the task context is setup first and start() has been called already 
       if (!(state & (SCoopPAUSED | SCoopKILLING))) 
	      { SCINM.Task = this;                     // we always can find a pointer to the current task in which we are running
            SCoopSwitch(&pStack,&SCINM.mainEnv);
		    return true; }                         // return to scheduler / yield() or cycle()
	   else prevMicros = SCoopMicros();            // just to avoid jeopardizing the cycleMicros in fact
	} else 
	   if (state & SCoopNEW) start();              // initialize context if not done in the main arduino setup() section ...
  return false; }                               

#if SCoopANDROIDMODE >= 2  
void SCoopTask::kill()                            
{ if (itemType == SCoopDynamicTask) 
     state |= (SCoopKILLING );
	 if (mySCoop.Task == this) yieldSwitch();      // quick return to main scheduler for treating the situation
}
#endif
  
  
/******** YIELD SECTION ****************/

  
  void SCoopTask::yield()                            // check if the quantum time alowed is elapsed, the switch to scheduler
  { yieldInline(quantumMicros); }
       
  
  void SCoopTask::yield(micros_t quantum)
  { yieldInline(quantum); }

  
  void SCoopTask::yieldInline(micros_t quantum)
  { if (quantum) {
       register micros_t spent = SCoopMicros() - prevMicros;
       if (spent >= quantum) yieldSpent(spent);  }   // switch makes sense	   
    else if (!SCINM.Atomic) yieldSwitch();
};     
  
  
  void SCoopTask::yieldSpent(micros_t spent)        // immediate jump back to scheduler
  { if (SCINM.Atomic) return;                      // do nothing if we are in a atomic section
#if SCoopTIMEREPORT > 0                              // verifiy if we want to measure timing  
    if (spent > maxYieldMicros) maxYieldMicros = spent;  // check max time to capture peak
    yieldMicros += spent - (yieldMicros >> SCoopTIMEREPORT); // this cumulate the time spent in the task over the 2^x last cycles
#endif
     yieldSwitch(); }

   
   void SCoopTask::yieldSwitch() { 
	register SCoopEvent* temp;
	if ((SCoopYIELDCYCLE == 1) &&                    // optimize speed by directly switching next adjacent task
	   ((temp=pNext) != NULL) &&                     // only if possible, otherwise back to main loop
       ((temp->state & (SCoopRUNNABLE | SCoopPAUSED | SCoopKILLING)) == SCoopRUNNABLE))	{   
           SCINM.Current = temp;                  
           SCINM.Task = (SCoopTask*)temp;          // lets go next
           SCoopSwitch(&(((SCoopTask*)temp)->pStack),&pStack); }    // save our context and use next one
    else {                                           // systematically return to main loop or scheduler if using cycle()
        SCINM.Task = NULL;                          
        SCoopSwitch(&SCINM.mainEnv,&pStack);  }      // save context and return to main scheduler
													 // will return here by launch() from scheduler yield() or cycle()
     prevMicros = SCoopMicros();
	};                                               // come back into the task HERE / NOW

/******** SLEEP SECTION ****************/

   
  void SCoopTask::sleep(SCDelay_t ms)                 // will replace your usual arduino "delay" function
  { sleepMs(ms,false); };

  
  void SCoopTask::sleepSync(SCDelay_t ms)             // same as Sleep but delays are not absolute but relative to previous call to SleepSync
  { sleepMs(ms, true); };
  

  void SCoopTask::sleepMs(SCDelay_t ms, bool sync) { 
   ifSCoopTRACE(3,"Task::sleepms");
   if (ms < 1) { timer.reset(); return; }
   if (sync) timer.add(ms); else timer.set(ms);
   state = SCoopWAITING;
   while (timer) yield(0); 
   state = SCoopRUNNING; }

  	
  bool SCoopTask::sleepUntil(vbool& var, SCDelay_t timeOut)  // just wait for an "external" variable to become true, with a timeout
  { register bool temp = false;
    if (timeOut) {
       timer.set(timeOut);
	   temp = true; }
	return sleepUntilBool(var, temp); }
    
  
  void SCoopTask::sleepUntil(vbool& var)       // just wait for an "external" variable to become true
  { sleepUntilBool(var, false); }
  
  
  bool SCoopTask::sleepUntilBool(vbool& var, bool checkTime) {             // just wait for an "external" variable to become true
    ifSCoopTRACE(3,"Task::sleepuntil");
	state=SCoopWAITING; 
	while(!var) {
	   yield(0);
	   if (checkTime) 
	      if (timer.elapsed()) { state = SCoopRUNNING; return false; }
	   }
    state = SCoopRUNNING;
	var=false; return true; }

  
  ptrInt SCoopTask::stackLeft() 
  { if (pStackAddr) {                          // sanity check if stack has been initialized
       return SCoopMemSearch(pStackAddr, pStack, 0x55);}
    else return 0;
  };


/********* SCoop METHODS *******/
  
SCoop::SCoop()        // constructor
  { startQuantum      = SCoopDefaultQuantum;     // every task will get the default value unless changed in their setup or with sart()
    quantumMicros     = SCoopDefaultQuantum;     // the main loop also
#if SCoopYIELDCYCLE == 0
    quantumMicrosReal = 0;                       // we do nt know yet the number of task registered
#endif
	targetCycleMicros = 0;                       //  we do nt know yet the total cycle time
#if SCoopTIMEREPORT > 0                          // verifiy if we want to measure timing  
	cycleMicros = 0; maxCycleMicros = 0; 
#endif	
    Current = NULL; 
	Task    = NULL; 
	Atomic  = 1; };                               // ensure yield is not activated
  

  void SCoop::start(micros_t cycleTime, micros_t mainLoop)   // define the total length of the cycle and the main loop quantum
  { startQuantum = (cycleTime - mainLoop) / (SCoopNumberTask); 
    quantumMicros = mainLoop;                                // specific time for main loop
    start(); }
  
  
  void SCoop::start(micros_t cycleTime)           // define the total length of the cycle for all tasks+1
  { startQuantum = cycleTime / (SCoopNumberTask+1); // consider the main loop as a task
    quantumMicros = startQuantum;                 // time for the main loop will be the same then
    start(); }
    
	
    void SCoop::start()                           // start all objects in the list
  { targetCycleMicros = quantumMicros;            // initialization
#if SCoopYIELDCYCLE == 0
    quantumMicrosReal = quantumMicros / SCoopNumberTask;  // divide time in slice, as we come back here at each task switch...
#endif
#if SCoopTRACE > 1
 	SCp("starting scheduler : ");SCp(SCoopNumberTask);SCp("+1 tasks, quantum = "); SCp(startQuantum);SCp(", main loop quantum = ");SCp(quantumMicros);
#endif
    Current = SCoopFirstItem;                     // take the first
	while (Current) {                         
	  Current->start();                           // start this task (initialize environement and call setup())
      Current = Current->pNext;  }                // take next ptr
#if SCoopTRACE > 1
	SCp(", target cycle time = ");SCpln(targetCycleMicros); // this is calculated by the task::start()
#endif
	SCINM.Atomic=0;                          // ready for switchiching task with "yield"
   };
   

  // this is the main code for the Scheduler, relying on yield() method as a state machine
  
  void SCoop::yield0()                         // can be called from where ever in order to Force the switch to next task
  { if (Task) Task->yield(0); else SCoop::yield(); }
  
  
  void SCoop::yield()                          // can be called from where ever in order to Force the switch to next task
  { if (Task) Task->yield();                   // we ve been called from a task context lets yield from there
    else {
	  if (Atomic) return;                      // self explaining
      
      register SCoopEvent* temp = SCoopFirstItem;
      while (temp != SCoopFirstTaskItem) { temp->launch(); temp = temp->pNext; }  // launch all events
	  
	  register micros_t time;
	  if (Current == NULL) {                   // a cycle is completed
	     temp = SCoopFirstTaskItem;
		 if (temp == NULL) return;             // no tasks in the list !
		    	     
		 // check overall target cycle time before launching first task
	     
#if SCoopTIMEREPORT > 0                         // verifiy if we want to measure timing , then calculate average cycle time 
	     time = SCoopMicros() - reinterpret_cast<SCoopTask*>(temp)->prevMicros; // mesure whole cycle length based on first task information
		 if (quantumMicros) {                   // check if we are supposed to spend some time in the main loop or not
             if (time < targetCycleMicros) return; }   // back in main loop() until we reach the expected target cycle time
         Current = temp;                        // we can launch this first task
         if (time > maxCycleMicros) maxCycleMicros = time; 
		 cycleMicros += (time - (cycleMicros>> SCoopTIMEREPORT));
#else
		 if (quantumMicros) {                   // check if we are supposed to spend some time in the main loop or not
     	     time = SCoopMicros() - reinterpret_cast<SCoopTask*>(temp)->prevMicros; // mesure whole cycle length based on first task information
             if (time < targetCycleMicros) return; }   // back in main loop() until we reach the expected target cycle time
         Current = temp;                        // we can launch this first task
#endif
		}
		else { // lets check intertask timing before launching the next 
#if SCoopYIELDCYCLE == 0
     		if (quantumMicrosReal) {              // check if we should spend quite some time in the main loop between 2 tasks
		       time = SCoopMicros() 
			        - reinterpret_cast<SCoopTask*>(Current)->prevMicros // give the time since last task executed
				    - reinterpret_cast<SCoopTask*>(Current)->quantumMicros; // mesure whole cycle length based on first task information
           if (time < quantumMicrosReal) return;   // back in main loop() until we reach the expected target cycle time           
			}
#endif			
		};
	  do { temp = Current; 
	     temp->launch();                     // now launch tasks from the list, and may be all in a single launch()
		 Current = temp->pNext;   
#if SCoopANDROIDMODE >= 2                    // check if we autorize the killme
		 if ((temp->state & SCoopKILLING) && 
		    (temp->itemType == SCoopDynamicTask)) {			     	     				 
			     delete temp;	
			} // killing done		 
#endif
#if SCoopYIELDCYCLE == 0	                   // back to main task if we are not in yield cycle mode
         return; 
#endif	                                       // otherwise we just go back into the main loop      
	    } while (Current);
	} 
}

  
  void SCoop::cycle() {                         // execute a complete cycle across all tasks & events
#if SCoopTRACE > 1
     SCpln("start scheduler cycle()");
#endif     
	 do { yield(); } while (Current); };


  
  
  void SCoop::sleep(SCDelay_t time)                 
{  SCoopDelay SCoopSleepTimer;
   SCoopSleepTimer = time; while (SCoopSleepTimer) SCINM.yield(); }

   
   void SCoop::delay(uint32_t ms) {
  uint32_t end = millis() + ms;
  while (millis() < end)	yield();
}


#if SCoopANDROIDMODE >= 1
SCoopTask* SCoop::startLoop(SCoopFunc_t func, uint32_t stackSize) {
	
	uint8_t *stack = (uint8_t*)malloc(stackSize);
	if (!stack) return NULL;

	SCoopTask *task = new SCoopTask((SCoopStack_t*)stack,stackSize,func);
	if (!task) {
		free(stack);
		return NULL; }

    // object now exist and is registered in the list!
	task->itemType = SCoopDynamicTask;
	return task;
};
#endif

/*************** OVERLOAD STANDARD YIELD *****************/


#if SCoopOVERLOADYIELD == 1
void yield(void) { SCINM.yield(); };              // overload standard arduino yield
void yield0(void){ SCINM.yield0(); };             // overload standard arduino yield
#endif

void __attribute__((weak)) sleep (SCDelay_t time) 
{ SCINM.sleep(time); }

/*************** FIFO *****************/

SCoopFifo::SCoopFifo(void * fifo, const uint8_t itemSize, const uint16_t itemNumber)
   { this->itemSize = itemSize;
     ptrMin = (uint8_t*)fifo;   
	 ptrIn =  (uint8_t*)fifo;   
	 ptrOut = (uint8_t*)fifo;   
     ptrMax = (uint8_t*)fifo + (itemNumber * itemSize); }


uint16_t SCoopFifo::count() {                      // return the number of item currently in the fifo
     register int16_t temp;

	 AVR_ATOMIC { temp = (ptrIn-ptrOut); }
     return (temp<0 ? (temp + ptrMax-ptrMin) : temp); }


bool SCoopFifo::put(void* var)                     // put a record in the pifo and return true if ok or false if fifo is full
   {   register uint8_t N = itemSize;
       register uint8_t* dest;
       register uint8_t* source;
	   
	   AVR_ATOMIC { dest = ptrIn; }
       register uint8_t* post = dest + N;
       if (post >= ptrMax) { post = ptrMin; }
     	   
	   AVR_ATOMIC { source = ptrOut; }
	   if (post != source) { // no overload
       	  source = (uint8_t*)var;        
          do { *dest++ = *source++; } while (--N);
          AVR_ATOMIC { ptrIn = post; }
          return true;       // ok
      } else return false; } // fifo was full


bool SCoopFifo::putChar(const uint8_t value) {
   uint8_t X=value;  return put(&X); }


bool SCoopFifo::putInt(const uint16_t value) {
   uint16_t X=value;  return put(&X); }


bool SCoopFifo::putLong(const uint32_t value) {
   uint32_t X=value;  return put(&X); }


bool SCoopFifo::get(void* var)                     // retreive one record from the fifo, if not empty otherwise return false
   { register uint8_t* In;
	 register uint8_t* source;
     
     AVR_ATOMIC { In=ptrIn; source=ptrOut; }
     if (In != source) {
       register uint8_t N = itemSize;
       register uint8_t* dest = (uint8_t*)var;      
       do { *dest++ = *source++; } while (--N) ;
       if (source >= ptrMax) { source = ptrMin; }
       AVR_ATOMIC { ptrOut = source; }
       return true;         // ok
	   } else return false; // fifo was empty
	 } 


void SCoopFifo::getYield(void* var)                // same as get(var) but wait until fifo is not empty, and calls yield()
{ register uint8_t* In;
  register uint8_t* Out;
    
  while (true) { 
     AVR_ATOMIC { In=ptrIn; Out=ptrOut; }
     if (In == Out) yield(); else break; } ;
	get(var); }


uint8_t SCoopFifo::getChar()
{ uint8_t result8; 
  getYield(&result8); return result8; }

uint16_t SCoopFifo::getInt()
{ uint16_t result16; 
  getYield(&result16); return result16; }

uint32_t SCoopFifo::getLong()
{ uint32_t result32; 
  getYield(&result32); return result32; }


uint16_t SCoopFifo::flush() {                      // empty the fifo
  ASM_ATOMIC {                                     // this will de activate interrupts
     ptrIn  = ptrMin;
     ptrOut = ptrMin; }                            // this will ACTIVATE interrupts                            
  return (ptrMax-ptrMin); }

uint16_t SCoopFifo::flushNonAtomic() {             // empty the fifo
     ptrIn  = ptrMin;
     ptrOut = ptrMin;
  return (ptrMax-ptrMin); }

  
