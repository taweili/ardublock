package com.ardublock.util.codegen;

import java.util.LinkedList;
import java.util.List;

public class OutputSet
{
	private List<String> blockGenusList = new LinkedList<String>();
	private List<String> blockDrawerList = new LinkedList<String>();
	private List<String> blockNameList = new LinkedList<String>();
	private List<String> blockDescriptionList = new LinkedList<String>();
	private List<String> blockTypeList = new LinkedList<String>();
	
	public void addBlockGenus(String blockGenus)
	{
		blockGenusList.add(blockGenus);
	}
	public void addBlockMember(String blockMember)
	{
		blockDrawerList.add(blockMember);
	}
	public void addBlockName(String blockName)
	{
		blockNameList.add(blockName);
	}
	public void addBlockDescription(String blockDescription)
	{
		blockDescriptionList.add(blockDescription);
	}
	public void addBlockType(String blockType)
	{
		blockTypeList.add(blockType);
	}
	public List<String> getBlockGenusList() {
		return blockGenusList;
	}
	public List<String> getBlockDrawerList() {
		return blockDrawerList;
	}
	public List<String> getBlockNameList() {
		return blockNameList;
	}
	public List<String> getBlockDescriptionList() {
		return blockDescriptionList;
	}
	public List<String> getBlockTypeList() {
		return blockTypeList;
	}
}
