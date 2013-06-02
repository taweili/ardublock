package com.ardublock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import processing.app.Editor;
import com.ardublock.core.Context;

import processing.app.tools.Tool;

import com.ardublock.ui.ArduBlockToolFrame;
import com.ardublock.ui.listener.OpenblocksFrameListener;

public class ArduBlockTool implements Tool, OpenblocksFrameListener
{
	static Editor editor;
	static ArduBlockToolFrame openblocksFrame;
	
	public void init(Editor editor) {
		if (ArduBlockTool.editor == null )
		{
			ArduBlockTool.editor = editor;
			ArduBlockTool.openblocksFrame = new ArduBlockToolFrame();
			ArduBlockTool.openblocksFrame.addListener(this);
			Context context = Context.getContext();
			String arduinoVersion = this.getArduinoVersion();
			context.setInArduino(true);
			context.setArduinoVersionString(arduinoVersion);
			System.out.println("Arduino Version: " + arduinoVersion);
		}
	}

	public void run() {
		try {
			ArduBlockTool.editor.toFront();
			ArduBlockTool.openblocksFrame.setVisible(true);
			ArduBlockTool.openblocksFrame.toFront();
		} catch (Exception e) {
			
		}
	}

	public String getMenuTitle() {
		return Context.APP_NAME;
	}

	public void didSave() {
		
	}
	
	public void didLoad() {
		
	}
	
	public void didGenerate(String source) {
		ArduBlockTool.editor.setText(source);
		ArduBlockTool.editor.handleExport(false);
	}
	
	private final static String ARDUINO_VERSION_UNKNOWN = "unknown";
	private String getArduinoVersion()
	{
		Context context = Context.getContext();
		File versionFile = context.getArduinoFile("lib/version.txt");
		if (versionFile.exists())
		{
			try
			{
				InputStream is = new FileInputStream(versionFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String line = reader.readLine();
				if (line == null)
				{
					return ARDUINO_VERSION_UNKNOWN;
				}
				line = line.trim();
				if (line.length() == 0)
				{
					return ARDUINO_VERSION_UNKNOWN;
				}
				return line;
				
			}
			catch (FileNotFoundException e)
			{
				return ARDUINO_VERSION_UNKNOWN;
			}
			catch (UnsupportedEncodingException e)
			{
				return ARDUINO_VERSION_UNKNOWN;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return ARDUINO_VERSION_UNKNOWN;
			}
		}
		else
		{
			return ARDUINO_VERSION_UNKNOWN;
		}
		
	}
}