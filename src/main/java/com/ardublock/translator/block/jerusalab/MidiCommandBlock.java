package com.ardublock.translator.block.jerusalab;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;

public class MidiCommandBlock extends TranslatorBlock
{
    public MidiCommandBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    public String toCode()
        throws SocketNullException, SubroutineNotDeclaredException
    {
        translator.addSetupCommand("Serial.begin(115200);");

        translator.addDefinitionCommand(midiFunctions);
		translator.addDefinitionCommand("#define MIDI_C 0\n#define MIDI_CS 1\n#define MIDI_D 2\n#define MIDI_DS 3\n#define MIDI_E 4\n#define MIDI_F 5\n#define MIDI_FS 6\n#define MIDI_G 7\n#define MIDI_GS 8\n#define MIDI_A 9\n#define MIDI_AS 10\n#define MIDI_B 11\n#define MIDI_OCTAVE 12");
		
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
        String note = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(1);
        int octave = Integer.parseInt(translatorBlock.toCode());
		translatorBlock = getRequiredTranslatorBlockAtSocket(2);
        int channel = Integer.parseInt(translatorBlock.toCode());
		translatorBlock = getRequiredTranslatorBlockAtSocket(3);
        int velocity = Integer.parseInt(translatorBlock.toCode());


		return " midi_note_on(" + constrain(channel, 0, 16) + ", MIDI_" + note + " + " + constrain(octave, 0, 9) + " * MIDI_OCTAVE " + ", " + constrain(velocity, 0, 127) + ");\n";
    }

    private static final String midiFunctions = 
		"void midi_note_off(byte channel, byte key, byte velocity) {\n" + 
		"	midi_command(0x80, channel, key, velocity);\n" + 
		"}\n\n" +

		"void midi_note_on(byte channel, byte key, byte velocity) {\n" + 
		"	midi_command(0x90, channel, key, velocity);\n" +
		"}\n\n" +

		"void midi_command(byte command, byte channel, byte param1, byte param2) {\n" + 
		"	Serial.write(command | (channel & 0x0F));\n" + 
		"	Serial.write(param1 & 0x7F);\n" + 
		"	Serial.write(param2 & 0x7F);\n" +
		"}\n";
		
	private int constrain(int val, int min, int max) {
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
}
