package am.qa.ituniversity.automation;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GlassdoorTest {

	@Test
	public void firstTest() {
		System.out.println("this is my first test");
	}
	
	@Test
	public void secondTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\hovsepyana\\Downloads\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.glassdoor.com");
		Assert.assertTrue(driver.getCurrentUrl().contains("https://www.glassdoor.com/index.htm"));
		
		driver.manage().window().maximize();
		//Scenario 1.
		//Given that browser is open
		//when user navigates to glassdoor.com
		//then glassdoor page is displayed with sign in button and login with google or facebook possibilities
		
		Assert.assertFalse(driver.findElements(By.xpath("//div[@class='locked-home-sign-in']/a")).isEmpty());
		Assert.assertFalse(driver.findElements(By.xpath("//div[@id='FbButton']")).isEmpty());
		//to do - - assert google login button is visible -- kam findElements(By.xpath(googleButtonXPath)).isEmpty ==false
		Assert.assertTrue(driver.findElements(By.xpath("//button[@data-test='googleBtn']")).size()>=1);
		
		//Scenario 2.
		//Given that glassdoor guest home page is open
		//When user types incorrect email such as "tttt" and password "tttt" and presses enter
		//Then error message is displayed
		
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("ttttt");
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("tttt");
		driver.findElement(By.xpath("//input[@id='userEmail']")).click();
		Assert.assertTrue(!driver.findElements(By.xpath("//input[@id='userEmail']/../../div[@data-test='error']")).isEmpty());
		Assert.assertTrue(driver.findElements(By.xpath("//input[@id='userPassword']/../../div[@data-test='error']")).size()>=1);
		
		//Scenario 3.
		//Given that glassdor page is displayed
		//When passing correct email address such as goga@gmail.com and password as "12345678"
		//Then error message "//*[text()='The password you just entered is too common. Please enter a more secure password.']" shall be displayed
		
		driver.findElement(By.xpath("//input[@id='userEmail']")).clear();
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("goga@gmail.com");
		driver.findElement(By.xpath("//input[@id='userPassword']")).clear();
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("12345678");
		driver.findElement(By.xpath("//button[contains(@class, 'signupSubmit') and @type='submit']")).click();
		  
		Thread.sleep(2000);
		Assert.assertTrue(driver.findElement(By.xpath("//*[text()='The password you just entered is too common. Please enter a more secure password.']")).isDisplayed());
		
		//Scenario 4.
		//Given that glassdoor page is displayed
		//When passing correct email such as chrtina@gmail.com and password "Aa12345678" and clicks the Continue with Email button
		//Then user shall be automatically logged in
		
		// initialize a Random object somewhere; you should only need one
		Random random = new Random();

		// generate a random integer from 0 to 899, then add 100
		int x = random.nextInt(9999) + 100;
		String username = "christna"+ x +"@gmail.com";
		driver.findElement(By.xpath("//input[@id='userEmail']")).clear();
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='userPassword']")).clear();
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("Aa12345678");
		driver.findElement(By.xpath("//button[contains(@class, 'signupSubmit') and @type='submit']")).click();
	
		Thread.sleep(2000);
		Assert.assertTrue(driver.findElements(By.xpath("//button[contains(@class, 'signupSubmit')]")).isEmpty());
		
		
		//Scenario 5.
		//Given that user is on Member home - registration 2nd page
		//when user Clicks on LOGO
		//then member home page with unfinished profile is displayed
		
		//scenario 5 depends on scenario 4
		driver.findElement(By.xpath("//a[@data-test='header-glassdoor-logo']")).click();
		Thread.sleep(2000);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@data-test='profile-container']//*[text()='"+username+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@data-test='profile-container']//button")).isDisplayed());
		
		
	}
}
