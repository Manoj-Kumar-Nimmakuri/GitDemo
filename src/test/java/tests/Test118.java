package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ComposePage;
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;
import utilities.TestUtility;

public class Test118
{
	public static void main(String[] args) throws Exception
	{
		//get browser name and test data from Excel file sheet
		File f=new File("Book1.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet2");
		int nour=sh.getPhysicalNumberOfRows(); //count of used rows
		int nouc=sh.getRow(0).getLastCellNum(); //count of used columns
		//Create result column with current date and time as heading
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		Cell rc=sh.getRow(0).createCell(nouc);
		rc.setCellValue(sf.format(dt));
		sh.autoSizeColumn(nouc); //auto fit on column size
		//Define wrap text for remaining cells
		CellStyle cs=wb.createCellStyle();
		cs.setWrapText(true);
		//Create object to Utility class
		TestUtility obj=new TestUtility();
		//Mail compose functional testing with multiple test data in cross browser environment
		//loop from 2nd row(index=1) in excel file due to 1st row has names of columns
		for(int i=1;i<nour;i++)
		{
			try
			{
				Row r=sh.getRow(i);
				String bn=r.getCell(0).getStringCellValue();
				String u=r.getCell(1).getStringCellValue();
				String p=r.getCell(2).getStringCellValue();
				String t=r.getCell(3).getStringCellValue();
				String s=r.getCell(4).getStringCellValue();
				String b=r.getCell(5).getStringCellValue();
				String fp=r.getCell(6).getStringCellValue();
				//Open browser
				RemoteWebDriver driver=obj.openBrowser(bn);
				//Create objects to page classes
				HomePage hp=new HomePage(driver);
				LoginPage lp=new LoginPage(driver);
				ComposePage cp=new ComposePage(driver);
				LogoutPage lop=new LogoutPage(driver);
				//Launch site
				obj.launchSite("http://www.gmail.com");
				WebDriverWait w=new WebDriverWait(driver,50);
				w.until(ExpectedConditions.visibilityOf(hp.uid));
				//Do login using valid data
				hp.uidfill(u); //parameterization
				hp.uidnextclick();
				w.until(ExpectedConditions.visibilityOf(lp.pwd));
				lp.pwdfill(p); //parameterization
				lp.pwdnextclick();
				w.until(ExpectedConditions.elementToBeClickable(cp.comp));
				//Goto compose operation
				cp.clickCompose();
				w.until(ExpectedConditions.visibilityOf(cp.toaddress));
				cp.fillTo(t);
				cp.fillSubject(s);
				cp.fillBody(b);
				cp.attachment(fp);
				w.until(ExpectedConditions.visibilityOf(cp.uploading));
				cp.clickSend();
				try
				{
					w.until(ExpectedConditions.visibilityOf(cp.outputmsg));
					String msg=cp.outputmsg.getText();
					Cell c=sh.getRow(i).createCell(nouc);
					c.setCellStyle(cs);
					c.setCellValue("Compose test passed and we got "+msg);
				}
				catch(Exception ex)
				{
					Cell c=sh.getRow(i).createCell(nouc);
					c.setCellStyle(cs);
					c.setCellValue("Compose test failed and see "+obj.screeshot());
				}
				//Do logout
				lop.clickProfilePic();
				w.until(ExpectedConditions.elementToBeClickable(lop.signout));
				lop.clickSignOut();
				w.until(ExpectedConditions.visibilityOf(lop.relogin));
				//close site
				obj.closeSite();
			}
			catch(Exception ex)
			{
				//close site
				obj.closeSite();
				Cell c=sh.getRow(i).createCell(nouc);
				c.setCellStyle(cs);
				c.setCellValue(ex.getMessage());
			}
		} //loop ending
		//Save and close excel file
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo); //save
		wb.close();
		fo.close();
		fi.close();
	}
}

