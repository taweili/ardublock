package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.workspace.Workspace;

public class GenerateCodeButtonListener implements ActionListener
{
	private JFrame parentFrame;
	private Context context;
	private Workspace workspace; 
	private ResourceBundle uiMessageBundle;
	
	public GenerateCodeButtonListener(JFrame frame, Context context)
	{
		this.parentFrame = frame;
		this.context = context;
		workspace = context.getWorkspaceController().getWorkspace();
		uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		boolean success;
		success = true;
		Translator translator = new Translator(workspace);
		translator.reset();
		
		Iterable<RenderableBlock> renderableBlocks = workspace.getRenderableBlocks();
		
		Set<RenderableBlock> loopBlockSet = new HashSet<RenderableBlock>();
		Set<RenderableBlock> subroutineBlockSet = new HashSet<RenderableBlock>();
		StringBuilder code = new StringBuilder();
		
		
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
					String functionName = block.getBlockLabel().trim();
					try
					{
						translator.addFunctionName(block.getBlockID(), functionName);
					}
					catch (SubroutineNameDuplicatedException e1)
					{
						context.highlightBlock(renderableBlock);
						//find the second subroutine whose name is defined, and make it highlight. though it cannot happen due to constraint of OpenBlocks -_-
						JOptionPane.showOptionDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.subroutineNameDuplicated"), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
						return ;
					}
					subroutineBlockSet.add(renderableBlock);
				}
				
			}
		}
		if (loopBlockSet.size() == 0)
		{
			JOptionPane.showOptionDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.noLoopFound"), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			return ;
		}
		if (loopBlockSet.size() > 1)
		{
			
			for (RenderableBlock rb : loopBlockSet)
			{
				context.highlightBlock(rb);
			}
			JOptionPane.showOptionDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.multipleLoopFound"), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			return ;
		}
		

		try
		{
			for (RenderableBlock renderableBlock : loopBlockSet)
			{
				Block loopBlock = renderableBlock.getBlock();
				code.append(translator.translate(loopBlock.getBlockID()));
			}
			
			for (RenderableBlock renderableBlock : subroutineBlockSet)
			{
				Block subroutineBlock = renderableBlock.getBlock();
				code.append(translator.translate(subroutineBlock.getBlockID()));
			}
			
			code.insert(0, translator.genreateHeaderCommand());
		}
		catch (SocketNullException e1)
		{
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
			JOptionPane.showOptionDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.socketNull"), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
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
		catch (SubroutineNotDeclaredException e3)
		{
			e3.printStackTrace();
			success = false;
			Long blockId = e3.getBlockId();
			Iterable<RenderableBlock> blocks = workspace.getRenderableBlocks();
			for (RenderableBlock renderableBlock3 : blocks)
			{
				Block block2 = renderableBlock3.getBlock();
				if (block2.getBlockID().equals(blockId))
				{
					context.highlightBlock(renderableBlock3);
					break;
				}
			}
			JOptionPane.showOptionDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.subroutineNotDeclared"), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
			
		}
		
		if (success)
		{
			if (!context.isInArduino())
			{
				System.out.println(code.toString());
			}
			
			context.didGenerate(code.toString());
			
			//for test by HE Qichen
			//System.out.println(code);
		}
	}
}
