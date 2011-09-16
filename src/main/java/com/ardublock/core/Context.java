package com.ardublock.core;

import java.util.HashSet;
import java.util.Set;

import com.ardublock.ui.listener.OpenblocksFrameListener;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.renderable.RenderableBlock;

public class Context
{
	private static Context singletonContext;
	
	private boolean workspaceChanged;
	private String saveFilePath;
	
	private Set<RenderableBlock> highlightBlockSet;
	private Set<OpenblocksFrameListener> ofls;
	
	final public static String APP_NAME = "TinkerKit Blocks";
	
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
	
	private Context() 
	{
		workspaceController = new WorkspaceController();
		workspaceController.resetWorkspace();
		workspaceController.resetLanguage();
		workspaceController.setLangDefDtd(this.getClass().getResourceAsStream(Configuration.LANG_DTD_PATH));
		workspaceController.setLangDefStream(this.getClass().getResourceAsStream(Configuration.ARDUBLOCK_LANG_PATH));
		workspaceController.loadFreshWorkspace();
		workspaceChanged = false;
		saveFilePath = null;
		highlightBlockSet = new HashSet<RenderableBlock>();
		ofls = new HashSet<OpenblocksFrameListener>();
	}

	public WorkspaceController getWorkspaceController() {
		return workspaceController;
	}

	public boolean isWorkspaceChanged()
	{
		return workspaceChanged;
	}

	public void setWorkspaceChanged(boolean workspaceChanged) {
		this.workspaceChanged = workspaceChanged;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
	
	public String makeFrameTitle()
	{
		String title = APP_NAME + " ";
		if (saveFilePath == null)
		{
			title = title + "untitled";
		}
		else
		{
			title = title + saveFilePath;
		}
		if (workspaceChanged)
		{
			title = title + " *";
		}
		return title;
	}
	
	public void highlightBlock(RenderableBlock block)
	{
		block.updateInSearchResults(true);
		highlightBlockSet.add(block);
	}
	
	public void cancelHighlightBlock(RenderableBlock block)
	{
		block.updateInSearchResults(false);
		highlightBlockSet.remove(block);
	}
	
	public void resetHightlightBlock()
	{
		for (RenderableBlock rb : highlightBlockSet)
		{
			rb.updateInSearchResults(false);
		}
		highlightBlockSet.clear();
	}
	
	public void registerOpenblocksFrameListener(OpenblocksFrameListener ofl)
	{
		ofls.add(ofl);
	}
	
	public void didSave()
	{
		for (OpenblocksFrameListener ofl : ofls)
		{
			ofl.didSave();
		}
	}
	
	public void didLoad()
	{
		for (OpenblocksFrameListener ofl : ofls)
		{
			ofl.didLoad();
		}
	}
	
	public void didGenerate(String sourcecode)
	{
		for (OpenblocksFrameListener ofl : ofls)
		{
			ofl.didGenerate(sourcecode);
		}
	}
}
