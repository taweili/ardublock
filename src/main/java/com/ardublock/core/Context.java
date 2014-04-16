package com.ardublock.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import processing.app.Editor;

import com.ardublock.ui.listener.OpenblocksFrameListener;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.workspace.Workspace;

public class Context
{
	public final static String LANG_DTD_PATH = "/com/ardublock/block/lang_def.dtd";
	public final static String ARDUBLOCK_LANG_PATH = "/com/ardublock/block/ardublock.xml";
	public final static String DEFAULT_ARDUBLOCK_PROGRAM_PATH = "/com/ardublock/default.abp";
	public final static String ARDUINO_VERSION_UNKNOWN = "unknown";
	public final boolean isNeedAutoFormat = true;
	
	private static Context singletonContext;
	
	private boolean workspaceChanged;
	private boolean workspaceEmpty;
	
	private Set<RenderableBlock> highlightBlockSet;
	private Set<OpenblocksFrameListener> ofls;
	private boolean isInArduino = false;
	private String arduinoVersionString = ARDUINO_VERSION_UNKNOWN;
	private OsType osType; 

	final public static String APP_NAME = "ArduBlock";
	
	private Editor editor;
	
	public enum OsType
	{
		LINUX,
		MAC,
		WINDOWS,
		UNKNOWN,
	};
	
	private String saveFilePath;
	private String saveFileName;
	
	//final public static String VERSION_STRING = " ";
	
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
	private Workspace workspace;
	
	private Context()
	{
		workspaceController = new WorkspaceController();
		resetWorksapce();
		workspace = workspaceController.getWorkspace();
		workspaceChanged = false;
		highlightBlockSet = new HashSet<RenderableBlock>();
		ofls = new HashSet<OpenblocksFrameListener>();
		this.workspace = workspaceController.getWorkspace();
		
		isInArduino = false;
		
		osType = determineOsType();
	}
	
	public void resetWorksapce()
	{
		/*
		 * workspace = new Workspace(); workspace.reset(); workspace.setl
		 */

		// Style list
		List<String[]> list = new ArrayList<String[]>();
		String[][] styles = {};
		
		//		{ "//BlockGenus[@name[starts-with(.,\"Tinker\")]]/@color", "128 0 0" },
		//		{ "//BlockGenus[@name[starts-with(.,\"df_\")]]/@color",	"0 128 0" } };

		for (String[] style : styles) {
			list.add(style);
		}
		workspaceController.resetWorkspace();
		workspaceController.resetLanguage();
		workspaceController.setLangResourceBundle(ResourceBundle.getBundle("com/ardublock/block/ardublock"));
		workspaceController.setStyleList(list);
		workspaceController.setLangDefDtd(this.getClass().getResourceAsStream(LANG_DTD_PATH));
		workspaceController.setLangDefStream(this.getClass().getResourceAsStream(ARDUBLOCK_LANG_PATH));
		workspaceController.loadFreshWorkspace();
		
		loadDefaultArdublockProgram();
		
		saveFilePath = null;
		saveFileName = "untitled";
		workspaceEmpty = true;
	}
	
	private void loadDefaultArdublockProgram()
	{
		InputStream defaultArdublockProgram = this.getClass().getResourceAsStream(DEFAULT_ARDUBLOCK_PROGRAM_PATH);
		
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        final DocumentBuilder builder;
        final Document doc;
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.parse(defaultArdublockProgram);
			final Element projectRoot = doc.getDocumentElement();
			workspaceController.resetWorkspace();
			workspaceController.loadProjectFromElement(projectRoot);
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			workspaceController.loadFreshWorkspace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			workspaceController.loadFreshWorkspace();
		}
        
	}
	
	//determine OS
	private OsType determineOsType()
	{
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();

		if (osName.contains("win"))
		{
			return Context.OsType.WINDOWS;
		}
		if (osName.contains("linux"))
		{
			return Context.OsType.LINUX;
		}
		if(osName.contains("mac"))
		{
			return Context.OsType.MAC;
		}
		return Context.OsType.UNKNOWN;
	}
	
	public File getArduinoFile(String name)
	{
		String path = System.getProperty("user.dir");
		if (osType.equals(OsType.MAC))
		{
			String javaroot = System.getProperty("javaroot");
			if (javaroot != null)
			{
				path = javaroot;
			}
		}
		File workingDir = new File(path);
		return new File(workingDir, name);
	}

	public WorkspaceController getWorkspaceController() {
		return workspaceController;
	}
	
	public Workspace getWorkspace()
	{
		return workspace;
	}

	public boolean isWorkspaceChanged()
	{
		return workspaceChanged;
	}

	public void setWorkspaceChanged(boolean workspaceChanged) {
		this.workspaceChanged = workspaceChanged;
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
	
	public void saveArduBlockFile(File saveFile, String saveString) throws IOException
	{
		if (!saveFile.exists())
		{
			saveFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(saveFile, false);
		fos.write(saveString.getBytes("UTF8"));
		fos.flush();
		fos.close();
		didSave();
	}
	
	public void loadArduBlockFile(File savedFile) throws IOException
	{
		if (savedFile != null)
		{
			saveFilePath = savedFile.getAbsolutePath();
			saveFileName = savedFile.getName();
			workspaceController.resetWorkspace();
			workspaceController.loadProjectFromPath(saveFilePath);
			didLoad();
		}
	}
	
	public void setEditor(Editor e) {
		editor = e;
	}
	
	public Editor getEditor() {
		return editor;
	}
	
	
	public boolean isInArduino() {
		return isInArduino;
	}

	public void setInArduino(boolean isInArduino) {
		this.isInArduino = isInArduino;
	}

	public String getArduinoVersionString() {
		return arduinoVersionString;
	}

	public void setArduinoVersionString(String arduinoVersionString) {
		this.arduinoVersionString = arduinoVersionString;
	}

	public OsType getOsType() {
		return osType;
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

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	public boolean isWorkspaceEmpty() {
		return workspaceEmpty;
	}

	public void setWorkspaceEmpty(boolean workspaceEmpty) {
		this.workspaceEmpty = workspaceEmpty;
	}
}
