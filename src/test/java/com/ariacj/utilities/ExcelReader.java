package com.ariacj.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader { 




	public static List<LinkedHashMap<String, String>> readData() {

		List<LinkedHashMap<String, String>> allData = null ;
		LinkedHashMap<String, String> data = null;


		try {
			FileInputStream fis = new FileInputStream("C:\\Users\\Apurva\\eclipse\\ariacj_1.0\\src\\test\\resources\\excel\\BuildigsList_1.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();

			String header;
			List headerValue = new ArrayList();

//			for(int i=0; i<colCount; i++) {
				Row row = sheet.getRow(0);
				Cell cell1 = row.getCell(1);
				header = cell1.getStringCellValue();
				headerValue.add(header);
				//System.out.println("List = " + header);
			//}

			allData =  new ArrayList<LinkedHashMap<String,String>>();

			for(int j=1; j<=rowCount; j++) {
				Row row1=sheet.getRow(j);
				data =new LinkedHashMap<String,String>();

				for(int k=1; k==1; k++ ) {
					
					Cell cell = row1.getCell(k);
					String cellValue = cell.getStringCellValue();
					//System.out.println(headerValue.get(0) + "," + cellValue + ":" + j + ":" + k);
					data.put((String) headerValue.get(0), cellValue);
				}
				
				allData.add(data);
				

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ListIterator<LinkedHashMap<String, String>> ll=	allData.listIterator();
		//while(ll.hasNext()) {
//			System.out.println(ll.next().values());
		//}
		return allData;

		}


}

//List<LinkedHashMap<String, String>> allData = null ;
//LinkedHashMap<String, String> data = null;
//
//
//try {
//	FileInputStream fis = new FileInputStream("C:\\Users\\Apurva\\eclipse\\appiumDemo\\src\\test\\resources\\Data\\BuildigsList.xlsx");
//	XSSFWorkbook workbook = new XSSFWorkbook(fis);
//	XSSFSheet sheet = workbook.getSheetAt(0);
//	int rowCount = sheet.getLastRowNum();
//	int colCount = sheet.getRow(0).getLastCellNum();
//
//	String header;
//	List headerValue = new ArrayList();
//
//	for(int i=0; i<colCount; i++) {
//		Row row = sheet.getRow(0);
//		Cell cell1 = row.getCell(i);
//		header = cell1.getStringCellValue();
//		headerValue.add(header);
//		//System.out.println("List = " + header);
//	}
//
//	allData =  new ArrayList<LinkedHashMap<String,String>>();
//
//	for(int j=1; j<=rowCount; j++) {
//		Row row1=sheet.getRow(j);
//		data =new LinkedHashMap<String,String>();
//
//		for(int k=1; k<colCount; k++ ) {
//			
//			Cell cell = row1.getCell(k);
//			String cellValue = cell.getStringCellValue();
//			//System.out.println(headerValue.get(0) + "," + cellValue + ":" + j + ":" + k);
//			data.put((String) headerValue.get(k), cellValue);
//		}
//		
//		allData.add(data);
//		
//
//	}
//
//} catch (FileNotFoundException e) {
//	e.printStackTrace();
//} catch (IOException e) {
//	e.printStackTrace();
//}
//ListIterator<LinkedHashMap<String, String>> ll=	allData.listIterator();
//while(ll.hasNext()) {
//	System.out.println(ll.next().values());
//}
//return allData;
//
//}