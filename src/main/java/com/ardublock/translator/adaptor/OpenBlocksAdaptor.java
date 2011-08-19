package com.ardublock.translator.adaptor;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.codeblocks.BlockConnector;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;

public class OpenBlocksAdaptor implements BlockAdaptor
{
	TranslatorBlockFactory translatorBlockFactory;
	public OpenBlocksAdaptor()
	{
		super();
		translatorBlockFactory = new TranslatorBlockFactory();
	}
	
	public TranslatorBlock nextTranslatorBlock(Translator translator, Long blockId)
	{
		Block block = Block.getBlock(blockId);
		blockId = block.getAfterBlockID();
		if (Block.NULL.equals(blockId))
		{
			return null;
		}
		else
		{
			block = Block.getBlock(blockId);
			TranslatorBlock translatorBlock = translatorBlockFactory.buildTranslatorBlock(translator, blockId, block.getGenusName(), block.getBlockLabel());
			return translatorBlock;
		}
	}
	
	public TranslatorBlock getTranslatorBlockAtSocket(Translator translator, Long blockId, int i)
	{
		Block block = Block.getBlock(blockId);
		if (block == null)
		{
			return null;
		}
		
		BlockConnector blockConnector = block.getSocketAt(i);
		blockId = blockConnector.getBlockID();
		if (Block.NULL.equals(blockId))
		{
			return null;
		}
		else
		{
			block = Block.getBlock(blockId);
			TranslatorBlock translatorBlock = translatorBlockFactory.buildTranslatorBlock(translator, blockId, block.getGenusName(), block.getBlockLabel());
			return translatorBlock;
		}
		
	}
	
}
