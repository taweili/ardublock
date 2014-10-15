package com.ardublock.translator.block.jerusalab;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class MidiInputPortBlock extends TranslatorBlock
{

    public MidiInputPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
        // translator.addHeaderFile("TinkerKit.h");
    }

    public String toCode()
        throws SocketNullException
    {
        return (new StringBuilder()).append(codePrefix).append(label).append(codeSuffix).toString();
    }
}
