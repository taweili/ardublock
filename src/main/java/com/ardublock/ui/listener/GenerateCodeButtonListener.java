package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
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
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		boolean success;
		success = true;
		Translator translator = new Translator(workspace);
		
		Iterable<RenderableBlock> renderableBlocks = workspace.getRenderableBlocks();
		
		// generate the infinite loop's code
		
		// declarations for the loop
		Set<RenderableBlock> loopBlockSet = new HashSet<RenderableBlock>();
		String codeLoop = null;
		String codeLoopHeader = null;
		
		// infinite loop search
		for (RenderableBlock renderableBlock:renderableBlocks)
		{
			Block block = renderableBlock.getBlock();
			
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getGenusName().equals("loop") || block.getGenusName().equals("loop2"))
				{
					loopBlockSet.add(renderableBlock);
				}
			}
		}
		// message if no infinite loop
		if (loopBlockSet.size() == 0)
		{
			JOptionPane.showOptionDialog(parentFrame, "No loop found!", "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			return ;
		}
		// message if too much infinite loops
		if (loopBlockSet.size() > 1)
		{
			
			for (RenderableBlock rb : loopBlockSet)
			{
				context.highlightBlock(rb);
			}
			JOptionPane.showOptionDialog(parentFrame, "multiple loop block found!", "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			return ;
		}
		
		// make the code of the infinite loop and the header
		for (RenderableBlock renderableBlock : loopBlockSet)
		{
			Block loopBlock = renderableBlock.getBlock();
			try
			{
				codeLoop = translator.translate(loopBlock.getBlockID());
				//codeLoopHeader = translator.translateHeader(loopBlock.getBlockID());
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
		
		// generate the infinite loop//'s code
		
		// declarations for the loop
		Set<RenderableBlock> loopParallelBlockSet = new HashSet<RenderableBlock>();
		String codeLoopParallel = "";
		//String codeLoopHeader = null;
		
		// infinite loop search
		for (RenderableBlock renderableBlock:renderableBlocks)
		{
			Block block = renderableBlock.getBlock();
			
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getGenusName().equals("loop//"))
				{
					loopParallelBlockSet.add(renderableBlock);
				}
			}
		}
		
		// make the code of the infinite loop and the header
		for (RenderableBlock renderableBlock : loopParallelBlockSet)
		{
			Block loopParallelBlock = renderableBlock.getBlock();
			try
			{
				codeLoopParallel += translator.translate(loopParallelBlock.getBlockID());
				//codeLoopHeader = translator.translateHeader(loopBlock.getBlockID());
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
		
		// generate the procedure's code
		
		// declarations for the procedures
		Set<RenderableBlock> procBlockSet = new HashSet<RenderableBlock>();
		String codeProc = "";
		
		// procedure search		
		for (RenderableBlock renderableBlock:renderableBlocks)
		{
			Block block = renderableBlock.getBlock();
			
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getGenusName().equals("procedure") || block.getGenusName().equals("procedure_interrupt"))
				{
					procBlockSet.add(renderableBlock);
				}
			}
		}
		
		// make the code of the procedure loop and the header
		for (RenderableBlock renderableBlock : procBlockSet)
		{
			Block procBlock = renderableBlock.getBlock();
			try
			{
				codeProc += translator.translateProc(procBlock.getBlockID());
				//codeProcHeader = translator.translateHeader(procBlock.getBlockID());
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
		
		// generate the header's code
		
		// declarations for the header
		Set<RenderableBlock> headerBlockSet = new HashSet<RenderableBlock>();
		String codeHeader = null;
		
		// infinite procedure search
		for (RenderableBlock renderableBlock:renderableBlocks)
		{
			Block block = renderableBlock.getBlock();
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
//				//headerBlockSet.add(renderableBlock);
//				if(block.getGenusName().startsWith("global-var-"))
//				{
					headerBlockSet.add(renderableBlock);					
//				}
			}
		}
		
		// make the code of the header for loops and procedures
		for (RenderableBlock renderableBlock : headerBlockSet)
		{
			Block headerBlock = renderableBlock.getBlock();
			try
			{
				//String inutile = translator.translateVar(headerBlock.getBlockID());
				codeHeader = translator.translateHeader(headerBlock.getBlockID());
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
		
		
		
		//Long i = 0L;
		//String codeHeader = translator.translateHeader(i);
		
		if (success)
		{
			System.out.println(codeHeader);
			System.out.println(codeLoopParallel);
			System.out.println(codeLoop);
			System.out.println(codeProc);
			String code = codeHeader + codeLoopParallel + codeLoop + codeProc;
			context.didGenerate(code);
			
			//for test by HE Qichen
			//System.out.println(code);
		}
	}
}
