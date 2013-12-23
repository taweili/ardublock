package com.ardublock.translator.block;

import java.util.HashSet;
import java.util.Set;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.codeblocks.BlockConnector;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.codeblocks.Variable;

public class SetGlobalVarBooleanBlock extends TranslatorBlock
{
	protected SetGlobalVarBooleanBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String internalVariableName = translator.getBooleanVariable(label.substring(4));
		String varName = translator.getBlock(blockId).getBlockLabel();
		String labelChild = null;	// valeur de la variable				
		String kindChild = null;
		
		Iterable<RenderableBlock> allRenderableBlock = translator.getWorkspace().getRenderableBlocks();//
		//Set<RenderableBlock> headerBlockSet = new HashSet<RenderableBlock>();

		//all renderable blocks listing
		for (RenderableBlock renderableBlock:allRenderableBlock)//
		{
			Block block = renderableBlock.getBlock();
			//boolean titi = block.isVariableDeclBlock();
			
			//search first block of each stacking
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getBlockLabel().equals(varName.substring(4)))		// substring removes the four letters "set " at the begenning of the varName
				{
					BlockConnector socketConnector = block.getSocketAt(0);
					Block blockChild = translator.getBlock(socketConnector.getBlockID());
					//kindChild = socketConnector.getKind();
					
					if (blockChild != null) {
						labelChild = blockChild.getBlockLabel();	// ok, donne la valeur de la variable boolenne
					}
				}
			}
		}
		
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label.substring(4));
			translator.addBooleanVariable(label, internalVariableName);
			String definition = internalVariableName;
			definition = "boolean " + definition;

			if (labelChild != null) {
				definition = definition + " = " + labelChild;
			}
			translator.addDefinitionCommand(definition + ";");
		}
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "\t" + internalVariableName + " = " + tb.toCode() + ";\n";
		return codePrefix + ret + codeSuffix;
	}

}
