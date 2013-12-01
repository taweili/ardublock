package com.ardublock.translator.block.bbbb;

public class BbbbBlock
{
	public static String BBBB_DEF = 
			"int PP_PORT_LED[10] = {0, 3, 4, 5, 6, 7, 8, 9, A3, A4};\n" + 
			"int PP_PORT_IO[10] = {1, 2, 10, 11, 12, 13, A0, A1, A2, A5};\n\n" +
			"void lightOnIndicator(int i)\n" + 
			"{\n" + 
			"	if (i<1 || i>10)\n" + 
			"	{\n" + 
			"		return ;\n" + 
			"	}\n" + 
			"	--i;\n" + 
			"	pinMode(PP_PORT_LED[i], OUTPUT);\n" + 
			"	digitalWrite(PP_PORT_LED[i], HIGH);\n" + 
			"}\n\n" +
			"\n" + 
			"void bbDigitalWrite(int i, int status)\n" + 
			"{\n" + 
			"	if (i<1 || i>10)\n" + 
			"	{\n" + 
			"		return ;\n" + 
			"	}\n" + 
			"	--i;\n" + 
			"	pinMode(PP_PORT_IO[i], OUTPUT);\n" + 
			"	digitalWrite(PP_PORT_IO[i], status);\n" + 
			"}\n" + 
			"\n" + 
			"int bbAnalogRead(int i)\n" + 
			"{\n" + 
			"	if (i<7 || i>10)\n" + 
			"	{\n" + 
			"		return 0;\n" + 
			"	}\n" + 
			"	--i;\n" + 
			"	return analogRead(PP_PORT_IO[i]);\n" + 
			"}\n" + 
			"\n" + 
			"int bbDigitalRead(int i)\n" + 
			"{\n" + 
			"	if (i<1 || i>6)\n" + 
			"	{\n" + 
			"		return 0;\n" + 
			"	}\n" + 
			"	--i;\n" + 
			"	pinMode(PP_PORT_IO[i], INPUT);\n" + 
			"	return digitalRead(PP_PORT_IO[i]);\n" + 
			"}\n" + 
			"\n" + 
			"void bbAnalogWrite(int i, int status)\n" + 
			"{\n" + 
			"	if (i<3 || i>4)\n" + 
			"	{\n" + 
			"		return ;\n" + 
			"	}\n" + 
			"	--i;\n" + 
			"	pinMode(PP_PORT_IO[i], OUTPUT);\n" + 
			"	analogWrite(PP_PORT_IO[i], status);\n" + 
			"}\n" + 
			""
			;
	
	
	
}
