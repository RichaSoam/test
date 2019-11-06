package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;

public class GoogleMap {

	final WebDriver driver;
	public String pageTitle="Google Maps";

	@FindBy(how = How.XPATH,using = ".//input[@id='searchboxinput']")
	public WebElement searchInputBox;

	@FindBy(how = How.XPATH,using = ".//button[@id='searchbox-searchbutton']")
	public WebElement searchButton;
	
	@FindBy(how = How.XPATH,using = "//button[@data-value='Directions']")
	public WebElement directionButton;
	
	@FindBy(how = How.XPATH,using = "//div[@id='directions-searchbox-0']/descendant::input")
	public WebElement fromLocation;
	
	@FindBy(how = How.XPATH,using = "//div[@id='directions-searchbox-1']/descendant::input")
	public WebElement toLocation;
	
	@FindBy(how = How.XPATH,using = "//div[@id='directions-searchbox-1']/descendant::button[@data-tooltip='Search']")
	public WebElement toLocationSearchbtn;
	
	@FindBy(how = How.XPATH,using = "//div[@data-tooltip='Driving']/ancestor::button")
	public WebElement carDrivingRoutes;
	
	@FindBys(@FindBy(how= How.XPATH, using="//div[@class='section-directions-trip-description']"))
	public List<WebElement> routeDescription;
	
	@FindBys(@FindBy(how= How.XPATH, using="//h1[@class='section-directions-trip-title']/span"))
	public List<WebElement> routeTitle;
	
	@FindBys(@FindBy(how= How.XPATH, using="//div[contains(@class,'section-directions-trip-duration')]"))
	public List<WebElement> routeTime;
	
	@FindBys(@FindBy(how= How.XPATH, using="//div[contains(@class,'section-directions-trip-distance')]/div"))
	public List<WebElement> routeDistance; 	 	
	

	public GoogleMap(WebDriver driver){ 

		this.driver = driver; 

	} 
}
