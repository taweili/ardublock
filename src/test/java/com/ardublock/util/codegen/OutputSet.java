package com.ardublock.util.codegen;

import java.util.LinkedList;
import java.util.List;

public class OutputSet
{
	private List<String> blockGenusList = new LinkedList<String>();
	private List<String> blockDrawerList = new LinkedList<String>();
	public void addBlockGenus(String blockGenus)
	{
		blockGenusList.add(blockGenus);
	}
	public void addBlockMember(String blockMember)
	{
		blockDrawerList.add(blockMember);
	}
	public List<String> getBlockGenusList() {
		return blockGenusList;
	}
	public List<String> getBlockDrawerList() {
		return blockDrawerList;
	}
}
