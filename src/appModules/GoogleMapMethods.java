package appModules;

import pageObjects.GoogleMap;
import utils.CommonMethods;


public class GoogleMapMethods {

	public static String getCoordinates(String currentURL)
	{
		String currURLArr[]=currentURL.split("@");
		String actualCoordinates=currURLArr[1].split("/")[0].split(",")[0].concat(",").concat(currURLArr[1].split("/")[0].split(",")[1]);
		return actualCoordinates;
	}

	public static void searchRoutesCar(String from, String to,GoogleMap GoogleMapPage)
	{
		GoogleMapPage.directionButton.click();
		GoogleMapPage.carDrivingRoutes.click();
		GoogleMapPage.fromLocation.clear();
		GoogleMapPage.fromLocation.sendKeys(from);
		GoogleMapPage.toLocation.clear();
		GoogleMapPage.toLocation.sendKeys(to);
		GoogleMapPage.toLocation.click();
		GoogleMapPage.toLocationSearchbtn.click();
	}

	public static void searchLocation(String location,GoogleMap GoogleMapPage) throws Exception
	{
		GoogleMapPage.searchInputBox.sendKeys(location);
		GoogleMapPage.searchButton.click();
	

	}
	public static void printRoutes(String fileName,GoogleMap GoogleMapPage) throws Exception
	{
		CommonMethods.openFile(fileName);
		int numberOfRoutes=GoogleMapPage.routeTitle.size();

		for(int i=0;i<numberOfRoutes;i++)
		{
			CommonMethods.writeFile("Route information for route #"+ (i+1));
			CommonMethods.writeFile(System.getProperty( "line.separator" ));
			CommonMethods.writeFile("************************************");
			CommonMethods.writeFile(System.getProperty( "line.separator" ));
			CommonMethods.writeFile("Route Title "+ GoogleMapPage.routeTitle.get(i).getText());
			CommonMethods.writeFile(System.getProperty( "line.separator" ));
			CommonMethods.writeFile("Route Duration "+ GoogleMapPage.routeTime.get(i).getText());
			CommonMethods.writeFile(System.getProperty( "line.separator" ));
			CommonMethods.writeFile("Route Distance "+ GoogleMapPage.routeDistance.get(i).getText());
			CommonMethods.writeFile(System.getProperty( "line.separator" ));
			CommonMethods.writeFile(System.getProperty( "line.separator" ));
		}
		CommonMethods.closeFile(fileName);
	}
}
