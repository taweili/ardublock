package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class D53 extends TranslatorBlock
	{

		public D53(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
		{
			super(blockId, translator, codePrefix, codeSuffix, label);
		}

		@Override
		public String toCode() throws SocketNullException {
			return codePrefix + "53" + codeSuffix;
		}
		
	}