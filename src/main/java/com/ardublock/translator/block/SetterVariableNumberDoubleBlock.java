package com.ardublock.translator.block;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SetterVariableNumberDoubleBlock extends TranslatorBlock
{
  private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
  
  public SetterVariableNumberDoubleBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
  {
    super(blockId, translator, codePrefix, codeSuffix, label);
  }

  @Override
  public String toCode() throws SocketNullException, SubroutineNotDeclaredException
  {
    TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
    if (!(tb instanceof VariableNumberDoubleBlock)) {
      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.double_number_var_slot"));
    }
    
    String ret = tb.toCode();
    tb = this.getRequiredTranslatorBlockAtSocket(1);
    ret = ret + " = " + tb.toCode() + " ;\n";
    //ret = ret + " = " + "(double)" + tb.toCode() + " ;\n";
    return ret;
  }

}