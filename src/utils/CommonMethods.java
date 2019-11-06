package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import utils.Log;
import utils.Constants;

public class CommonMethods {

	public static WebDriver driver = null;

	//method to open browser
	public static WebDriver openBrowser(String browserName) throws Exception{
		try{
			if(browserName.equals("Firefox")){
				System.setProperty("webdriver.gecko.driver", ".\\lib\\geckodriver.exe");

				driver = new FirefoxDriver();

				Log.info("FF driver instantiated");
			}
			else if(browserName.equals("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
				driver = new ChromeDriver();
				Log.info("Chrome driver instantiated");

			}
			else if(browserName.equals("IEEdge"))
			{
				System.setProperty("webdriver.edge.driver",".\\lib\\MicrosoftWebDriver.exe");

				driver=new EdgeDriver();
				Log.info("Edge driver instantiated");

			}

		}catch (Exception e){
			Log.error("Class CommonMethods | Method openBrowser | Exception desc : "+e.getMessage());
		}
		return driver;
	}
   //method to open the application
	public static void openURL(String url)
	{
		try
		{
			driver.get(url);
			Log.info("Web application launched successfully");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}catch (Exception e){
			Log.error("Class CommonMethods | Method openURL | Exception desc : "+e.getMessage());
		}
	}
	
	//method to get the test case name

	public static String getTestCaseName(String sTestCase)throws Exception{
		String value = sTestCase;
		try{
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			return value;
		}catch (Exception e){
			Log.error("Class CommonMethods | Method getTestCaseName | Exception desc : "+e.getMessage());
			throw (e);
		}
	}

	//method to take screenshot
	public static void takeScreenshot(String sTestCaseName) throws Exception{
		try{
			String currTime=convertTimeStampToString(getTimeStamp(),"yyyyMMdd");
			String random=generateRandomNumber(1, 1000);
			String suffix=currTime+"_"+random;

			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Constants.screenShotPath + sTestCaseName +suffix+".jpg"));	
		} catch (Exception e){
			Log.error("Class CommonMethods | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
			throw new Exception();
		}
	}

	public static FileWriter writer;
	public static void openFile(String fileName) throws Exception
	{
		try
		{
			writer = new FileWriter(fileName, false);
		}
		catch (IOException e) {
			Log.error("Class CommonMethods | Method writeFile | Exception occured while opening the file : "+e.getMessage());
			throw new Exception();
		}
	}
	public static void writeFile(String data) throws Exception
	{
		try {
			writer.write(data);

		} catch (IOException e) {
			Log.error("Class CommonMethods | Method writeFile | Exception occured while writing into the file : "+e.getMessage());
			throw new Exception();
		}

	}
	public static void closeFile(String fileName) throws Exception
	{
		try
		{
			writer.close();
		}
		catch (IOException e) {
			Log.error("Class CommonMethods | Method writeFile | Exception occured while closing the file : "+e.getMessage());
			throw new Exception();
		}
	}

	public static Timestamp getTimeStamp()
	{
		Date date= new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);

		return ts;
	}

	public static String convertTimeStampToString(Timestamp t,String format)
	{
		String formattedDate = new SimpleDateFormat(format).format(t);
		return formattedDate;
	}

	public static String generateRandomNumber(int min, int max) 
	{
		double x = (int)(Math.random()*((max-min)+1))+min;
		return String.valueOf(x);

	}

	public static void validateActualExpected(String testName,String actual,String expected,String stepDetails,
			boolean takeScreenshot) throws Exception
	{
		if(actual.equals(expected))
		{
			Reporter.log("Step Description:"+stepDetails+"\nVerification Passed.\n Actual Result:"+actual+"Expected Result:"+expected);
			Log.info("Step Description:"+stepDetails+"\nVerification Passed.\n Actual Result:"+actual+"Expected Result:"+expected);
			if(takeScreenshot)
			{
				CommonMethods.takeScreenshot(testName);
			}
		}
		else
		{
			Reporter.log("Step Description:"+stepDetails+"\nVerification Failed.\n Actual Result:"+actual+"Expected Result:"+expected);
			Log.info("Step Description:"+stepDetails+"\nVerification Failed.\n Actual Result:"+actual+"Expected Result:"+expected);
			CommonMethods.takeScreenshot(testName);
		}


	}

	public static void assertActualExpected(String testName,String actual,String expected,String stepDetails,
			boolean takeScreenshot) throws Exception
	{
		Assert.assertEquals(driver.getTitle(),expected, stepDetails);
		Log.info(stepDetails+"Actual Result:"+actual+"Expected Result:"+expected);
		Reporter.log(stepDetails+"Actual Result:"+actual+"Expected Result:"+expected);
		if (takeScreenshot)
			CommonMethods.takeScreenshot(testName);

	}

	public static void waitForElement(WebElement element,int pageLoadTimeout){

		WebDriverWait wait = new WebDriverWait(driver, pageLoadTimeout);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public static void waitForCompleteURL(String keyword,int pageLoadTimeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Constants.pageLoadTimeout);
		wait.until(ExpectedConditions.urlContains(keyword));
	}
	public static void waitForPageLoad(int pageLoadTimeout)
	{
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, pageLoadTimeout);
		wait.until(pageLoadCondition);

	}


}
