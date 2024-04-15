package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelHelper {
	
	public static Object[][] getMapArray( String sheetName, String tableName) throws IOException{
		FileInputStream fileInputStream = new FileInputStream(
				System.getProperty("user.dir") + File.separator + "src/test/resources/testdata/TestData.xlsx");
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		int startRow, startCol=0, endRow, endCol = 0, ci, cj,endCols,endRows;
		
		Sheet sheet = workbook.getSheet(sheetName);
		 startRow = sheet.getFirstRowNum();
		 endRow = sheet.getLastRowNum();

		Cell tableStart = null;
		Cell tableEnd = null;

		for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
		    Row row = sheet.getRow(rowNum);
		    if (row != null) {
		        // Iterate through cells in the row
		        for (Cell cell : row) {
		            if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(tableName)) {
		                tableStart = cell;
		                break;
		            }
		        }
		        if (tableStart != null) {
		            break;
		        }
		    }
		}

		if (tableStart != null) {
		    startCol = tableStart.getColumnIndex();
		    startRow = tableStart.getRowIndex();
			endCols =sheet.getRow(startRow).getLastCellNum();
			endRows = sheet.getLastRowNum();
			System.out.println("startRow :" + startRow + "startCol :" + startCol);
			for (int rowNum = startRow+1; rowNum <= endRows; rowNum++) {
			    Row row = sheet.getRow(rowNum);
			    if (row != null) {
		        for (Cell cell : row) {
		            if(cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(tableName)){
		            	 tableEnd = cell;
		            	 break;
			}
		        }
		        if (tableEnd != null) {
		            break;
		        }}}
			if (tableEnd != null) {
				endRow = tableEnd.getRowIndex();
				endCol = tableEnd.getColumnIndex();
				System.out.println("startRow=" + startRow +",endRow=" + endRow + "'" + "startCol=" + startCol + ", endCol=" + endCol);
			}
			}
			ci =0;
			int k =0;
			Object[][] obj = new Object[(endRow+1) - startRow - 1][1];
			
			for(int i= startRow; i< endRow; i++, ci++) {
				cj=0;
				Map<Object, Object> datamap = new HashMap<>();
				for (int j= startCol+1; j<endCol;j++,cj++) {
					String key=sheet.getRow(startRow).getCell(j).getStringCellValue();
					String value=sheet.getRow(i+1).getCell(j).getStringCellValue();
					datamap.put(key,value);
				}
				datamap.remove("");
				obj[k][0] = datamap;
				k++;
		}

		return (obj);
}
	public static Map<String, String> getExcelData(String sheetName) throws IOException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(
					System.getProperty("user.dir") + File.separator + "src/test/resources/testdata/TestData.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HashMap<String, String> MapLocal = new HashMap<String, String>();
		try (Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			Sheet sheet = workbook.getSheet(sheetName);
			int lRow = sheet.getLastRowNum();
			for (int i = 0; i <= lRow; i++) {
				Row row = sheet.getRow(i);
				Cell keyCell = row.getCell(0);
				String keyCellValue = keyCell.getStringCellValue().trim();

				Cell valueCell = row.getCell(1);
				String valueCellValue = valueCell.getStringCellValue().trim();
				MapLocal.put(keyCellValue, valueCellValue);

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return MapLocal;
	}
	
}