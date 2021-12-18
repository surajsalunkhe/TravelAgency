package com.org.pages;

import com.org.helper.LoggerHelper;
import com.org.util.ElementUtil;
import com.org.util.PropertiesFileManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.org.util.Constants.TEMP_DATA_FILE;

public class BlazeDemoStartPage {
    WebDriver driver;
    ElementUtil elementutil;
    Logger log = LoggerHelper.getLogger(BlazeDemoStartPage.class);
    public BlazeDemoStartPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
        elementutil=new ElementUtil(driver);
    }
    private By departureCity =By.xpath("//select[@name='fromPort']");
    private By destinationCity =By.xpath("//select[@name='toPort']");
    private By searchFlight=By.xpath("//input[@value='Find Flights']");

    public void selectDepartureCity(String fromCity){
        log.info("Select Departure City from drop down as "+fromCity);
        elementutil.waitForWebPageLoad();
        elementutil.selectFromDropDown(departureCity,fromCity);
        PropertiesFileManager.writeToProperties("FROM_CITY",fromCity,TEMP_DATA_FILE);
    }

    public void selectDestinationCity(String toCity){
        log.info("Select Destination City from drop down as "+toCity);
        elementutil.selectFromDropDown(destinationCity,toCity);
        PropertiesFileManager.writeToProperties("TO_CITY",toCity,TEMP_DATA_FILE);
    }

    public void searchFlightButton(){
        log.info("Click on Search Flight Button");
        elementutil.waitTillDisplay(searchFlight,5);
        elementutil.doClick(searchFlight);
    }

}
