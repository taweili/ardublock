package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.translator.Translator;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.workspace.Workspace;

public class GenerateCodeButtonListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		Translator translator = new Translator();
		
		Workspace workspace = Workspace.getInstance();
		Iterable<Block> blocks = workspace.getBlocks();
		for (Block block:blocks)
		{
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				System.out.println(translator.translate(block.getBlockID()));
			}
		}
	}
}
