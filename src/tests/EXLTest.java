package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import appModules.GoogleMapMethods;
import pageObjects.GoogleHome;
import pageObjects.GoogleMap;
import utils.*;

public class EXLTest {
	public WebDriver driver;
	private String testCaseName;
	GoogleHome GoogleHomePage;
	GoogleMap GoogleMapPage;

	@Parameters(value="browser")
	@BeforeMethod
	public void beforeMethod(String browser) throws Exception {
		Log.info("Execution of before method started");
		testCaseName = this.toString();
		testCaseName = CommonMethods.getTestCaseName(this.toString());
		Log.startTestCase(testCaseName);
		driver = CommonMethods.openBrowser(browser);
		//1.Launch Chrome
		CommonMethods.openURL(Constants.appURL);	
		GoogleHomePage=PageFactory.initElements(driver, GoogleHome.class);
		GoogleMapPage=PageFactory.initElements(driver, GoogleMap.class);
	}


	@Test
	public void validateRouteDetails() 
	{
		try
		{
			Log.info("Execution of test method started");
			//2.Navigate to Google Maps (maps.google.com)
			GoogleHomePage.navigateToGoogleMap(driver);		
			CommonMethods.assertActualExpected(testCaseName, driver.getTitle(), GoogleMapPage.pageTitle, "Navigate to Google Map Page", true);

			//3.Search for San Francisco, California
			GoogleMapMethods.searchLocation(Constants.searchFor, GoogleMapPage);
			CommonMethods.waitForCompleteURL("place", Constants.pageLoadTimeout);
			CommonMethods.waitForPageLoad(Constants.pageLoadTimeout);
			
			//4. Verify the coordinates for San Francisco are 37.7577627,-122.4726194
			String currentURL=driver.getCurrentUrl();
			String actualCoordinates=GoogleMapMethods.getCoordinates(currentURL);
			CommonMethods.validateActualExpected(testCaseName, actualCoordinates, Constants.expectedCoordinate, "Verify Coordinates for the location="+Constants.searchFor, true);

			//5.Then search for driving directions (by car) from Chico, California to San Francisco, California.
			GoogleMapMethods.searchRoutesCar(Constants.fromLocation, Constants.toLocation, GoogleMapPage);
			
			//6.Verify three routes are displayed in the list.
			int numberOfRoutes=GoogleMapPage.routeDescription.size();
			CommonMethods.validateActualExpected(testCaseName, String.valueOf(numberOfRoutes),Constants.expectedNumofRoutes,
					"Verify total number of the routes from "+Constants.fromLocation+" to "+Constants.toLocation, true);

			//7.Then print the route title, distance in miles, and the travel time to a file titled “routes.txt”.
			GoogleMapMethods.printRoutes(Constants.routeInfoFile, GoogleMapPage);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}

	@AfterMethod
	public void afterMethod()
	{
		Log.endTestCase(testCaseName);
		driver.quit();
	}




}
