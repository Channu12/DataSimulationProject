package org.tyss.simulationProject.testcases;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.tyss.simulationProject.genericutility.BaseClass;
import org.tyss.simulationProject.genericutility.UtilityObjectClass;
import org.tyss.simulationProject.objectrepository.HomePage;

@Listeners(org.tyss.simulationProject.genericutility.ListenerImplementationClass.class)
public class NewTest extends BaseClass {
	
	@Test
	public void simulateTransactionsTest() {

		//Generate Json Files
		String excelFilePath = ".\\src\\test\\resources\\TestDataFiles\\Transaction Details.xlsx";
		String sheetName = "Transaction Details";
		String jsonSchemaPath = ".\\src\\test\\resources\\TestDataFiles\\Transaction Json Schema.txt";
		List<String> transactionsList = excelUtility.generateAndWriteDataToExcel(excelFilePath, sheetName);
		List<Map<String, String>> entireData = excelUtility.getdataFromExcel(excelFilePath, sheetName);
		String jsonStaticData = fileUtility.readTextFile(jsonSchemaPath);
		String copyStaticData = jsonStaticData;

		String jsonArrayAsString = "[";
		for (Map<String, String> map : entireData) {
			for (Entry<String, String> singleRowData : map.entrySet()) {
				jsonStaticData = jsonStaticData.replaceAll(singleRowData.getKey().trim(), singleRowData.getValue().trim());
			}
			jsonArrayAsString = jsonArrayAsString+jsonStaticData.trim()+",";
			jsonStaticData = copyStaticData;
		}

		// Get jsons file path
		String jsonFilePath = fileUtility.writeTextToFile(jsonArrayAsString.substring(0,jsonArrayAsString.length()-1)+"]");
		UtilityObjectClass.getExtentTest().info("Json file Generated."+jsonFilePath);
		//		System.out.println(jsonFilePath);

		// FTP from Local to Remote
		sshUtility.ftpFromLocalToRemote(UtilityObjectClass.getSshSession(), jsonFilePath, "/home/chidori/Ninza_Kafka/kafka/bin/");

		// Push Transactions to Kafka by running shell script
		String command = "/home/chidori/Ninza_Kafka/kafka/bin/pushTxn.sh //home/chidori/Ninza_Kafka/kafka/bin/"+new File(jsonFilePath).getName();
		sshUtility.executeLinuxCommand(UtilityObjectClass.getSshSession(), command);

		webDriverUtility.refreshBrowserWindow(driver);
		webDriverUtility.waitForSeconds(1);
		for (String transactionId : transactionsList) {

			// Verification in GUI Layer
			HomePage homepage = new HomePage(driver);
			webDriverUtility.clickOnELement(driver, homepage.getAllTransactionsLink());
			webDriverUtility.waitForSeconds(1);
			webDriverUtility.clearAndEnterInputIntoElement(driver, transactionId, homepage.getSearchTextField());
			webDriverUtility.verifyElementIsDisplayed(driver, homepage.getTransactionId(transactionId));

			// Verification in DB Layer
			databaseUtilty.verifyColumnContainsData(UtilityObjectClass.getDbConnection(), "transaction", "transaction_id", transactionId);

			// Verification in API Layer
			restAssuredUtility.getAndVerifyTransaction(transactionId);
		}
	}
}
