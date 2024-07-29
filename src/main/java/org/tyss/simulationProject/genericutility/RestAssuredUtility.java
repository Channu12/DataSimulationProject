package org.tyss.simulationProject.genericutility;

import static io.restassured.RestAssured.given;

import java.io.File;

import io.restassured.RestAssured;

public class RestAssuredUtility {
	FileUtility fileUtility = new FileUtility();

	public void pushTransactionsToDB(String filePath) {
		RestAssured.baseURI = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "url");
		given().multiPart(new File(filePath)).when().post("/transactions");
		UtilityObjectClass.getExtentTest().info("Transactions pushed to DB.");
	}
	
	public void getAndVerifyTransaction(String transactionId) {
		RestAssured.baseURI = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "url");
		RestAssured.when().get("/http://49.249.29.5:8091/transaction?"+transactionId+"");
		UtilityObjectClass.getExtentTest().info(transactionId+" is verified in API Layer.");
	}
}
