package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test122
{

	public static void main(String[] args) throws Exception
	{
		//Launch site
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.silentOutput","true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("http://www.google.co.in");
		Thread.sleep(5000);
		//Case-1
		String x=driver.getTitle();
		if(x.equals("Google"))
		{
			System.out.println("Smoke test1 passed");
		}
		else
		{
			System.out.println("Smoke test1 failed");
		}
		//Case2
		String u=driver.getCurrentUrl();
		if(u.contains("https:"))
		{
			System.out.println("Smoke test2 passed");
		}
		else
		{
			System.out.println("Smoke test2 failed");
		}
		//Close site
		driver.close();
	}

}
