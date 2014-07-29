#ifndef SCOOP_H
#define SCOOP_H

/*****************************************************************************/
/* SCOOP LIBRARY / AUTHOR FABRICE OUDERT / GNU GPL V3                        */
/* https://code.google.com/p/arduino-scoop-cooperative-scheduler-arm-avr/    */
/* VERSION 1.2  NEW YEAR PACK 10/01/2013                                     */
/* ENJOY AND USE AT YOUR OWN RISK  :)                                        */
/* SHOULD READ USER GUIDE FIRST (@!@)                                        */
/*****************************************************************************/

/******** PREPROCESSING CONDITIONS ********/

// SCoopTRACE enables using trace("x") function in the user program, or even tracing the scheduler behavior, with following values:
// 0=disable source code for trace function, disable "scoopdebug"
// 1=enable trace("x") functions in user sketch only. 
// 2=enable the library to print traces when running mySCoop.start() 
// 3=enable the library to print traces when starting SCoopEvent or derived
// 4=enable the library to print traces when starting tasks or timers 
#define  SCoopTRACE         1     

// SCOOPTIMEREPORT enable time control variables in SCoopTask and enables cycletime average calculation. accept following values:
// 4 -> 16 cycle average, 3->8 average cycles, 2 ->4 cycle average , 1 -> 2 cycles average, 
// 0 : NO TIME MEASUREMENT , NO VARIABLES yieldMicros,cycleMicros,maxCycleMicros,maxYieldMicros...
#define  SCoopTIMEREPORT    1        // default value =1 in order to prioritize performance for the user program.
                                     // overload to 4 in ARM section, if this definition was not 0, as we have more power in ARM

#define  SCoopYIELDCYCLE    1        // if set to 1, yield will automatically launch all tasks in the list 
                                     // without coming back to main loop (like mySCoop.cycle() (faster when more than 1 task)

#define SCoopInstanceNickName    mySCoop   // could be changed for "Sch" or "SC" or whatever you prefer
#define ArduinoSchedulerNickName Scheduler // for compatibility with Arduino DUE library

#define SCoopANDROIDMODE    1        // set to 1 if we want to have the possibility to use startLoop() 
                                     // set to 2 if we also want possibility to kill the task
									 
#define  SCoopOVERLOADYIELD 1        // set to 1 to provides a yield() global function which will overload standard arduino yield()

#if (ARDUINO < 103)
#warning "V1.2 TESTED ONLY ON 1.0.3 with PanSTamp, Arduino UNO, Teensy++2.0, Teensy2.0 and Teensy3.0" 
#endif

#if (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>                // not a valid approach for ARM
#endif

#if defined (__AVR__)
#define SCoop_AVR 1                  // inform the library that the code is made for AVR

#define SCDelay_t           int32_t  // type for all the virtual timer used in scoop library (period of timer, sleep function..) 
#define SCoopTimerCount_t   int32_t  // define the type of the counter used in SCoopTimer. can be changed to int32_t instead
                                     // these 2 variables could changed to int16_t without any issue !

#define SCoopDefaultQuantum   400    // recomended before switching to next task. this provide a 5% overhead time used by scheduler, for 3 tasks+loop
#define SCoopDefaultStackSize 150    // to be experimented by user. seems enough for a task with couple of variable and a call to serial.print
#define AndroidSchedulerDefaultStack SCoopDefaultStackSize

#define micros_t     int16_t         // used for low level time handling. MUST not be changed to int32 
#define ptrInt       uint16_t        // used to typecast pointers to integer 
typedef uint8_t      SCoopStack_t;   // type definition for stack array of bytes

#elif defined(__MK20DX128__)  || defined (__SAM3X8E__)     // below code enables compiling on ARM / could be replace by #elif defined(__arm__)
#define SCoop_ARM 1                  // inform the lbrary that the code is made for ARM // not used yet

#define SCDelay_t           int32_t  // type for all the virtual timer used in scoop library (period of timer, sleep function..)
#define SCoopTimerCount_t   int32_t  // define the type of the counter used in SCoopTimer. can be changed to int32_t instead

#define SCoopDefaultQuantum   200;   // recomended before switching to next task. this provide a 1% overhead time used by scheduler, for 3 tasks+loop
#define SCoopDefaultStackSize 256    // must be a multiple of 8
#define AndroidSchedulerDefaultStack 1024 // a bit too much, just for backward compatibility reason

#define micros_t     int32_t         // all low level micros second computation will be done in 32 bit too. possibility to change to int16
#define ptrInt       uint32_t        // used to typecast pointers to integer
typedef uint64_t     SCoopStack_t   __attribute__ ((aligned (8)));

#else
#error "this library might not be compatible with this NON-AVR / ARM platform. Please experiment and report on Arduino.cc forum"
#endif

#define SCoopDelayMillis()  (SCDelay_t)millis()  // overloading and typecasting the standard millis()

// some macro for easy code writing, just to replace "Serial." ...
#define SCbegin(_X)    { Serial.begin(_X);while(!Serial); }
#define SCp(_X)        { Serial.print(_X); }
#define SCphex(_X)     { Serial.print(_X,HEX); }
#define SCpln(_X)      { Serial.println(_X); }
#define SCplnhex(_X)   { Serial.println(_X,HEX); }
#define SCkey()        { Serial.print(">?");while (!(Serial.available())) ; SCpln((uint8_t)Serial.read()); }
#define SCpkey1(_X)    { Serial.print("<");Serial.print(_X);SCkey(); }
#define SCpkey2(_X,_Y) { Serial.print("<");Serial.print(_X);Serial.print(":");Serial.print(_Y,HEX);SCkey(); }

#define ifSCoopTRACE(_X,_Y) if (SCoopTRACE > (_X)) { trace(_Y); }   // to make code more readable

/********* type defs  *******/

typedef void (*SCoopFunc_t)(void); // type definition for a pointer to a function

typedef volatile int8_t   vi8;     // hope everyone like it
typedef volatile int16_t  vi16;
typedef volatile int32_t  vi32;
typedef volatile uint8_t  vui8;
typedef volatile uint16_t vui16;
typedef volatile uint32_t vui32;
typedef volatile uint64_t vui64;   // yep you can also play with 64 bits variable on ARM platform without pain
typedef volatile int64_t  vi64;
typedef volatile boolean  vbool;

// definition of the various state of an object or task. this was prefered to "enum" for using 8 bits instead of 16bits on AVR ...
#define SCoopTERMINATED  0
#define SCoopCONSTRUCTED B00001    // object state, compatible with Java library
#define SCoopNEW         B00010    // context ready
#define SCoopRUNNABLE    B00100    // bit 2 means the task is runnable or running : setup() has been launched and context ready
#define SCoopRUNNING     B00101    // inside run() or loop()
#define SCoopWAITING     B00110    // inside a sleep method
#define SCoopPAUSED      B01000    // bit 3 means the task is paused
#define SCoopTRIGGER     B10000    // force object to be launched when calling launch()
#define SCoopKILLING    B100000    // force object to be killed by Scheduler (or paused if static)

#define SCoopEventType   1         // used to provide a statical type information to the object in the list (polymorph)
#define SCoopTaskType    2         // only used by mySCoop.start() in the library code , as virtual call were prefered elsewhere
#define SCoopTimerType   3         // not used so far
#define SCoopDynamicTask 4         // 

/********* Objects Prototypes *******/

class SCoopDelay;
class SCoopDelayus;
class SCoopEvent;
class SCoopTimer;
class SCoopTask;
class SCoop;


/********* GLOBAL VARIABLE *******/

extern SCoopEvent *  SCoopFirstItem;      // point on the latest registered item in the scheduler list
extern SCoopTask*    SCoopFirstTask;      // point on the latest registered task
extern uint8_t       SCoopNumberTask;     // the number of tasks registered (main loop() not counted)
extern void          sleep(SCDelay_t time); // (weak) in order to replace standard delay() for Arduino <150 not containing yield

extern SCoop SCoopInstanceNickName;       // one forced instance of the SCoop Scheduler
#if SCoopANDROIDMODE >= 1
extern SCoop& ArduinoSchedulerNickName;   // redundant declaration for compatibilit with the name of the Android/DUE "Scheduler"
#endif

#if SCoopOVERLOADYIELD == 1
extern void          yield(void);         // used to overload the Arduino yield "weak"
extern void          yield0(void);        // used to define our global yield(0)
#endif

#define SCoopClassOperatorEqual(name,type) name & operator=(const type rhs) { set(rhs); return *this; }; // magic statement tadah
 

/********* SCoopEVENT CLASS *******/

class SCoopEvent                      // represent an object in the SCoop list (task, event, msg...)
{ public: 
  
  SCoopEvent();                       // basic constructor to register the object in the list
  SCoopEvent(SCoopFunc_t func);       // possibility to pass the user function (instead of overloading run() )
    
  ~SCoopEvent();                      // destructor to remove item from the list
  
  void registerThis(uint8_t type)     // add this item to the list (top/first)
  __attribute__((noinline));          // well, too much code generated. better to call it
  
  void unregisterThis()               // remove the item from the list
  __attribute__((noinline));          // well, too much code generated. better to call it
  
  void init(SCoopFunc_t func);        // init the object (an extension of constructor actions). 
                                      // Set state to NEW if parameter not NULL
  
#if SCoopTRACE > 0                    // if we want to trace whats hapen during start&launch
  void traceThis();                   // specifically display the "this" pointer value, and the SP stack 
  void trace(char * xx);              // display "this , SP" and the xx string
#else
#define traceThis() ;                 // no code generated then
#define trace(x)    ;
#endif

  virtual void setup() { };           // can be overloaded by derived object. called by start()
    
  virtual void run()                  // should be overloaded if used in a derived object. other wise call the user function if defined.
  { if (userFunc) { userFunc(); } };  // called by launch(). 
   
  virtual void start();               // used to init the user object by launching its setup(). called by mySCoop.start() only, if object in state NEW
  virtual bool launch() ;             // launch or switch into this item or its derived if not paused. called by mySCoop.yield() only
  
  virtual void pause();               // put the object in a state where it will not be launched again until resumed
  virtual bool paused();              // return the paused status as a boolean
  virtual void resume();              // clear the flag and enable the task to run again
  
  void set()   { set(true); }         // force event to be launched by futur yield()
  bool set(bool val)                  // same but possibility to pass an expression
  { if (val) { state |= SCoopTRIGGER; }; return val;  }
  
  SCoopClassOperatorEqual(SCoopEvent,bool) // overload operator assignement to make things event simpler
                                       
  uint8_t getState()                  // for compatibility with Java Thread library ...
  { return state; };                  // basically return the object state. see definition section for potential values
  
  bool    isAlive()                   // means object is in the list and has been init() successfully
  { return ((state >= SCoopNEW)); }   // may be the object is not started yet. for compatibility with Java Thread library ...

  SCoopEvent *  pNext;                // point to the next object registered in the list
  uint8_t       itemType;             // place holder for recognizing item type, as we use polymorphism... 
  vui8          state;                // status of the object. see definition section fro potential values.
                                      
protected:

  SCoopFunc_t   userFunc;            // pointer to the user function to call

private:                             // nothing private
                                     // Total object variables = 6 bytes on AVR or 10 on ARM, per object instance
};                                   // end of class SCoopEvent. 
    

/********* FACILITATE EVENT DEFINITION *******/

// this creates a derived object inheriting from SCoopEvent, 
#define defineEventBegin(myevent) \
class myevent : public SCoopEvent \
{public: myevent () : SCoopEvent() { state = SCoopNEW; }; \
SCoopClassOperatorEqual(myevent,bool)

#define defineEventEnd(myevent) }; myevent myevent ;

#define defineEvent(myevent) defineEventBegin(myevent) void setup();void run(); defineEventEnd(myevent)
// user must define the run and the setup method in the myevent scope with:
// void myevent :: setup() { ... }
// void myevent :: run()   { ... }	

// Same but the user just has to put the bloc code { } for a single run method
#define defineEventRun(myevent) defineEvent(myevent) void myevent :: setup() { }; void myevent :: run()
// user must write the bloc code for run directly after this macro : 
// defineEventRun(myEvent1) { ... }

	
/********* SCOOPDELAY CLASS *******/           // a basic virtual timer solution

class SCoopDelay                               // sort of timerDown... used in SCoopTimer and SCoopTask and sleep
{ public:
  SCoopDelay();                                // basic constructor. set time to 0 . doesnt touch reload variable;
  
  SCoopDelay(SCDelay_t reload);                // possibility to define reload value, otherwse linker should remove the corresponding code avd variable
  
  SCDelay_t setReload(SCDelay_t reload);       // define the reload period for this object
  SCDelay_t getReload();                       // return the period variable
  void     initReload();                       // load the timer with its reload value
  void     reload();                           // add the reload time to the timer
  bool     reloaded();                         // return true (only once) each time when "reload" is spent;
  
  void     reset();                            // reset timer
  
  SCDelay_t set(SCDelay_t time)                // set the time value . return time value . timer will start counting down
  __attribute__((noinline));                   // we prefer a call to this method as it will take time anyway
  
  SCDelay_t get()                              // return the value corresponding to the remaining time. return 0 if negative
  __attribute__((noinline));
  
  SCDelay_t add(SCDelay_t time);               // add amount of time to timer, keep timer synchronized with millis.
  
  SCDelay_t sub(SCDelay_t time);
  
  bool     elapsed();                          // return true if timer has reached 0. doesnt reload -> use reloaded instead.
  
  operator SCDelay_t() { return get(); }       // SCoopDelay can be used in an interger expression
  

  SCoopClassOperatorEqual(SCoopDelay,SCDelay_t)  // another magic statement 
  
  SCoopDelay & operator=(const SCoopDelay & rhs) // overload operator assignement 
  { timeValue=rhs.timeValue; return *this; }
  
  SCoopDelay & operator+=(const SCDelay_t rhs)   // overload operator += to make things event simpler
  { add(rhs); return *this;}                   

  SCoopDelay & operator-=(const SCDelay_t rhs)   // overload operator -= to make things event simpler
  { sub(rhs); return *this;}
  
  SCDelay_t timeValue;                         // the realtime value of the timer
private:
  SCDelay_t reloadValue;                       // store the period for further reload.
                                               // might be removed by linker, if object instance doesnt use reload function or constructor
};

/********* SCOOPDELAYUS CLASS *******/           // a basic virtual timer solution

class SCoopDelayus                               // sort of timerDown... used in SCoopTimer and SCoopTask and sleep
{ public:
  SCoopDelayus();                                // basic constructor. set time to 0 . doesnt touch reload variable;
  
  SCoopDelayus(micros_t reload);                // possibility to define reload value, otherwse linker should remove the corresponding code avd variable
  
  micros_t setReload(micros_t reload);       // define the reload period for this object
  micros_t getReload();                       // return the period variable
  void     initReload();                       // load the timer with its reload value
  void     reload();                           // add the reload time to the timer
  bool     reloaded();                         // return true (only once) each time when "reload" is spent;
  
  void     reset();                            // reset timer
  
  micros_t set(micros_t time)                // set the time value . return time value . timer will start counting down
  __attribute__((noinline));                   // we prefer a call to this method as it will take time anyway
  
  micros_t get()                              // return the value corresponding to the remaining time. return 0 if negative
  __attribute__((noinline));
  
  micros_t add(micros_t time);               // add amount of time to timer, keep timer synchronized with millis.
  
  micros_t sub(micros_t time);
  
  bool     elapsed();                          // return true if timer has reached 0. doesnt reload -> use reloaded instead.
  
  operator micros_t() { return get(); }       // SCoopDelay can be used in an interger expression
  

  SCoopClassOperatorEqual(SCoopDelayus,micros_t)  // another magic statement 
  
  SCoopDelayus & operator=(const SCoopDelay & rhs) // overload operator assignement 
  { timeValue=rhs.timeValue; return *this; }
  
  SCoopDelayus & operator+=(const micros_t rhs)   // overload operator += to make things event simpler
  { add(rhs); return *this;}                   

  SCoopDelayus & operator-=(const micros_t rhs)   // overload operator -= to make things event simpler
  { sub(rhs); return *this;}
  
private:
  micros_t timeValue;                         // the realtime value of the timer
  micros_t reloadValue;                       // store the period for further reload.
                                               // might be removed by linker, if object instance doesnt use reload function or constructor
};

	
/********* SCoopTIMER CLASS *******/

class SCoopTimer : public SCoopEvent
{ public:
  SCoopTimer();                                // constructor
  SCoopTimer(SCDelay_t period);
  SCoopTimer(SCDelay_t period, SCoopFunc_t func);
  
  void  init(SCDelay_t period, SCoopFunc_t func); // user function only

  void setTimeToRun(SCDelay_t time);            // set the next launch time to happen in "time" ms
  SCDelay_t getTimeToRun();                     // return the value corresponding to the time when the timer will be launched
  
  void schedule(SCDelay_t time);                // plan the next launch (same as SetTimeToRun in fact, but force counter to -1
  void schedule(SCDelay_t time, SCoopTimerCount_t count); // same but with a limited number of occurences (count)

  virtual void start();                        // initialize timer and make it ready for launch
  virtual bool launch();                       // launch the run() if time ellapsed and not paused

  operator SCDelay_t(){ return getTimeToRun(); }
                                               // all other virtual methods are inherited from Event, included run()
private:
  void initBasic();
  SCoopDelay timer;                            // virtual timer used for identifting when Timer object should be launched
  SCoopTimerCount_t counter;                   // by defaut = -1. if >0 then represent the max number of futur occurences
                                               // ptrInt will force 16 bits for AVR (new in V1.2) and 32 for ARM
};


/******* MACRO FOR CREATING TIMER OBJECTS Easily ******/
// define an object class inheriting from SCoopTimer
// user has to define the object run() and setup() method only

#define defineTimerBegin_Period(timer,period) \
class timer : public SCoopTimer          \
{public: timer () : SCoopTimer( period ) { state = SCoopNEW; }; \
operator SCDelay_t(){ return getTimeToRun(); };

#define defineTimerBegin_(timer) defineTimerBegin_Period(timer,0)

#define defineTimerBegin_X(x,A,B,FUNC, ...)  FUNC  // trick to create macro with optional arguments
#define defineTimerBegin(...)  defineTimerBegin_X(,##__VA_ARGS__, \
        defineTimerBegin_Period(__VA_ARGS__),\
        defineTimerBegin_(__VA_ARGS__)) 

#define defineTimerEnd(timer) } ; timer timer ;
		
		
#define defineTimer_Period(timer,period) defineTimerBegin_Period(timer,period) void setup();void run(); defineTimerEnd(timer)

#define defineTimer_(timer) defineTimer_Period(timer,0)

#define defineTimer_X(x,A,B,FUNC, ...)  FUNC  // trick to create macro with optional arguments
#define defineTimer(...)  defineTimer_X(,##__VA_ARGS__, \
        defineTimer_Period(__VA_ARGS__),\
        defineTimer_(__VA_ARGS__)) 

// quick definition of a timer run() with the bloc code corresponding to the run() { ... }

#define defineTimerRun_Period(timer,period) defineTimerBegin_Period(timer,period) void run(); defineTimerEnd(timer) void timer :: run()

#define defineTimerRun_(timer) defineTimerRun_Period(timer ,0)

#define defineTimerRun_X(x,A,B,FUNC, ...)  FUNC  // trick to create macro with optional arguments
#define defineTimerRun(...)  defineTimerRun_X(,##__VA_ARGS__, \
        defineTimerRun_Period(__VA_ARGS__),\
        defineTimerRun_(__VA_ARGS__)) 

		
/********* SCoopTASK CLASS *******/

class SCoopTask : public SCoopEvent
{public:

  SCoopTask();                               // basic constructor
  SCoopTask(SCoopStack_t* stack, ptrInt size);
  SCoopTask(SCoopStack_t* stack, ptrInt size, SCoopFunc_t func);

  ~SCoopTask();                              // just call terminate(). should be used only if a stack is made with malloc()

  void init(SCoopStack_t* stack, ptrInt size, SCoopFunc_t func);  // user function
  
#if SCoopTRACE > 0
  void trace(char * xx);
#else
#define trace(x)  ;
#endif

  virtual void loop()                        // this is the call to user function. should be overloaded by a derived objects
  { if (userFunc) { userFunc(); } }
  
  virtual void setup() { };                  // called after start. should be overriden by child objects

  void yield();                              // this method is specific to the task. either return to scheduler, or switch to next task
  void yield0() { yield(0); }                // same but switch imediately without checking time
  void yield(micros_t quantum);              // same but force to check if the time passed is spent
  
  void sleep(SCDelay_t ms);                  // will replace your usual arduino "delay" function
                                             
  void sleepSync(SCDelay_t ms);              // same as Sleep but delays are not absolute but relative to previous call to SleepSync 

  void sleepUntil(vbool& var);               // just wait for an external variable to become true. variable will then be flaged to false

  bool sleepUntil(vbool& var, SCDelay_t timeOut);  // same, with timeout. return true, if the var was set true
  
  ptrInt stackLeft();                        // remaining stack space in this task
#if SCoopANDROIDMODE >= 2
    void kill();                               // only works in conjunction with SCoop::startLoop for dynamic tasks
#endif  
  uint8_t *    pStack;                       // always point back and forth to the SP register for this task
  uint8_t *    pStackAddr;                   // keep a copy of the lowest stack adress. only used by stackleft()
  micros_t     quantumMicros;                // copy of the SCoopQuantum global definition, so the user can overload the value in setup()
  micros_t     prevMicros;                   // memorize the value of the micros() counter when entering the task. Works with quantumMicros
  
#if SCoopTIMEREPORT > 0                      // verifiy if we want to measure timing
  micros_t     yieldMicros;                  // time spent in the task during 1 complete scheduler cycle (average)
  micros_t     maxYieldMicros;               // maximum average amount of time spent in the task
#endif
  
protected:                                   // members below can be overidedn in a user object, if neded
  
  virtual void start();                      // initialize stack environement for calling run/loop. can be called by user if needed
  virtual bool launch() ;                    // launch the task from where it was stop, or just launch run/loop or user function the first time
  
  SCoopDelay   timer;                        // virtual timer used by Sleep functions

private:  // only internal methods used to optimize code size or readabilty
  
  void initBasic();                          // called by constructors. common code to each constructor variant
  void init(SCoopStack_t* stack, ptrInt size)// only called by constructor
  __attribute__((noinline));                 // optimize code instead of speed, as this is called only once...
  
  void sleepMs(SCDelay_t ms, bool sync);      // intermediate function called by sleep and sleepsync to optimize code size
  bool sleepUntilBool(vbool& var, bool checkTime);// intermediate function called by sleepUntil
  
  void  inline yieldInline(micros_t quantum)// potentially switch to pNext object, if time quantum given is reached
  __attribute__((always_inline));  
    
  void yieldSpent(micros_t spent)            // give control back to scheduler in order to switch to next task or come bacok in main loop()
  __attribute__((noinline));                 // speed optimization not that critical, as we already decided to switch
  
  void yieldSwitch()                         // just do it when you want to go to it
  __attribute__((noinline));
  
  inline void startFirstLoop()               // only used to simplify code reading. most likely the compiler will inline them
  __attribute__((always_inline));            // internal use only, to split cod into eementary function, facilitate inlining
  
  virtual void run() { }                     // not really used by us. putting it in private should avoid further overloading for derived object.
  __attribute__((used));                     // user will get an error message if trying to overload this method. loop() should be used!
  
                                             // total variable size = 12 on AVR and 22 on ARM if TIMEREPORT = 0
};                                           // total variable size = 16 on AVR and 30 on ARM if TIMEREPORT >=1

/******* MACRO FOR CREATING ALLIGNED STACK Easily ******/

// define a stack as a static array , taking care of stack allignement
#define defineStack(x,y) static SCoopStack_t x [ (  y + sizeof(SCoopStack_t) -1)/ sizeof(SCoopStack_t)];

/******* MACRO FOR CREATING TASK OBJECTS Easily ******/

// define a new object class inheriting from the SCoopTask object
#define defineTaskBegin_Size( mytask , stacksize ) \
defineStack( mytask##Stack , stacksize )      \
class mytask : public SCoopTask \
{ public: mytask ():SCoopTask(& mytask##Stack [0] , stacksize ) { state = SCoopNEW; };

#define defineTaskEnd(mytask) } ; mytask mytask ;

#define defineTask_Size( task , stacksize) defineTaskBegin_Size( task, stacksize) void setup(); void loop(); defineTaskEnd(task)
#define defineTask_( task ) defineTask_Size( task , SCoopDefaultStackSize )

// this is used to handle multiple optional parameters in macro ... see stackoverflow forum !
#define defineTask_X(x,A,B,FUNC, ...)  FUNC  
#define defineTask(...)  defineTask_X(,##__VA_ARGS__, \
        defineTask_Size(__VA_ARGS__),\
        defineTask_(__VA_ARGS__)) 


// define a new object class inheriting from the SCoopTask object
// predefine the prototype for loop and expect the user to complete with a bloc statement { ... }

#define defineTaskLoop_Size( task , stacksize ) defineTask_Size( task , stacksize ) void task :: setup() { }; void task :: loop()

#define defineTaskLoop_( task ) defineTaskLoop_Size( task , SCoopDefaultStackSize )

// this is used to handle multiple optional parameters in macro ... see stackoverflow forum !
#define defineTaskLoop_X(x,A,B,FUNC, ...)  FUNC  
#define defineTaskLoop(...)  defineTaskLoop_X(,##__VA_ARGS__, \
        defineTaskLoop_Size(__VA_ARGS__),\
        defineTaskLoop_(__VA_ARGS__)) 

	
/******* MAIN SCoop CLASS ******/

class SCoop                            // used only once for instanciating "mySCoop"
{ public:
  SCoop();                             // basic constructor

  void start(micros_t cycleTime);      // same as start but will compute a task quantum based on provided user expected cycle time. 
  void start(micros_t cycleTime, micros_t mainLoop);      // same as start but will compute a task quantum based on provided time. 
  void start();                        // start all registered objects in the list
  void cycle();                        // execute a complete cycle (all tasks , all timer, all event before returning)
#if SCoopANDROIDMODE >= 1
  SCoopTask* startLoop(SCoopFunc_t task, uint32_t stackSize = AndroidSchedulerDefaultStack); // dynamic task creation ... !
#endif  
  void yield();                        // can be called from where ever in order to Force the switch to next task
  void yield0();                       // can be called from where ever in order to Force the switch to next task
  void sleep(SCDelay_t time);          // quick implementation of a delay() type of function, in case the standard Arduino delay doesnt contain yield()
  void delay(uint32_t ms);             // same code as in Arduino 1.5
  
  uint8_t*    mainEnv;                 // used to store the main Stack register of the main loop()
  SCoopEvent* Current;                 // curent task in the yield cycle
  SCoopTask * Task;                    // task pointer
  vui8        Atomic;
  micros_t    startQuantum;            // initial value for each task time quantum. use default, otherwise calculated by start(x)
  micros_t    quantumMicros;           // initial value for the main loop time quantum. use default, otherwise calculated by start(x)
  micros_t    targetCycleMicros;       // this represent the target cycle time declared in the start(xxx), or the sum of all quantum
#if SCoopYIELDCYCLE == 0
  micros_t    quantumMicrosReal;       // this variable is same as quantum micros but divided by number of tasks
#endif
#if SCoopTIMEREPORT > 0                // verifiy if we want to measure timing
  micros_t     cycleMicros;            // total cycle time (average) for N cycle 
  micros_t     maxCycleMicros;         // maximum average amount of time spent in a full cycle
#endif
                                       // total variable size : 13 to 19 bytes on ARM, 25 to 37 bytes on ARM
};

 // possibility to use this excellent trick for declaring non-yield section with macro SCoopATOMIC { .. code ... } credits to Dean Camera!!!
#ifndef yieldATOMIC
void    inline __decAtomic(const  uint8_t *__s) { --SCoopInstanceNickName.Atomic; }
uint8_t inline __incAtomic(void)                { ++SCoopInstanceNickName.Atomic; return 1; }
#define SCoopATOMIC for ( uint8_t __temp __attribute__((__cleanup__(__decAtomic))) = __incAtomic(); __temp  ; __temp = 0 )
#define yieldATOMIC SCoopATOMIC
#else
#define SCoopATOMIC yieldATOMIC
#endif

#ifndef yieldPROTECT
void    inline __SCoopUnprotect(uint8_t* *__s)  { uint8_t* staticFlag = *__s; *staticFlag = 0; };
#define SCoopPROTECT() static uint8_t __SCoopProtect = 0; \
register uint8_t* __temp __attribute__((__cleanup__(__SCoopUnprotect)))=& __SCoopProtect; \
while (__SCoopProtect) yield0(); __SCoopProtect = 1; 
#define yieldPROTECT() SCoopPROTECT()
#else
#define SCoopPROTECT() yieldPROTECT()
#endif

#ifndef yieldUNPROTECT
#define SCoopUNPROTECT() { __SCoopProtect = 0; }
#define yieldUNPROTECT() SCoopUNPROTECT()
#else
#define SCoopUNPROTECT() yieldUNPROTECT()
#endif

// encapsulate the next block code within noInterrupt() and interrupts() // credits to Dean Camera
#ifndef ASM_ATOMIC
void    inline __SCoopInterrupts(const  uint8_t *__s) { interrupts(); }
uint8_t inline __SCoopNoInterrupts(void)              { noInterrupts(); return 1; }
#define ASM_ATOMIC for ( uint8_t __temp __attribute__((__cleanup__(__SCoopInterrupts))) = __SCoopNoInterrupts(); __temp  ; __temp = 0 )
#endif

/*************** SCoopFIFO CLASS ******************/

// easy way of handling tx rx buffers for bytes, int or long or any structure < 256 bytes

class SCoopFifo
{public:
  SCoopFifo(void * fifo, const uint8_t itemSize, const uint16_t itemNumber);
  
  uint16_t count();                   // return number of samples available in the buffer

  bool put(void* var);                // store one sample in the buffer. return true if ok, false if buffer is full

  bool putChar(const uint8_t value);
  bool putInt(const uint16_t value);
  bool putLong(const uint32_t value);

  bool get(void* var);               // provide the older item available in the buffer. return true if ok, false if the buffer is empty

  uint8_t  getChar();                // return the next value in the fifo, as an integer depending on the itemsize. it will wait until available!!!
  uint16_t getInt();                 // return the next value in the fifo, as an integer depending on the itemsize. it will wait until available!!!
  uint32_t getLong();                // return the next value in the fifo, as an integer depending on the itemsize. it will wait until available!!!
  
  uint16_t flush();                  // empty the fifo (disable and ENABLE interrupts)
  uint16_t flushNonAtomic();         // same without touching interrupt flags
  
  operator uint16_t() { return count(); }

private:

  void getYield(void* var);          // return an item and potentially wait until it is available. calls yield() in the meantime

  uint8_t* volatile ptrIn;
  uint8_t* volatile ptrOut;
  uint8_t  itemSize;
  uint8_t* ptrMin;
  uint8_t* ptrMax;
  };

/*************** MACRO TO CREATE FIFO BUFFER and INSTANCIATE OBJECT  ******************/

#define defineFifo( name , type , number ) \
type name##type##number [ number ]; \
SCoopFifo name ( name##type##number , sizeof( type ), number );

#endif


