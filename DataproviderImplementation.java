package selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class DataproviderImplementation {
	WebDriver driver=new FirefoxDriver();	
	TimeUnit tm = TimeUnit.SECONDS;
	String strExpected="The email and password you entered don't match.";
	@Test(dataProvider="getExcelData")
	public void setData(String username, String password)
	{
	    driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(""+username+"");
        driver.findElement(By.id("next")).click();
        WebElement wbPwd=driver.findElement(By.xpath("//input[@id='Passwd']"));
        wbPwd.sendKeys(""+password+"");
        driver.findElement(By.id("signIn")).click();
        String strActualText=driver.findElement(By.id("errormsg_0_Passwd")).getText();
	    
        //verifying user enters wrong password
        Assert.assertEquals(strActualText, strExpected);
	}
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
	@BeforeMethod
	public void OpenBrowser()
	{
		driver.manage().window().maximize();
		driver.get("https://www.gmail.com");
		driver.manage().timeouts().implicitlyWait(20, tm);
	}
	@DataProvider
	public Object[][] getExcelData() throws IOException
	{
		FileInputStream fis  =  new FileInputStream("E:/myworkspace/LoginCredentialSheet.xls");
		Workbook wrkbookObj=new HSSFWorkbook(fis);
		Sheet sheetObj=wrkbookObj.getSheet("LoginDetails");
		//row count
		int rowCount=sheetObj.getLastRowNum();
		//cell count in row
		int columnCount=sheetObj.getRow(0).getLastCellNum();
		String[][] data = new String[rowCount][columnCount];
		int k = 0;
		for(int i=1;i<=rowCount;i++)
		{
			Row rowObj=sheetObj.getRow(i);
			for(int j=0;j<=columnCount-1;j++)
			{
				Cell cellObj=rowObj.getCell(j);
				String strValue=cellObj.toString();
				data[k][j]=strValue;
			}
		 k++;
		}
		wrkbookObj.close();
		return data;
	}
	
}
