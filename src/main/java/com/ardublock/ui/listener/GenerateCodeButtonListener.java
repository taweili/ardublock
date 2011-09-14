package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.workspace.Workspace;

public class GenerateCodeButtonListener implements ActionListener
{
	private JFrame parentFrame;
	private Context context;
	public GenerateCodeButtonListener(JFrame frame)
	{
		this.parentFrame = frame;
		context = Context.getContext();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Translator translator = new Translator();
		
		Workspace workspace = Workspace.getInstance();
		Iterable<RenderableBlock> renderableBlocks = workspace.getRenderableBlocks();
		Set<RenderableBlock> loopBlockSet = new HashSet<RenderableBlock>();
		String code = null;
		for (RenderableBlock renderableBlock:renderableBlocks)
		{
			Block block = renderableBlock.getBlock();
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getGenusName().equals("loop"))
				{
					loopBlockSet.add(renderableBlock);
				}
				
			}
		}
		if (loopBlockSet.size() == 0)
		{
			JOptionPane.showOptionDialog(parentFrame, "No loop found!", "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			return ;
		}
		if (loopBlockSet.size() > 1)
		{
			
			for (RenderableBlock rb : loopBlockSet)
			{
				context.highlightBlock(rb);
			}
			JOptionPane.showOptionDialog(parentFrame, "multiple loop block found!", "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			return ;
		}
		
		
		for (RenderableBlock renderableBlock : loopBlockSet)
		{
			Block loopBlock = renderableBlock.getBlock();
			code = translator.translate(loopBlock.getBlockID());
		}
		
		System.out.println(code);
	}
}
