package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ComposePage;
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;
import utilities.TestUtility;

public class Test117
{
	public static void main(String[] args) throws Exception
	{
		//get browser name and test data from Excel file sheet
		File f=new File("Book1.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows(); //count of used rows
		int nouc=sh.getRow(0).getLastCellNum(); //count of used columns
		//Create result column with current date and time as heading
		//at next column to last used column 
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		Cell rc=sh.getRow(0).createCell(nouc);
		rc.setCellValue(sf.format(dt));
		sh.autoSizeColumn(nouc); //auto fit on column size
		//Define wrap text for remaining cells in that column
		CellStyle cs=wb.createCellStyle();
		cs.setWrapText(true);
		//Create object to Utility class
		TestUtility obj=new TestUtility();
		//Login functional testing with multiple test data in cross browser environment
		//loop from 2nd row(index=1) in excel file due to 1st row has names of columns
		for(int i=1;i<nour;i++)
		{
			try
			{
				String bn=sh.getRow(i).getCell(0).getStringCellValue();
				String u;
				try
				{
					u=sh.getRow(i).getCell(1).getStringCellValue();
				}
				catch(NullPointerException ne1)
				{
					u="";
				}
				String uc=sh.getRow(i).getCell(2).getStringCellValue();
				String p;
				try
				{
					p=sh.getRow(i).getCell(3).getStringCellValue();
				}
				catch(NullPointerException ne2)
				{
					p="";
				}
				String pc=sh.getRow(i).getCell(4).getStringCellValue();
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
				//Enter userid and click next
				hp.uidfill(u); //parameterization
				hp.uidnextclick();
				//userid testing
				if(u.length()==0)
				{
					try
					{
						w.until(ExpectedConditions.visibilityOf(hp.blankuiderr));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Uid blank test passed");
					}
					catch(Exception ex1)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Uid blank test failed and see "+obj.screeshot());
					}
				}
				else if(u.length()!=0 && uc.equalsIgnoreCase("invalid"))
				{
					try
					{
						w.until(ExpectedConditions.visibilityOf(hp.invaliduiderr));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Invalid UID test passed");
					}
					catch(Exception ex2)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Invalid Uid test failed and see "+obj.screeshot());
					}
				}
				else  //UId is valid value
				{
					try
					{
						w.until(ExpectedConditions.visibilityOf(lp.pwd));
						//Fill password and click next
						lp.pwdfill(p); //parameterization
						lp.pwdnextclick();
						//Password Testing
						if(p.length()==0)
						{
							try
							{
								w.until(ExpectedConditions.visibilityOf(lp.blankpwderr));
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellStyle(cs);
								c.setCellValue("PWD blank test passed");
							}
							catch(Exception ex1)
							{
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellStyle(cs);
								c.setCellValue(
										"PWD blank test failed and see "+obj.screeshot());
							}
						}
						else if(p.length()!=0 && pc.equalsIgnoreCase("invalid"))
						{
							try
							{
								w.until(ExpectedConditions.visibilityOf(lp.invalidpwderr));
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellStyle(cs);
								c.setCellValue("Invalid PWD test passed");
							}
							catch(Exception ex2)
							{
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellStyle(cs);
								c.setCellValue(
										"Invalid PWD test failed and see "+obj.screeshot());
							}	
						}
						else //PWD is valid value
						{
							try
							{
								w.until(ExpectedConditions.visibilityOf(cp.comp));
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellStyle(cs);
								c.setCellValue("Valid PWD test passed");
								//Do logout
								lop.clickProfilePic();
								w.until(ExpectedConditions.elementToBeClickable(lop.signout));
								lop.clickSignOut();
								w.until(ExpectedConditions.visibilityOf(lop.relogin));
							}
							catch(Exception ex3)
							{
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellStyle(cs);
								c.setCellValue(
										"Valid PWD test failed and see "+obj.screeshot());
							}
						}
					}
					catch(Exception ex3)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Valid Uid test failed and see "+obj.screeshot());
					}
				}
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

