package com.ardublock.util.codegen;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsReader
{
	public static void main(String args[]) throws InvalidFormatException, IOException
	{
		XlsReader me = new XlsReader();
		me.work();
	}
	
	public void work() throws InvalidFormatException, IOException
	{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("seeedstudio/test.xls");
		Workbook wb = WorkbookFactory.create(is);
		Sheet sheet = wb.getSheetAt(0);
		System.out.println(sheet.getSheetName());
		Row row = sheet.getRow(0);
		System.out.println(row.getFirstCellNum());
		Cell cell = row.getCell(0);
		System.out.println(cell.getStringCellValue());
	}
}
