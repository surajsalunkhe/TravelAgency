package com.org.util;

import java.time.Duration;
import java.util.List;

import com.org.helper.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {
	WebDriver driver;
	JavascriptExecutor js;
	Logger log = LoggerHelper.getLogger(ElementUtil.class);
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public void launchUrl(String url) {
		log.info("Navigating to Url="+url);
		driver.get(url);
	}
	public String getTitleOfWebsite(){
		log.info("Website Page Title="+driver.getTitle());
		return driver.getTitle();
	}
	public String getUrlOfWebsite(){
		js=(JavascriptExecutor) driver;
		String url= (String) js.executeScript("return document.URL");
		log.info("Website URl="+url);
		return url;
	}
	public void switchWindow(String windID) {
		driver.switchTo().window(windID);
	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doClickIfPresent(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement wb = getElement(locator);
			log.info("Clicking WebElement If present");
			wb.click();
		} catch (Exception e) {
			log.info("Element not found"+e.getStackTrace());
		}
	}

	public void doClick(By locator) {
		WebElement wb = getElement(locator);
		wb.click();
	}

	public void doSendKeys(By locator, String input) {
		WebElement wb = getElement(locator);
		wb.clear();
		log.info("Entering Text in Field="+input);
		wb.sendKeys(input);
	}

	public void doClear(By locator) {
		WebElement wb = getElement(locator);
		log.info("Clearing data in the text field");
		wb.clear();
	}

	public String getReqText(By locator) {
		WebElement wb = getElement(locator);
		return wb.getText();
	}

	public WebElement waitTillDisplay(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public boolean getDisplayStatus(By locator) {
		return waitTillDisplay(locator, 30).isDisplayed();
	}

	public void selectItemInElementList(By locator, String itemName) {
		List<WebElement> filterOption = getElements(locator);
		for (WebElement option : filterOption) {
			log.info("Values from list:"+option.getText());
			if (option.getText().equalsIgnoreCase(itemName)) {
				option.click();
				break;
			}
		}
	}

	public void selectItemWhenContainsInElementList(By locator, String itemName) {
		List<WebElement> filterOption = getElements(locator);
		for (WebElement option : filterOption) {
			log.info("Values from list:"+option.getText());
			if (option.getText().contains(itemName)) {
				option.click();
				break;
			}
		}
	}

	public void selectFromDropDown(By locator,String itemName){
		waitTillDisplay(locator,5);
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(itemName);
	}
	public void clickOnFirstElementFromList(By locator){
		waitTillDisplay(locator,5);
		List<WebElement> filterOption = getElements(locator);
		WebElement element=filterOption.getFirst();
		log.info("Clicking on first element from list ="+element.getText());
		element.click();
	}

	public void clickOnSearchResult(By locator){
		List<WebElement> filterOption = getElements(locator);
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element=filterOption.getFirst();
		try {
			log.info("Clicking on first result using JS executor ="+element.getText());
			safeJavaScriptClick(element);
			Thread.sleep(3000);
		} catch (Exception e) {
			log.info("Unable to click "+ e.getStackTrace());
		}
	}
	public void safeJavaScriptClick(WebElement element){
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				log.info("Clicking on element with using java script executor");
				js.executeScript("arguments[0].click();", element);
			} else {
				log.info("Unable to click on element");

			}
		} catch (StaleElementReferenceException e) {
			log.info("Element is not attached to the page document "+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			log.info("Element was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			log.info("Unable to click on element "+ e.getStackTrace());
		}
	}
	public void scrollWebapage(){
		js.executeScript("window.scrollBy(0,500)", "");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void waitForWebPageLoad(){
		js.executeScript("return document.readyState").equals("complete");
	}
	public void quitBrowser(){
		driver.quit();
	}
}

