package appHooks;

import java.io.File;
import java.io.IOException;

import com.org.util.Constants;
import com.org.util.PropertiesFileManager;
import io.cucumber.java.AfterStep;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.org.driverFactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/*
Author: Suraj Salunkhe
Date:18th Dec 2021
*/
public class ApplicationHook {
	DriverFactory driverfactory;
	WebDriver driver;

	@Before("@smoke and @regression")
	public void setUpWebDriver()
	{
		 driverfactory=new DriverFactory();
		 driver=driverfactory.init_Driver(PropertiesFileManager.getPropertyValue("broswerName"));
	}
	
	@After
	public void captureScreenshot(Scenario sc) throws IOException
	{
		if(sc.isFailed()) {
			driver=DriverFactory.getDriver();
			String screenshotName = sc.getName().replace("", "").replace(":", "").replaceAll("[^a-zA-Z0-9]","_");
			File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			//FileUtils.copyFile(src,new File(Constants.SCREENSHOTS + screenshotName+ ".png"));
			final byte[] screenshot= ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			sc.attach(screenshot, "image/png", screenshotName);
		}
		
	}
	//Added code to check removal of Result output Directory once project run
	/*@Before
	public void clearReportFolder(){
		File myfile=new File(Constants.REPORT_FOLDER);
		try {
			FileUtils.deleteDirectory(myfile);
		} catch (IOException e) {
			System.out.println("Folder not found for deletion");
		}
	}*/
	
	@After("@smoke and @regression")
	public void tearDown() {
		driver=DriverFactory.getDriver();
		driver.quit();
	}

}
