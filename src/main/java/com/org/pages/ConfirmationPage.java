package com.org.pages;

import com.org.helper.LoggerHelper;
import com.org.util.ElementUtil;
import com.org.util.PropertiesFileManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;

import static com.org.util.Constants.TEMP_DATA_FILE;

public class ConfirmationPage {
    WebDriver driver;
    ElementUtil elementutil;
    Logger log = LoggerHelper.getLogger(ConfirmationPage.class);
    public ConfirmationPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
        elementutil=new ElementUtil(driver);
    }
    private By selectID = By.xpath("//input[@id='inputName']");
    private By purchaseSucess=By.xpath("//h1[@class=\"\"]");
    private By tableID=By.xpath("//table[@class='table']/tbody/tr[1]/td");
    int numberOfRows;
    int numberOfColumns;

    HashMap<String,String> hmap=new HashMap<>();

    public void savePurchaseDetails(){
        List<WebElement> rowList=driver.findElements(By.xpath("//table[@class='table']/tbody/tr"));
        numberOfRows=rowList.size();
        List<WebElement> colList=driver.findElements(By.xpath("//table[@class='table']/tbody/tr/td"));
        numberOfColumns=colList.size();
        for(int i=1;i<=numberOfRows;i++){
            hmap.put(driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + i +"]//child::td[1]")).getText(),driver.findElement(By.xpath("//table[@class='table']/tbody/tr[" + i +"]//child::td[2]")).getText());
        }
        PropertiesFileManager.writeToProperties("PURCHASE_ID",hmap.get("Id"),TEMP_DATA_FILE);

    }

}
