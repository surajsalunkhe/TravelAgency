package stepDefination;
import com.org.driverFactory.DriverFactory;
import com.org.managers.PageObjectManager;
import com.org.pages.*;
import com.org.util.PropertiesFileManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/*
Author: Suraj Salunkhe
Date:18th Dec 2021
*/
public class StepDefination {

	WebDriver driver;
	PageObjectManager pageObjectManager;
	DriverFactory driverFactory=new DriverFactory();
	String searchEngine;


	@Given("User opens the {string} browser")
	public void user_open_browser(String browserName){
		driver=driverFactory.init_Driver(browserName.toUpperCase());
		pageObjectManager=new PageObjectManager(driver);
	}

	@Given("Navigate to url {string}")
	public void login_to_url(String searchEngineFromUser) throws Exception {
		String url=PropertiesFileManager.getPropertyValue(searchEngineFromUser.toUpperCase());
		searchEngine=searchEngineFromUser.toLowerCase();
		driver.get(url);
	}


	@Given("User select {string} from departure city dropdown")
	public void user_select_from_departure_city_dropdown(String fromCity) {
		// Write code here that turns the phrase above into concrete actions
		pageObjectManager.getblazeDemoStartPage().selectDepartureCity(fromCity);
	}

	@Given("User select {string} from destination city dropdown")
	public void user_select_from_destination_city_dropdown(String toCity) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Select DropDown value");
		pageObjectManager.getblazeDemoStartPage().selectDestinationCity(toCity);

	}

	@When("User click on search flight button")
	public void user_click_on_search_button() {
			pageObjectManager.getblazeDemoStartPage().searchFlightButton();
	}

	@When("User select first flight from flight list")
	public void user_select_first_flight_from_flight_list() {
		pageObjectManager.getSelectFlightPage().selectFirstFlightFromList();
	}

	@When("User select cheapest flight from flight list")
	public void user_select_cheapest_flight_from_flight_list() {
		pageObjectManager.getSelectFlightPage().selectCheapestFlight();
	}
	@When("User select costliest flight from flight list")
	public void user_select_costliest_flight_from_flight_list() {
		pageObjectManager.getSelectFlightPage().selectCostlyFlight();
	}

	@When("User select flight by {string} as flight number")
	public void user_select_flight_by_as_flight_number(String flightNo) {
		pageObjectManager.getSelectFlightPage().selectFlightByFlightNumber(flightNo);
	}

	@When("User verify flight reservation page")
	public void verify_flight_reservation_page() {
		pageObjectManager.getSelectFlightPage().verifyPageTitle();
	}


	@When("User fill the details to purchase ticket")
	public void user_fill_the_Details() {
		pageObjectManager.getFlightReserved().enterUserName();
		pageObjectManager.getFlightReserved().enterAddress();
		pageObjectManager.getFlightReserved().enterCity();
		pageObjectManager.getFlightReserved().enterStateName();
		pageObjectManager.getFlightReserved().enterZipCode();
		pageObjectManager.getFlightReserved().selectCardType();
		pageObjectManager.getFlightReserved().enterCreditCardNumber();
		pageObjectManager.getFlightReserved().enterMonth();
		pageObjectManager.getFlightReserved().enterYear();
		pageObjectManager.getFlightReserved().enterNameOnCard();
		pageObjectManager.getFlightReserved().selectCheckbox();
		pageObjectManager.getFlightReserved().clickPurchaseFlightTicket();
	}

	@Then("User verify confirmation page and save confirmation Id")
	public void user_verify_confirmation_page() {
		pageObjectManager.getConfirmationPage().savePurchaseDetails();
	}

	@Then("User quit the browser")
	public void user_quit_the_browser() {
		driver.quit();
	}

}
