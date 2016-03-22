package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class IRREMOTE_NE_PAS_MEMORISER extends TranslatorBlock
{

	public IRREMOTE_NE_PAS_MEMORISER(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException {
		return codePrefix + "IRREMOTE_NE_PAS_MEMORISER " + codeSuffix;
	}

}
