package org.tyss.simulationProject.genericutility;

import java.sql.Connection;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.tyss.simulationProject.objectrepository.HomePage;
import org.tyss.simulationProject.workflowutility.CommonWorkflowsUtility;

public class BaseClass {
	public ExcelUtility excelUtility = new ExcelUtility();
	public FileUtility fileUtility = new FileUtility();
	public JavaUtility javaUtility = new JavaUtility();
	public AssertionUtility assertionUtility = new AssertionUtility();
	public RestAssuredUtility restAssuredUtility = new RestAssuredUtility();
	public WebDriverUtility webDriverUtility = new WebDriverUtility();
	public DatabaseUtility databaseUtilty = new DatabaseUtility();
	public CommonWorkflowsUtility commonWorkflowUtility = new CommonWorkflowsUtility();
	public WebDriver driver;
	Connection connection;
	@BeforeSuite
	public void configBS() {
		System.out.println("*********Connect to the Database*********");
		String dbUrl = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "dburl");
		String dbUserName = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "dbusername");
		String dbPassword = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "dbpassword");
		connection = databaseUtilty.connectDB(dbUrl, dbUserName, dbPassword);
		UtilityObjectClass.setDbConnection(connection);
	}

	@BeforeClass
	public void configBC() {

		System.out.println("*********Open Browser*********");
		String url = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "url");
		String browserName = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "browser");
		driver = commonWorkflowUtility.openBrowserWindow(browserName);
		UtilityObjectClass.setDriver(driver);
		webDriverUtility.maximizeBrowserWindow(driver);
		webDriverUtility.navigateToUrl(driver, url);
		webDriverUtility.implicitWaitForSeconds(driver, IConstants.IMPLICIT_WAIT_TIME);
	}

	@BeforeMethod
	public void configBM(ITestResult result) {
		System.out.println("*********SignIn to the Application*********");
		String username = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "username");
		String password = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "password");
		commonWorkflowUtility.signInToApplication(driver, username, password);
	}

	@AfterMethod
	public void configAM(ITestResult result) {
		System.out.println("*********Sign out from the Application*********");
	}
	
	@AfterClass
	public void configAC() {
		System.out.println("*********Close browser*********");
		driver.close();
	}

	@AfterSuite
	public void configAS() {
		System.out.println("*********Disconnected from the Database*********");
		databaseUtilty.closeDB(connection);
	}
}