package org.tyss.simulationProject.genericutility;

import java.sql.Connection;

import org.openqa.selenium.WebDriver;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jcraft.jsch.Session;

public class UtilityObjectClass {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<XmlTest> xmlTest = new ThreadLocal<XmlTest>();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentReports> extentReports = new ThreadLocal<ExtentReports>();
	private static ThreadLocal<Connection> dbConnection = new ThreadLocal<Connection>();
	private static ThreadLocal<Session> sshSession = new ThreadLocal<Session>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(WebDriver actDriver) {
		driver.set(actDriver);
	}

	public static XmlTest getXmlTest() {
		return xmlTest.get();
	}

	public static void setXmlTest(XmlTest actXmlTest) {
		xmlTest.set(actXmlTest);;
	}

	public static ExtentTest getExtentTest() {
		return extentTest.get();
	}

	public static void setExtentTest(ExtentTest actExtentTest) {
		extentTest.set(actExtentTest);
	}

	public static ExtentReports getExtentReports() {
		return extentReports.get();
	}

	public static void setExtentReports(ExtentReports actExtentReports) {
		extentReports.set(actExtentReports);
	}

	public static Connection getDbConnection() {
		return dbConnection.get();
	}

	public static void setDbConnection(Connection actDbConnection) {
		dbConnection.set(actDbConnection);
	}

	public static Session getSshSession() {
		return sshSession.get();
	}

	public static void setSshSession(Session actSshSession) {
		sshSession.set(actSshSession);
	}
}
