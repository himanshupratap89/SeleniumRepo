package selenium;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Irctc 
{
	WebDriver driver=new FirefoxDriver();	
	TimeUnit tm = TimeUnit.SECONDS;
	@BeforeMethod
	public void OpenBrowser()
	{
		driver.get("https://www.irctc.co.in");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, tm);
	}
	@Test
	public void FindFlights()
	{
	    String ParentWdHandle=driver.getWindowHandle(); 
		driver.findElement(By.linkText("Flight Tickets")).click();
		Set<String> allhandle=driver.getWindowHandles();
		for(String winHandle :allhandle)
		{
			driver.switchTo().window(winHandle);
			if(driver.getTitle().equals("IRCTC Flight Ticket Booking"))
		    	break;
		}
		driver.findElement(By.id("origin")).sendKeys("delhi");
		driver.findElement(By.linkText("Delhi (New Delhi),DEL")).click();
		driver.findElement(By.id("destination")).sendKeys("jaipur");
		driver.findElement(By.linkText("Jaipur,JAI")).click();
		driver.findElement(By.xpath("//input[@id='departDate']/following-sibling::img[@class='ui-datepicker-trigger']")).click();
		driver.findElement(By.xpath("//td[contains(@class,' ui-datepicker-week-end ')]/a[text()='21']")).click();
		Select selectAdult=new Select(driver.findElement(By.id("noOfAdult")));
		selectAdult.selectByVisibleText("2");
		Select selectChild=new Select(driver.findElement(By.id("noOfChild")));
		selectChild.selectByVisibleText("1");
		driver.findElement(By.xpath("//div[@id='searchopt_dom']/following-sibling::div[@class='srchbtn']")).click();
		System.out.println("No. of Flights:"+ driver.findElements(By.xpath("//div[@id='flightListResult']/div")).size());
		driver.findElement(By.id("minPrice")).click();
		String strPrice=driver.findElement(By.xpath("//div[@id='flight_7042_7036_20559W7029']/descendant::span[text()='121350']")).getText();
		Integer nFlightPrice = Integer.valueOf(strPrice);
		System.out.println(nFlightPrice);
		Boolean bFlag=false;
		if(nFlightPrice<50000)
		{
			bFlag= true;
		}
		//verifying flight price is less than Rs50,000
		Assert.assertFalse(bFlag);
	}
	@AfterMethod
	public void CloseBrowser()
	{
		driver.close();
	}
}
