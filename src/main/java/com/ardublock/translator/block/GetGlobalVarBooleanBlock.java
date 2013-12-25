package com.ardublock.translator.block;

import java.util.HashSet;
import java.util.Set;

import com.ardublock.translator.Translator;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.codeblocks.BlockConnector;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.codeblocks.Variable;

public class GetGlobalVarBooleanBlock extends TranslatorBlock
{
	protected GetGlobalVarBooleanBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		String internalVariableName = translator.getBooleanVariable(label);
		String varName = translator.getBlock(blockId).getBlockLabel();
		String labelChild = null;	// valeur de la variable				
		String kindChild = null;
		
		Iterable<RenderableBlock> allRenderableBlock = translator.getWorkspace().getRenderableBlocks();//

		//all renderable blocks listing
		for (RenderableBlock renderableBlock:allRenderableBlock)//
		{
			Block block = renderableBlock.getBlock();
			
			//search first block of each stacking
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getBlockLabel().equals(varName))
				{
					BlockConnector socketConnector = block.getSocketAt(0);
					Block blockChild = translator.getBlock(socketConnector.getBlockID());
					
					if (blockChild != null) {
						labelChild = blockChild.getBlockLabel();	// ok, donne la valeur de la variable boolenne
					}
				}
			}
		}
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			translator.addBooleanVariable(label, internalVariableName);
			String definition = internalVariableName;
			definition = "boolean " + definition;

			if (labelChild != null) {
				definition = definition + " = " + labelChild;
			}
			translator.addDefinitionCommand(definition + ";");
		}
		String ret = internalVariableName;
		return codePrefix + ret + codeSuffix;
	}

}
