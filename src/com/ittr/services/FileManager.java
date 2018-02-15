package com.ittr.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class FileManager {
	File excelFile = new File("C:\\Excel Upload\\PurchaseOrder.xls");
	int row;
	int col;

	public List<Map<String, String>> getExcelSheet() throws BiffException, IOException {
		Workbook excelWorkbook = Workbook.getWorkbook(excelFile);
		Sheet excelSheet = excelWorkbook.getSheet(0);
		row = excelSheet.getRows();
		col = excelSheet.getColumns();
		List<Map<String, String>> listForExcelData = new ArrayList<Map<String, String>>();
		for (int i = 1; i < row; i++) {
			Map<String, String> excelData = new HashMap<>();
			for (int j = 0; j < col; j++) {
				Cell excelCell = excelSheet.getCell(j, i);
				if (j == 0) {
					excelData.put("ExternalOrderID", excelCell.getContents());
				} else if (j == 1) {
					excelData.put("ExternalItemID", excelCell.getContents());
				} else if (j == 2) {
					excelData.put("UnitPrice", excelCell.getContents());
				} else if (j == 3) {
					excelData.put("Quantity", excelCell.getContents());
				} else if (j == 4) {
					excelData.put("Discount", excelCell.getContents());
				} else {
					excelData.put("ProductID", excelCell.getContents());
				}
			}
			listForExcelData.add(excelData);
		}
		return listForExcelData;
	}
}
