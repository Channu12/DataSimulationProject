package org.tyss.simulationProject.workflowutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.tyss.simulationProject.genericutility.WebDriverUtility;
import org.tyss.simulationProject.objectrepository.WelcomePage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonWorkflowsUtility {
	WebDriverUtility webDriverUtility = new WebDriverUtility();

	public void signInToApplication(WebDriver driver, String username, String password) {
		WelcomePage welcomePage = new WelcomePage(driver);
		webDriverUtility.enterInputIntoElement(driver, username, welcomePage.getUsernameTextfield());
		webDriverUtility.enterInputIntoElement(driver, password, welcomePage.getPasswordTextfield());
		webDriverUtility.clickOnELement(driver, welcomePage.getSignInButton());
	}

	public WebDriver openBrowserWindow(String browserName) {
		WebDriver driver = null;
		if (browserName.trim().equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if(browserName.trim().equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if(browserName.trim().equalsIgnoreCase("edge")){
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		return driver;
	}
}

