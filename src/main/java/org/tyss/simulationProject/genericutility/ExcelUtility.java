package org.tyss.simulationProject.genericutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	public JavaUtility javaUtility = new JavaUtility();

	public String getDataFromExcelCellBasedOnUniqueDataAndHeader(String excelPath, String sheetName,
			String uniqueData, String header) throws EncryptedDocumentException, IOException {
		String value = "";
		FileInputStream fis = new FileInputStream(excelPath);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowCount; i++) {
			String actualUniqueData = sheet.getRow(i).getCell(0).getStringCellValue();
			if (actualUniqueData.equalsIgnoreCase(uniqueData)) {
				int columnCount = sheet.getRow(i).getPhysicalNumberOfCells();
				for (int j = 1; j < columnCount; j++) {
					String actualHeader = sheet.getRow(0).getCell(j).getStringCellValue();
					if (actualHeader.equalsIgnoreCase(header)) {
						value = sheet.getRow(i).getCell(j).getStringCellValue();
						break;
					}
				}
				if (!value.isEmpty()) {
					break;
				}
			}
		}
		workbook.close();
		fis.close();
		return value;
	}

	public List<String> generateAndWriteDataToExcel(String filePath, String sheetName){
		InputStream inputStream;
		List<String> transactionsList = new LinkedList<String>();
		try {
			inputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			DataFormatter df = new DataFormatter();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			int rowCount = sheet.getPhysicalNumberOfRows();
			int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

			int tatDateColumn = 0 ;
			for (int i = 0; i < columnCount; i++) {
				if (df.formatCellValue(sheet.getRow(0).getCell(i), evaluator).trim().equalsIgnoreCase("TAT DATE")) {
					tatDateColumn = i;
					break;
				}
			}

			for (int i = 1; i < rowCount; i++) {
				String tatDate = df.formatCellValue(sheet.getRow(i).getCell(tatDateColumn), evaluator).trim();
				String transactionId = "TR"+javaUtility.generateDateWithOffset(tatDate,"yyyyMMddhhmmssSSS");
				transactionsList.add(transactionId);
				String transctionDate = javaUtility.generateDateWithOffset(tatDate,"dd-MM-yyyy") ;
				String transactionTime = javaUtility.generateDateWithOffset(tatDate,"hh:mm:ss:SSS");

				for (int j = 0; j < columnCount; j++) {
					String cellData = df.formatCellValue(sheet.getRow(0).getCell(j), evaluator);

					if (cellData.equalsIgnoreCase("TRANSACTION ID")) {
						sheet.getRow(i).getCell(j).setCellValue(transactionId);
					} else if(cellData.equalsIgnoreCase("TRANSACTION DATE")) {
						sheet.getRow(i).getCell(j).setCellValue(transctionDate);
					} else if(cellData.equalsIgnoreCase("TRANSACTION TIME")) {
						sheet.getRow(i).getCell(j).setCellValue(transactionTime);
					}
				}
				Thread.sleep(100);
			}
			OutputStream outputStream = new FileOutputStream(filePath);
			workbook.write(outputStream);
			workbook.close();
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactionsList;
	}

	public List<Map<String, String>> getdataFromExcel(String excelPath, String sheetName) {
		List<Map<String, String>> entireData = new LinkedList<Map<String, String>>();

		try {
			InputStream inputStream = new FileInputStream(excelPath);
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			DataFormatter df = new DataFormatter();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			int rowCount = sheet.getLastRowNum();
			for (int i = 0; i < rowCount; i++) {
				Map<String, String> singleRowData = new LinkedHashMap<String, String>();
				int columnCount = sheet.getRow(i).getLastCellNum();
				for (int j = 0; j < columnCount; j++) {
					singleRowData.put(df.formatCellValue(sheet.getRow(0).getCell(j), evaluator), df.formatCellValue(sheet.getRow(i+1).getCell(j), evaluator));
				}
				entireData.add(singleRowData);
			}
			workbook.close();
			inputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return entireData;
	}
}
