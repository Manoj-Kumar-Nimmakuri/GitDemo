package tests;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Test123
{
	public static void main(String[] args) throws Exception
	{
		HtmlUnitDriver driver=new HtmlUnitDriver();
		driver.get("http://www.google.co.in");
		Thread.sleep(5000);
		String t=driver.getTitle();
		System.out.println(t);
		driver.close();
	}
}
