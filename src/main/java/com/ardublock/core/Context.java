package com.ardublock.core;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.ardublock.core.exception.ArdublockStartupException;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Page;

public class Context
{
	private static Context singletonContext;
	
	
	
	public static Context getContext()
	{
		if (singletonContext == null)
		{
			synchronized (Context.class)
			{
				if (singletonContext == null)
				{
					singletonContext = new Context();
				}
			}
		}
		return singletonContext;
	}
	
	private WorkspaceController workspaceController;
	private String ardublockLangPath;
	private Element ardublockLangRoot;
	
	private Context() 
	{
		ardublockLangPath = this.getClass().getResource(Configuration.ARDUBLOCK_LANG_PATH).getFile();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			try {
				ardublockLangRoot = documentBuilder.parse(ardublockLangPath).getDocumentElement();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ArdublockStartupException();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ArdublockStartupException();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ArdublockStartupException();
		}
		
		workspaceController = new WorkspaceController();
		workspaceController.resetWorkspace();
		workspaceController.resetLanguage();
		workspaceController.setLangDefDtd(this.getClass().getResourceAsStream(Configuration.LANG_DTD_PATH));
		workspaceController.setLangDefStream(this.getClass().getResourceAsStream(Configuration.ARDUBLOCK_LANG_PATH));
		workspaceController.loadFreshWorkspace();
	}

	public WorkspaceController getWorkspaceController() {
		return workspaceController;
	}

	public Element getArdublockLangRoot() {
		return ardublockLangRoot;
	}

	public String getArdublockLangPath() {
		return ardublockLangPath;
	}
}
