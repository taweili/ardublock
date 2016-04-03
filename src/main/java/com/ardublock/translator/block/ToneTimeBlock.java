package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ToneTimeBlock extends TranslatorBlock
{
	public ToneTimeBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException , SubroutineNotDeclaredException
	{
		TranslatorBlock pinBlock = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock freqBlock = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock timeBlock = this.getRequiredTranslatorBlockAtSocket(2);
		/* NOTE: Tone() is non-blocking but beginners assume that it is.
		 * Playing multiple notes involves extra code to wait
		 * for each note to finish before playing the next.
		 * This wait is not always what is wanted and Delay() would cause massive problems with SCoop.
		 * Should we automate this - perhaps in s different block - Note ?*/
		String ret = "tone(" + pinBlock.toCode() + ", " + freqBlock.toCode() + ", " + timeBlock.toCode() + ");\n";
		return ret;
	}
}
