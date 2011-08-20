package com.ardublock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import edu.mit.blocks.renderable.RenderableBlock;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.codeblocks.BlockConnectorShape;
import edu.mit.blocks.codeblocks.BlockGenus;
import edu.mit.blocks.codeblocks.BlockLinkChecker;
import edu.mit.blocks.codeblocks.CommandRule;
import edu.mit.blocks.codeblocks.SocketRule;
import edu.mit.blocks.controller.WorkspaceController;

import edu.mit.blocks.workspace.Page;
import edu.mit.blocks.workspace.Workspace;

import com.ardublock.core.Starter;
import com.ardublock.translator.Translator;

public class Main
{
	public static void main(String args[]) throws SAXException, IOException, ParserConfigurationException
	{
		Starter starter = new Starter();
		starter.startArdublock();
	}
	

	class fMain
	{
		WorkspaceController wc;
		Element lang;
		public void main(String args[]) throws SAXException, IOException, ParserConfigurationException
		{
			fMain main = new fMain();
			main.work();
			main.console();
		}
		private void work() throws SAXException, IOException, ParserConfigurationException
		{
			JFrame jframe = new JFrame("Ardublocks");
			wc = new WorkspaceController();
			Workspace workspace = Workspace.getInstance();
			
			//get the handle of language definition file
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			lang = documentBuilder.parse(getClass().getResource("/com/ardublock/block/ardublock_def.xml").toString()).getDocumentElement();
			
			jframe.setSize(new Dimension(640, 480));
			
			//
			BlockConnectorShape.loadBlockConnectorShapes(lang);
			BlockGenus.loadBlockGenera(lang);
			BlockLinkChecker.addRule(new CommandRule());
			BlockLinkChecker.addRule(new SocketRule());
			
			workspace.loadWorkspaceFrom(null, lang);
			
			Page page = new Page("heqichen-page");
			
			workspace.addPage(page);
			
			jframe.add(workspace);
			
			
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			jframe.setVisible(true);
		}
		
		public void console()
		{
			JFrame jframe = new JFrame();
			jframe.setLayout(new FlowLayout());
			JButton generateButton = new JButton("generate code");
			generateButton.addActionListener(new GenerateCodeListener());
			
			JButton saveButton = new JButton("save");
			saveButton.addActionListener(new SaveButtonListener());
			
			JButton openButton = new JButton("open");
			openButton.addActionListener(new OpenButtonListener());
			
			jframe.add(generateButton);
			jframe.add(saveButton);
			jframe.add(openButton);
			
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe.setSize(200, 100);
			jframe.setVisible(true);
		}
		
		private class GenerateCodeListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				Workspace workspace = Workspace.getInstance();
				Page page = workspace.getPageNamed("heqichen-page");
				Collection<RenderableBlock> rbc = page.getBlocks();
				
				
				Iterable<Block> blocks = workspace.getBlocks();
				
				for (Block block:blocks)
				{
					
					System.out.print(block.getGenusName());
					System.out.print(" from " + block.getPageLabel());
					System.out.println(" " + block.getBeforeBlockID());
					
					if (block.getBeforeBlockID() == Block.NULL)
					{
						System.out.println("root  :  " + block.getGenusName());
						if (block.getGenusName().equals("number"))
						{
							System.out.println(block.getPlug().getBlockID());
							System.out.println("***");
							Iterable<BlockConnector> ibc = block.getSockets();
							for (BlockConnector bc:ibc)
							{
								System.out.println(bc.getBlockID());
							}
							System.out.println("8*8*8*");
						}
					}
				}
				System.out.println("--------------------------------------------");
				for (RenderableBlock rb : rbc)
				{
					System.out.println(rb.getBlock().getGenusName());
					System.out.println(rb.getBlock().getBeforeBlockID());
				}
				System.out.println("===============================================");
				*/
				Translator translator = new Translator();
				
				Workspace workspace = Workspace.getInstance();
				Page page = workspace.getPageNamed("heqichen-page");
				Collection<RenderableBlock> rbc = page.getBlocks();
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
		
		public class SaveButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
//				WorkspaceController
				
			}
			
		}
		
		class OpenButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					
					//workspace.loadWorkspaceFrom(newRoot, null);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}
	}

}
