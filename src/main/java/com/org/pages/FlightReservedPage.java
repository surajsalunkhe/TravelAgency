package com.org.pages;

import com.org.helper.LoggerHelper;
import com.org.util.ElementUtil;
import com.org.util.PropertiesFileManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class FlightReservedPage {
    WebDriver driver;
    ElementUtil elementutil;
    Logger log = LoggerHelper.getLogger(FlightReservedPage.class);
    public FlightReservedPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
        elementutil=new ElementUtil(driver);
    }
    private By userName = By.xpath("//input[@id='inputName']");
    private By address = By.xpath("//input[@id='address']");
    private By cityName = By.xpath("//input[@id='city']");
    private By stateName = By.xpath("//input[@id='state']");
    private By zipCode = By.xpath("//input[@id='zipCode']");
    private By cardType = By.xpath("//select[@id='cardType']");
    private By cardNumber = By.xpath("//input[@id='creditCardNumber']");
    private By expiry_month = By.xpath("//input[@id='creditCardMonth']");
    private By expiry_year = By.xpath("//input[@id='creditCardYear']");
    private By nameOnCard = By.xpath("//input[@id='nameOnCard']");
    private By rememberCheckbox =  By.xpath("//input[@id='rememberMe']");
    private By purchaseFlightButton = By.xpath("//input[@value='Purchase Flight']");

    public void enterUserName(){
        elementutil.waitTillDisplay(userName,5);
        log.info("Enter username");
        elementutil.doSendKeys(userName, PropertiesFileManager.getPropertyValue("USER_NAME"));
    }
    public void enterAddress(){
        log.info("Enter Address");
        elementutil.doSendKeys(address,PropertiesFileManager.getPropertyValue("ADDRESS"));
    }
    public void enterCity(){
        log.info("Enter City name");
        elementutil.doSendKeys(cityName,PropertiesFileManager.getPropertyValue("CITY"));
    }
    public void enterStateName(){
        log.info("Enter State Name");
        elementutil.doSendKeys(stateName,PropertiesFileManager.getPropertyValue("STATE"));
    }
    public void enterZipCode(){
        log.info("Enter ZipCode");
        elementutil.waitTillDisplay(zipCode,10);
        elementutil.doSendKeys(zipCode,PropertiesFileManager.getPropertyValue("ZIPCODE"));
    }
    public void selectCardType(){
        elementutil.scrollWebapage();
        log.info("Select Card Type="+PropertiesFileManager.getPropertyValue("CARD_TYPE"));
        elementutil.selectFromDropDown(cardType,PropertiesFileManager.getPropertyValue("CARD_TYPE"));
    }
    public void enterCreditCardNumber(){
        log.info("Enter Credit Card NO");
        elementutil.doSendKeys(cardNumber,PropertiesFileManager.getPropertyValue("CREDIT_CARD_NUMBER"));
    }
    public void enterMonth(){
        log.info("Enter Card Expiry Month");
        elementutil.doSendKeys(expiry_month,PropertiesFileManager.getPropertyValue("MONTH"));
    }
    public void enterYear(){
        log.info("Enter Card Expiry Year");
        elementutil.doSendKeys(expiry_year,PropertiesFileManager.getPropertyValue("YEAR"));
    }
    public void enterNameOnCard(){
        log.info("Enter Name On Card");
        elementutil.doSendKeys(nameOnCard,PropertiesFileManager.getPropertyValue("NAME_ON_CARD"));
    }
    public void selectCheckbox(){
        elementutil.doClick(rememberCheckbox);
    }
    public void clickPurchaseFlightTicket(){
        elementutil.doClick(purchaseFlightButton);
    }

}
