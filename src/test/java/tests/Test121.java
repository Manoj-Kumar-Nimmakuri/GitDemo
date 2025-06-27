package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test121 
{
	public ChromeDriver driver;
	
	@FindBy(how=How.NAME,using="identifier")
	WebElement e;
	
	public Test121(ChromeDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	public static void main(String[] args) throws Exception
	{
		//Open browser
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.silentOutput","true");
		ChromeDriver driver=new ChromeDriver();
		Test121 obj=new Test121(driver);
		//Launch site
		driver.get("http://www.gmail.com");
		Thread.sleep(5000);
		//Locate an element
		obj.e.sendKeys("seleniumfullstack",Keys.ENTER);
		Thread.sleep(5000);
		driver.navigate().back();
		Thread.sleep(5000);
		obj.e.clear();
		obj.e.sendKeys("seleniumhalfstack",Keys.ENTER);
		System.out.println("Test 2");
	}
}






