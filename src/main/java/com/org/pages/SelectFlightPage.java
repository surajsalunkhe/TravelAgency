package com.org.pages;

import com.org.helper.LoggerHelper;
import com.org.util.ElementUtil;
import com.org.util.PropertiesFileManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.org.util.Constants.TEMP_DATA_FILE;

public class SelectFlightPage {
    WebDriver driver;
    ElementUtil elementutil;
    Logger log = LoggerHelper.getLogger(SelectFlightPage.class);

    public SelectFlightPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
        elementutil=new ElementUtil(driver);
    }
    //private By tableDetails = By.xpath("//table[@class=\"table\"]");
    private By rowsNo=By.xpath("//table[@class='table']/tbody/tr");
    private By firstFlightSelect=By.xpath("//table[@class='table']/tbody/tr[1]/td[1]/input[1]");

    public void selectFirstFlightFromList(){
        log.info("Clicking on First flight in available table");
        elementutil.doClick(firstFlightSelect);
    }

    public void verifyPageTitle(){
        String pageTitle=elementutil.getTitleOfWebsite();
        Assert.assertEquals("Page Title mismatch", PropertiesFileManager.getPropertyValue("RESERVATION_PAGE_TITLE"),pageTitle);
    }
    public void selectCheapestFlight(){
        log.info("Selecting Cheapest flight available in table");
        elementutil.waitForWebPageLoad();
        int rowSize=findNumberOfRows();
        List<String> lst=new ArrayList<>();
        for(int i=1;i<=rowSize;i++){
                lst.add(driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + i +"]/td[6]")).getText());
        }
        String min= Collections.min(lst);
        log.info("Cheapest flight Price="+min);
        int index= lst.indexOf(min)+1;
        log.info("Cheapest flight index="+index);
        System.out.println("//table[@class='table']/tbody/tr[" + index +"]/td[1]");
        By cheapestFlight=By.xpath("//table[@class='table']/tbody/tr[" + index +"]/td[1]/input");
        elementutil.waitTillDisplay(cheapestFlight,5);
        elementutil.doClick(cheapestFlight);
    }
    public void selectCostlyFlight(){
        int rowSize=findNumberOfRows();
        List<String> lst=new ArrayList<>();
        for(int i=1;i<=rowSize;i++){
            lst.add(driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + i +"]/td[6]")).getText());
        }
        String max= Collections.max(lst);
        int index= lst.indexOf(max)+1;
        driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + index +"]/td[1]/input")).click();
    }
    public void selectFlightByFlightNumber(String flightNo){
        int rowSize=findNumberOfRows();
        List<String> lst=new ArrayList<>();
        for(int i=1;i<=rowSize;i++){
            lst.add(driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + i +"]/td[2]")).getText());
        }
        int index= lst.indexOf(flightNo)+1;
        try{
        driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + index +"]/td[1]/input")).click();
        }catch (Exception e){
            log.info("No flight found with given number selecting min cost flight");
            selectCheapestFlight();
        }
    }
    public int findNumberOfRows(){
      int noOfRows;
      List<WebElement> rowList=elementutil.getElements(rowsNo);
      noOfRows=rowList.size();
      return noOfRows;
    }

}
