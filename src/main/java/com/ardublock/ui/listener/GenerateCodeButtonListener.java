package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.workspace.Workspace;

public class GenerateCodeButtonListener implements ActionListener
{
	private JFrame parentFrame;
	private Context context;
	private Workspace workspace; 
	
	public GenerateCodeButtonListener(JFrame frame, Context context)
	{
		this.parentFrame = frame;
		this.context = context;
		workspace = context.getWorkspaceController().getWorkspace();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		boolean success;
		success = true;
		Translator translator = new Translator(workspace);
		
		Iterable<RenderableBlock> renderableBlocks = workspace.getRenderableBlocks();
		
		Set<RenderableBlock> loopBlockSet = new HashSet<RenderableBlock>();
		Set<RenderableBlock> subroutineBlockSet = new HashSet<RenderableBlock>();
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
				if (block.getGenusName().equals("subroutine"))
				{
					subroutineBlockSet.add(renderableBlock);
					translator.addFunctionName(block.)
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
			try
			{
				code = translator.translate(loopBlock.getBlockID());
			}
			catch (SocketNullException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				success = false;
				Long blockId = e1.getBlockId();
				Iterable<RenderableBlock> blocks = workspace.getRenderableBlocks();
				for (RenderableBlock renderableBlock2 : blocks)
				{
					Block block2 = renderableBlock2.getBlock();
					if (block2.getBlockID().equals(blockId))
					{
						context.highlightBlock(renderableBlock2);
						break;
					}
				}
				JOptionPane.showOptionDialog(parentFrame, "socket null", "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			}
			catch (BlockException e2)
			{
				e2.printStackTrace();
				success = false;
				Long blockId = e2.getBlockId();
				Iterable<RenderableBlock> blocks = workspace.getRenderableBlocks();
				for (RenderableBlock renderableBlock2 : blocks)
				{
					Block block2 = renderableBlock2.getBlock();
					if (block2.getBlockID().equals(blockId))
					{
						context.highlightBlock(renderableBlock2);
						break;
					}
				}
				JOptionPane.showOptionDialog(parentFrame, e2.getMessage(), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			}
		}
		
		if (success)
		{
			if (!context.isInArduino())
			{
				System.out.println(code);
			}
			
			context.didGenerate(code);
			
			//for test by HE Qichen
			//System.out.println(code);
		}
	}
}
