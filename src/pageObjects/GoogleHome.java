package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utils.CommonMethods;
import utils.Constants;

public class GoogleHome {
	
	 final WebDriver driver;
	
	@FindBy(how = How.XPATH,using = ".//a[@title='Google apps']")
	public WebElement appLink;
	
	@FindBy(how = How.XPATH,using = "//span[text()='Maps']/ancestor::a")
	public WebElement mapLink;
	
	public void navigateToGoogleMap(WebDriver driver ) throws Exception
	{
		CommonMethods.waitForElement(appLink,Constants.pageLoadTimeout);
		appLink.click();
		CommonMethods.waitForElement(driver.findElement(By.xpath("//iframe")),Constants.pageLoadTimeout);
		driver.switchTo().frame(0);
		new Actions(driver).moveToElement(mapLink).click(mapLink).perform();
		CommonMethods.waitForCompleteURL("@", Constants.pageLoadTimeout);
		driver.switchTo().defaultContent();

	}
	
	public GoogleHome(WebDriver driver){ 
		 
	    this.driver = driver; 
	 
	    } 
	 
}
