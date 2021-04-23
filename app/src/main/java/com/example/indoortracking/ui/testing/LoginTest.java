import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginBotWithInvalidValidUser {
	static String[] usernameList = {"23222", "#$%!#", "abcd6", "{][23","Madhu23"};
	static String[] passwordList = {"hi", "9807.3", "SUTD", "][][.<", "thisisatest"};
	
	public static void main(String[] args) throws InterruptedException {		
		System.setProperty("webdriver.chrome.driver","C:/webdrivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		for (int i=0; i<usernameList.length;i++) {
			driver.get("https:127.0.0.1:8000/login/");
			
			// get the user name field of the account page
			WebElement username = driver.findElement(By.id("username"));
				
			// send my user name to fill up the box
			username.sendKeys(usernameList[i]);
	
			// locate the "Next" button in the account page
			WebElement password = driver.findElement(By.id("password"));		
			password.sendKeys(passwordList[i]);
			
			// login and :)
			WebElement nextButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/input[3]"));		
			nextButton.click();
		
	}
}

}
