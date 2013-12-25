package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.codeblocks.BlockConnector;
import javax.swing.JOptionPane;

import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;

public class ProcInterruptBlock extends TranslatorBlock
{
	public ProcInterruptBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		// function name
		String ret = "void ";
		Block bl = translator.getBlock(blockId);
		String InterruptName = bl.getBlockLabel();
		ret = ret + InterruptName;
		// arguments
		ret = ret + "() {\n";
		TranslatorBlock pinBlock = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock modeBlock = this.getRequiredTranslatorBlockAtSocket(1);
		int interrupt = Integer.parseInt(pinBlock.toCode());
		
		switch (interrupt) {
		case 2:	interrupt = 0;	break;
		case 3:	interrupt = 1;	break;
		default:interrupt = 0;
		}
		
		String setupCode = "attachInterrupt(" + interrupt + ", " + InterruptName + ", " + modeBlock.toCode() + ");";
		translator.addSetupCommand(setupCode);
		// function body
		TranslatorBlock tb=nextTranslatorBlock();		
		while (tb != null)
		{
			String a = tb.label;
			//System.out.println(a);
			ret = ret + tb.toCode();
			if (a.equals("return")) {
				throw new BlockException(blockId, "No 'return' from an interrupt function");
			}
			if (a.equals("Delay") || a.equals("delay") || a.equals("millis") || a.equals("micros")) {
				JOptionPane.showMessageDialog(null,"'delay' won't work and the value returned by 'millis' will not increment.");
			}
			if (a.equals("Serial reveive message")) {
				JOptionPane.showMessageDialog(null,"Serial data received while in the function may be lost.");
			}
			tb = tb.nextTranslatorBlock();
		}
		ret = ret + "}\n\n";
		return ret;
	}
}
