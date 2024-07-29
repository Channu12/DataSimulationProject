package org.tyss.simulationProject.testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.tyss.simulationProject.genericutility.BaseClass;
import org.tyss.simulationProject.genericutility.UtilityObjectClass;
import org.tyss.simulationProject.objectrepository.HomePage;

@Listeners(org.tyss.simulationProject.genericutility.ListenerImplementationClass.class)
public class SimulateTransactionsTest extends BaseClass {

	@Test
	public void simulateTransactionsTest() throws InterruptedException {

		//Generate Json Files
		String excelFilePath = ".\\src\\test\\resources\\TestDataFiles\\Transaction Details.xlsx";
		String sheetName = "Transaction Details";
		String jsonSchemaPath = ".\\src\\test\\resources\\TestDataFiles\\Transaction Json Schema.txt";
		List<String> transactionsList = excelUtility.generateAndWriteDataToExcel(excelFilePath, sheetName);
		List<Map<String, String>> entireData = excelUtility.getdataFromExcel(excelFilePath, sheetName);
		String jsonStaticData = fileUtility.readTextFile(jsonSchemaPath);
		String copyStaticData = jsonStaticData;
		
		List<String> listOfJson = new ArrayList<String>();
		//String finalData = "";
		for (Map<String, String> map : entireData) {
			for (Entry<String, String> singleRowData : map.entrySet()) {
				jsonStaticData = jsonStaticData.replaceAll(singleRowData.getKey().trim(), singleRowData.getValue().trim());
			}
			listOfJson.add(jsonStaticData);
			//	finalData = finalData+jsonStaticData;
			jsonStaticData = copyStaticData;
		}

		// Get jsons file path
		String jsonFilePath = fileUtility.writeTextToFile(listOfJson.toString());
		UtilityObjectClass.getExtentTest().info("Json file Generated."+jsonFilePath);
		System.out.println(jsonFilePath);

		//		postRequest(txtFilePath);
		restAssuredUtility.pushTransactionsToDB(jsonFilePath);
		webDriverUtility.refreshBrowserWindow(driver);

		Thread.sleep(1000);	
		for (String transactionId : transactionsList) {
			
			// Verification in GUI Layer
			HomePage homepage = new HomePage(driver);
			webDriverUtility.clickOnELement(driver, homepage.getAllTransactionsLink());
			Thread.sleep(500);
			webDriverUtility.clearAndEnterInputIntoElement(driver, transactionId, homepage.getSearchTextField());
			webDriverUtility.verifyElementIsDisplayed(driver, homepage.getTransactionId(transactionId));
			
			// Verification in DB Layer
			databaseUtilty.verifyColumnContainsData(UtilityObjectClass.getDbConnection(), "transaction", "transaction_id", transactionId);

			// Verification in API Layer
			restAssuredUtility.getAndVerifyTransaction(transactionId);
		}
	}
}
