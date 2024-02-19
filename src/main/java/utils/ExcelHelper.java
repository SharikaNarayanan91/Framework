package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	
	public static Map<String,String>getExcelData(String sheetName)throws IOException{
		FileInputStream fileInputStream=null;
		try {
		fileInputStream=new FileInputStream(System.getProperty("user.dir")+File.separator+"src/test/resources/testdata/TestData.xlsx");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		HashMap<String,String>MapLocal=new HashMap<String,String>();
		try(Workbook workbook=new XSSFWorkbook(fileInputStream)) {
			Sheet sheet =workbook.getSheet(sheetName);
			int lRow=sheet.getLastRowNum();
			for (int i=0;i<=lRow;i++) {
				Row row=sheet.getRow(i);
				Cell keyCell=row.getCell(0);
				String keyCellValue=keyCell.getStringCellValue().trim();
				
				Cell valueCell=row.getCell(1);
				String valueCellValue=valueCell.getStringCellValue().trim();
				MapLocal.put(keyCellValue, valueCellValue);
				
			}
		}catch(IOException e){
			e.printStackTrace();
			
		}
		return MapLocal;
	}

}
