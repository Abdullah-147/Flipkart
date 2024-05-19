package Utility;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor jse;

	public WebDriver getDriver() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		jse=(JavascriptExecutor)driver;
		return driver;
	}
	
	public void click(WebDriver driver,WebElement el) {
		wait.until(ExpectedConditions.elementToBeClickable(el)).click();
	}
	
	public void sendKeys(WebElement el,String str) {
		el.clear();
		el.sendKeys(str);
	}
}
