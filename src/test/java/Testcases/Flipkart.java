package Testcases;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Utility.BaseClass;

public class Flipkart extends BaseClass {
	WebDriver driver;

	@BeforeSuite(alwaysRun = true)
	public void setUp() {
		driver = getDriver();
	}

	@Test(priority=1)
	public void Flipkart_flow1() throws InterruptedException{
		String search = "Washing machine";
		// Navigate to Flipkart
		driver.get("https://www.flipkart.com/");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://www.flipkart.com/"));

		// Enter "Washing machine" in search box.
		sendKeys(driver.findElement(By.name("q")), search + Keys.ENTER);

		// click on popularity
		click(driver, driver.findElement(By.xpath("//div[text()='Popularity']")));

		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='BUOuZu']"))).getText().contains(search));
		
		// Take list of webelements with all ratings
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='XQDdHH']"))));
		List<WebElement> listOfRatings = driver.findElements(By.xpath("//*[@class='XQDdHH']"));
		int countOfRatingsMoreThan4 = 0;
		for (int i = 0; i < listOfRatings.size(); i++) {
			if (Double.parseDouble(listOfRatings.get(i).getText()) <= 4)
				countOfRatingsMoreThan4++;
		}

		System.out.println("Items with rating less than or equal to 4 stars= "
				+countOfRatingsMoreThan4);
	}

	@Test(priority=2)
	public void Flipkart_flow2() {
		driver.get("https://www.flipkart.com/");
		String search = "Iphone";
		sendKeys(driver.findElement(By.name("q")), search + Keys.ENTER);

		Assert.assertTrue(driver.findElement(By.cssSelector("[class='BUOuZu']")).getText().contains(search));

		List<WebElement> discountElements = wait.until(ExpectedConditions
				.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='UkUFwK']/span"))));
		// System.out.println("-------->"+discountElements.size());
		for (int i = 0; i < discountElements.size(); i++) {
			int discount = Integer.parseInt(discountElements.get(i).getText().split("%")[0]);
			if (discount > 17) {
				int a = i + 1;
				String title = wait
						.until(ExpectedConditions
								.visibilityOf(driver.findElement(By.xpath("(//div[@class='KzDlHZ'])[" + a + "]"))))
						.getText();
				System.out.println(title+" is having discount of "+discount+"%");
			}
		}
	}

	@Test(priority=3)
	public void Flipkart_flow3() throws InterruptedException {
		driver.get("https://www.flipkart.com/");
		String search = "Coffee mug";
		sendKeys(driver.findElement(By.name("q")), search + Keys.ENTER);

		Assert.assertTrue(driver.findElement(By.cssSelector("[class='BUOuZu']")).getText().contains(search));

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='4★ & above']")))).click();

		Thread.sleep(2000);
		List<WebElement> numberOfReviews = wait.until(ExpectedConditions.visibilityOfAllElements(
				driver.findElements(By.xpath("//span[contains(@id,'productRating')]/following-sibling::span"))));

		List<WebElement> productTitles = wait.until(
				ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//a[@class='wjcEIp']"))));

		HashMap<Integer, Integer> map = new HashMap<>();
		int intNumberOfReviews[] = new int[numberOfReviews.size()];
		for (int i = 0; i < numberOfReviews.size(); i++) {
			String str = numberOfReviews.get(i).getText().replace(",", "").replace("(", "").replace(")", "");

			intNumberOfReviews[i] = Integer.parseInt(str);
			map.put(intNumberOfReviews[i],i);
		}
		
		//map.forEach((key, value) -> System.out.println(key + ": " + value));
		Arrays.sort(intNumberOfReviews);
		System.out.println(Arrays.toString(intNumberOfReviews));
		for (int i = intNumberOfReviews.length - 1; i >= intNumberOfReviews.length - 5; i--) {
			int index = map.get(intNumberOfReviews[i]);
			String productTitle = productTitles.get(index).getAttribute("title");
			String imgUrl = driver.findElement(By.xpath("(//img[@loading='eager'])["+(index+1)+"]")).getAttribute("src");
			System.out.println(intNumberOfReviews[i]+" Title= " + productTitle + " and url= " + imgUrl);
		}
	}
	
	@AfterSuite(alwaysRun=true)
	public void tearDown() {
		driver.quit();
	}
}
