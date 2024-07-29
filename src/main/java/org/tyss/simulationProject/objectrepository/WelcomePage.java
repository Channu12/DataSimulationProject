package org.tyss.simulationProject.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomePage {
	WebDriver driver;
	@FindBy(id = "username") private WebElement usernameTextfield;
	@FindBy(id = "inputPassword") private WebElement passwordTextfield;
	@FindBy(xpath = "//button[text()='Sign in']") private WebElement signInButton;


	public WelcomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getUsernameTextfield() {
		return usernameTextfield;
	}

	public void setUsernameTextfield(WebElement usernameTextfield) {
		this.usernameTextfield = usernameTextfield;
	}

	public WebElement getPasswordTextfield() {
		return passwordTextfield;
	}

	public void setPasswordTextfield(WebElement passwordTextfield) {
		this.passwordTextfield = passwordTextfield;
	}

	public WebElement getSignInButton() {
		return signInButton;
	}

	public void setSignInButton(WebElement signInButton) {
		this.signInButton = signInButton;
	}
}
