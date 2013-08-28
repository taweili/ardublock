package com.ardublock.util.codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsReader
{
	private String xlsFilePath;
	private Sheet sheet;
	private Row row;
	
	public void setFilePath(String xlsFilePath)
	{
		this.xlsFilePath = xlsFilePath; 
	}
	
	public void reset() throws InvalidFormatException, IOException
	{
		setupXls();
		seekLineTo(0);
	}

	private void setupXls() throws IOException, InvalidFormatException
	{
		File xlsFile = new File(xlsFilePath);
		FileInputStream fis = new FileInputStream(xlsFile);
		Workbook wb = WorkbookFactory.create(fis);
		sheet = wb.getSheetAt(0);
	}
	
	private void seekLineTo(int lineId)
	{
		row = sheet.getRow(lineId);
	}
	
	public boolean hasNext()
	{
		if (row == null)
			return false;
		String firstCellContent = getCellContent(0);
		return (firstCellContent ==null || !firstCellContent.isEmpty());
	}
	
	private String getCellContent(int cellId)
	{
		Cell cell = row.getCell(cellId);
		if (cell == null)
			return null;
		return cell.getStringCellValue().trim();
	}

	public BlockDescription nextBlockDescription()
	{
		BlockDescription blockDesc = new BlockDescription();
		blockDesc.setBlockType(getCellContent(0));
		blockDesc.setBlockColor(getCellContent(1));
		blockDesc.setBlockGenusName(getCellContent(2));
		blockDesc.setBlockImagePath(getCellContent(3));
		blockDesc.setBlockShowName(getCellContent(4));
		blockDesc.setBlockDescription(getCellContent(5));
		
		seekNextRow();
		
		return blockDesc;
	}

	private void seekNextRow()
	{
		seekLineTo(row.getRowNum() + 1);
	}
}
