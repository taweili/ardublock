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
	
}
