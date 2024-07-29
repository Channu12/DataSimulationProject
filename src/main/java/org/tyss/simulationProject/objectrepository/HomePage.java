package org.tyss.simulationProject.objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	@FindBy(xpath = "//div[@title='logout']") private WebElement closeIcon;
	@FindBy(xpath = "//a[text()='All Transactions']") private WebElement allTransactionsLink;
	@FindBy(xpath = "//input[@placeholder='Search by Transaction Id']") private WebElement searchTextField;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getTransactionId(String transactionId) {
		return driver.findElement(By.xpath("//th[text()='TransactionId']/ancestor::table/descendant::td[text()='"+transactionId+"']"));
	}

	public WebElement getCloseIcon() {
		return closeIcon;
	}

	public void setCloseIcon(WebElement closeIcon) {
		this.closeIcon = closeIcon;
	}

	public WebElement getAllTransactionsLink() {
		return allTransactionsLink;
	}

	public void setAllTransactionsLink(WebElement allTransactionsLink) {
		this.allTransactionsLink = allTransactionsLink;
	}

	public WebElement getSearchTextField() {
		return searchTextField;
	}

	public void setSearchTextField(WebElement searchTextField) {
		this.searchTextField = searchTextField;
	}
}
