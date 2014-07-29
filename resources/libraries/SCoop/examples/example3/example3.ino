//EXAMPLE 3
// footprint in memory 

#define WITH_SCOOP 1   // put 0 or 1 and compile this sketch to see the difference on the binary sketch size in byte

// AVR:
// about 1838 bytes FLASH + 87 RAM + 150 RAM stack for this basic example, 
// without dynamic task creation (SCoopANDROIDMODE == 0), 
// and without measurment variables SCoopTIMEREPORT == 0
// and in fast cyclemode SCoopYIELDCYCLE == 1
//
// about 1970 (+132) FLASH with SCoopYIELDCYCLE == 0 (come back in loop at each yield())
//
// about 2074 (+104) FLASH with SCoopTIMEREPORT == 1 (add measurement variable and related code for average)
//
// about 2438 (+364) FLASH with SCoopANDROIDMODE == 1 (add dynamica task allocation and kill (malloc, new, delete...)
//
// ARM 4920 FLASH minimum
//
//

volatile uint32_t count=0;

#if WITH_SCOOP == 1

#include <SCoop.h>

defineTaskLoop(task1) { count++; }

#else

void __attribute__((noinline)) increment() { count++; } 

#endif

void setup() { 
#if WITH_SCOOP ==1
  mySCoop.start();
#else
  count=0;
#endif
}

void loop() { 
#if WITH_SCOOP == 1
  mySCoop.yield(); 
#else
  increment();
#endif
}

