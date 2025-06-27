package tests;

import org.openqa.selenium.remote.RemoteWebDriver;

import pages.GooglePage;
import utilities.TestUtility;

public class Test119
{
	public static void main(String[] args)
	{
		//Create object to Utility class
		TestUtility obj=new TestUtility();
		//Create driver object by opening required browser
		RemoteWebDriver driver=obj.openBrowser("chrome");
		//Create object to Page class
		GooglePage sp=new GooglePage(driver);
		//Launch site
		obj.launchSite("http://www.google.co.in");
		//Get text of link 1000 times
		long x=System.currentTimeMillis();
		for(int i=1;i<=1000;i++)
		{
			sp.mylink1.getText();
		}
		long y=System.currentTimeMillis();
		System.out.println("Time taken in seconds Without cache " +(y-x)/1000); //22
		//Get text of link 1000 times
		long z=System.currentTimeMillis();
		for(int i=1;i<=1000;i++)
		{
			sp.mylink2.getText();
		}
		long w=System.currentTimeMillis();
		System.out.println("Time taken in seconds With cache " +(w-z)/1000); //10
		//close site
		driver.close();
	}
}








