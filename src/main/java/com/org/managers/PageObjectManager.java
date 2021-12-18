package com.org.managers;

import com.org.pages.*;
import org.openqa.selenium.WebDriver;
/*Author: Suraj Salunkhe
18th Dec 2021
Page Object Manager for Pages and Code Maintability
 */
public class PageObjectManager {
    private WebDriver driver;
    private BlazeDemoStartPage blazeDemoStartPage;
    private SelectFlightPage selectFlightPage;
    private FlightReservedPage flightReservedPage;
    private ConfirmationPage confirmationPage;

    public PageObjectManager(WebDriver driver){
        this.driver=driver;
     }

    public BlazeDemoStartPage getblazeDemoStartPage(){
        return (blazeDemoStartPage==null)?blazeDemoStartPage=new BlazeDemoStartPage(this.driver):blazeDemoStartPage;
    }
    public SelectFlightPage getSelectFlightPage(){
        return (selectFlightPage==null)?selectFlightPage=new SelectFlightPage(this.driver):selectFlightPage;
    }
    public FlightReservedPage getFlightReserved(){
        return (flightReservedPage==null)?flightReservedPage=new FlightReservedPage(this.driver):flightReservedPage;
    }
    public ConfirmationPage getConfirmationPage(){
        return (confirmationPage==null)?confirmationPage=new ConfirmationPage(this.driver):confirmationPage;
    }
}
