package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Test120 
{
	public static void main(String[] args) throws Exception
	{
		//Open browser
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.silentOutput","true");
		ChromeDriver driver=new ChromeDriver();
		//Launch site
		driver.get("http://www.gmail.com");
		Thread.sleep(5000);
		//Maximize browser
		driver.manage().window().maximize();
		Thread.sleep(5000);
		//Locate an element
		WebElement e=driver.findElement(By.name("identifier"));
		//Operate element
		e.sendKeys("seleniumfullstack",Keys.ENTER);
		Thread.sleep(5000);
		//Back to page
		driver.navigate().back();
		Thread.sleep(5000);
		//Operate element
		e.sendKeys("seleniumhalfstack",Keys.ENTER);
		System.out.println("Test 1");
		System.out.println("Test 2");
		System.out.println("Test 3");
		System.out.println("Test 4");
		//Close site
		System.out.println("Test 5");
		System.out.println("Test 6");
		System.out.println("Test 7");
		System.out.println("Test 8");
		System.out.println("Test 9");
		System.out.println("Test 10");
		driver.close();
	}
}







