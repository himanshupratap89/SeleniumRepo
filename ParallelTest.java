package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParallelTest {
	WebDriver driver;
	@Parameters(value ="browser")
	@Test
	public void RunMultipleBrowser(String browser)
	{
		if(browser.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();	
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","E:/myworkspace/java/selenium/src/test/Drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver","E:/myworkspace/java/selenium/src/test/Drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();	
		}
		driver.get("https://www.google.co.in");
		driver.findElement(By.id("lst-ib")).sendKeys("macys");
		driver.findElement(By.name("btnK")).click();
	}

}
