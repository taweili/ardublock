package com.ardublock;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.workspace.Workspace;

import org.testng.annotations.*;

public class MainTest
{
	private Main main;
	private Translator translator;
	private Context context;

	private void teardown() {
		main.shutdown();
	}

	@BeforeClass
	public void setUp() throws SAXException, IOException, ParserConfigurationException
	{
		System.out.println("### setUp()");
		main = new Main();
		// main.startArdublock();
		// context = Context.getContext();
		// Workspace workspace = context.getWorkspaceController().getWorkspace();
		// translator = new Translator(workspace);
	}
	
	@Test
	public void testFiles() throws SAXException, IOException, ParserConfigurationException, SubroutineNameDuplicatedException, SocketNullException, SubroutineNotDeclaredException
	{
		System.out.println("### testFiles");
		// String savedFiles[] = {
		// 		"src/test/resources/examples/factorial.abp",
		// 		"src/test/resources/examples/single-loop.abp",
		// 		};
		// 
		// 
		// 
		// for (String savedFile : savedFiles)
		// {
		// 	translator.reset();
		// 	File file = new File(savedFile);
		// 	context.loadArduBlockFile(file);
		// 	Set<RenderableBlock> loopBlockSet = translator.findEntryBlocks();
		// 	Set<RenderableBlock> subroutineBlockSet = translator.findSubroutineBlocks();
		// 	
		// 	String code = translator.translate(loopBlockSet, subroutineBlockSet);
		// 	System.out.println(code);
		// }
	}
	
	@Test
	public void testSingleLoop() {
		System.out.println("### testSingleLoop");
		
	}
}
