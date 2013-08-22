package com.ardublock.util.codegen;

import java.util.LinkedList;
import java.util.List;

public class OutputSet
{
	private List<String> blockGenusList = new LinkedList<String>();
	public void addEntry(String blockGenus)
	{
		blockGenusList.add(blockGenus);
	}
	public List<String> getBlockGenusList() {
		return blockGenusList;
	}
}
