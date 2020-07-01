package com.applitools.quickstarts;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Unit test for simple App.
 */
public class AppTestV2 {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\AppData\\Roaming\\npm\\node_modules\\protractor\\node_modules\\webdriver-manager\\selenium\\chromedriver_83.0.4103.39.exe");
		// Create a new chrome web driver
		WebDriver webDriver = new ChromeDriver();

		// Create a runner with concurrency of 1
		VisualGridRunner runner = new VisualGridRunner(10);

		// Create Eyes object with the runner, meaning it'll be a Visual Grid eyes.
		Eyes eyes = new Eyes(runner);

		setUp(eyes);

		try {
			// ⭐️ Note to see visual bugs, run the test using the above URL for the 1st run.
			// but then change the above URL to https://demo.applitools.com/index_v2.html
			// (for the 2nd run)
			ultraFastTest(webDriver, eyes);

		} finally {
			tearDown(webDriver, runner);
		}

	}

	public static void setUp(Eyes eyes) {

		// Initialize eyes Configuration
		Configuration config = new Configuration();

		// You can get your api key from the Applitools dashboard
		config.setApiKey("lIjxYcX0O106xzcpHbrnKnzlOjtlRCEht6ah5kBddK2DU110");

		// create a new batch info instance and set it to the configuration
		config.setBatch(new BatchInfo("UFG Hackathon"));

		// Add browsers with different viewports
		config.addBrowser(800, 600, BrowserType.CHROME);
		config.addBrowser(700, 500, BrowserType.FIREFOX);
		config.addBrowser(1600, 1200, BrowserType.IE_11);
		config.addBrowser(1024, 768, BrowserType.EDGE_CHROMIUM);
		config.addBrowser(800, 600, BrowserType.SAFARI);

		// Add mobile emulation devices in Portrait mode
		config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
		config.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);

		// Set the configuration object to eyes
		eyes.setConfiguration(config);

	}

	public static void ultraFastTest(WebDriver webDriver, Eyes eyes) {

		try {

			/**********Task - 1 *********/
			webDriver.get("https://demo.applitools.com/gridHackathonV2.html");
			eyes.open(webDriver, "Applifashion", "Task 1", new RectangleSize(800, 600));
			eyes.check("Cross-Device Elements Test",Target.window().fully().withName("Applifashion launch Page"));
			eyes.closeAsync();
						
			/**********Task - 2*********/
			webDriver.get("https://demo.applitools.com/gridHackathonV2.html");
			eyes.open(webDriver, "Applifashion", "Task 2", new RectangleSize(800, 600));
			webDriver.findElement(By.id("A__openfilter__207")).click();
			webDriver.findElement(By.id("SPAN__checkmark__107")).click();
			webDriver.findElement(By.id("filterBtn")).click();
			eyes.check("Filter Results", Target.region(By.id("product_grid")));
			eyes.closeAsync();

			/**********Task - 3*********/
			// Navigate to the url we want to test
			webDriver.get("https://demo.applitools.com/gridHackathonV2.html");
			eyes.open(webDriver, "Applifashion", "Task 3", new RectangleSize(800, 600));
			webDriver.findElement(By.id("A__openfilter__207")).click();
			webDriver.findElement(By.id("SPAN__checkmark__107")).click();
			webDriver.findElement(By.id("filterBtn")).click();
			webDriver.findElement(By.id("product_1")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			eyes.check("Product Details test", Target.window().fully().withName("Product Details test Page"));
			eyes.closeAsync();
			
		} finally  {
			eyes.abortAsync();
		}

	}

	private static void tearDown(WebDriver webDriver, VisualGridRunner runner) {
		// Close the browser
		webDriver.quit();

		// we pass false to this method to suppress the exception that is thrown if we
		// find visual differences
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		System.out.println(allTestResults);
	}

}