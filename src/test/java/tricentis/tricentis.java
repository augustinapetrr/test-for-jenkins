package tricentis;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.util.Random;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Scanner;

public class tricentis {
	
	static ChromeDriver Driver;
	static String[] LogIn = new String[2];

	//creates the user
	@BeforeClass
	public static void setUp(){ 
		System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\chromedriver.exe");
		ChromeDriver setUp = new ChromeDriver();
		setUp.manage().window().maximize();
		setUp.get("https://demowebshop.tricentis.com/");
		setUp.findElement(By.xpath("//a[@href = \"/login\"]")).click();
		setUp.findElement(By.xpath("//div[@class = \"new-wrapper register-block\"]//input[@value = \"Register\"]")).click();
		setUp.findElement(By.xpath("//input[@id = \"gender-female\"]")).click();
		setUp.findElement(By.xpath("//input[@id = \"FirstName\"]")).sendKeys("Princess Consuela");
		setUp.findElement(By.xpath("//input[@id = \"LastName\"]")).sendKeys("Banana Hammock");
		Random ran = new Random();
		int x = ran.nextInt(5000);
		LogIn[0] = "Hammock" + x + "@gmail.com";
		LogIn[1] = "Banana123";
		setUp.findElement(By.xpath("//input[@id = \"Email\"]")).sendKeys(LogIn[0]);
		setUp.findElement(By.xpath("//input[@id = \"Password\"]")).sendKeys(LogIn[1]);
		setUp.findElement(By.xpath("//input[@id = \"ConfirmPassword\"]")).sendKeys(LogIn[1]);
		setUp.findElement(By.xpath("//input[@id = \"register-button\"]")).click();
		setUp.findElement(By.xpath("//input[@value = \"Continue\"]")).click();
		setUp.quit();
	}
	
	//starts the session and logs in
	@Before
	public void openDriverSession() {
		System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\chromedriver.exe");
		Driver = new ChromeDriver();
		Driver.manage().window().maximize();
		Driver.get("https://demowebshop.tricentis.com/");
		Driver.findElement(By.xpath("//a[@href = \"/login\"]")).click();
		Driver.findElement(By.xpath("//input[@id = 'Email']")).sendKeys(LogIn[0]);
		Driver.findElement(By.xpath("//input[@id = 'Password']")).sendKeys(LogIn[1]);
		Driver.findElement(By.xpath("//input[@value = 'Log in']")).click();
		Driver.findElement(By.xpath("//div[@class = 'block block-category-navigation']//a[@href = '/digital-downloads']")).click();
	}
	
	//closes the session and confirms the payment method
	@After
	public void closeDriverSession() {	
		Driver.findElement(By.xpath("//input[@class = 'button-1 new-address-next-step-button']")).click();
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		Driver.findElement(By.xpath("//input[@class = 'button-1 payment-method-next-step-button']")).click();
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		Driver.findElement(By.xpath("//input[@class = 'button-1 payment-info-next-step-button']")).click();
		//scrolling to confirm button
		WebElement confirm = Driver.findElement(By.xpath("//input[@class = 'button-1 confirm-order-next-step-button']"));
		JavascriptExecutor js = (JavascriptExecutor) Driver;
        js.executeScript("arguments[0].scrollIntoView();", confirm);
		confirm.click();		
		
		//checking if the cart is empty
		String order = Driver.findElement(By.xpath("//div[@class = 'title']/strong")).getText();
		if (order.equals("Your order has been successfully processed!"))
			System.out.println("Your order has been successfully processed!");
		else
			System.out.println("Your order has NOT been processed!");
		
		Driver.quit();
	}
	
	@Test
	public void test1() {
		try {
			File myObj = new File("C:\\Users\\Augistina\\eclipse-workspace\\tricentis\\src\\test\\java\\tricentis\\data1.txt");
		    Scanner myReader = new Scanner(myObj);
		    int x = 0;
		    while (myReader.hasNextLine()) {
		    	++x;
			    String data = myReader.nextLine();
			    Driver.findElement(By.xpath("//a[text() = '" + data + "']/../following-sibling::div[@class = 'add-info']/div[@class = 'buttons']/input[@value ='Add to cart']")).click();
			    //wait till the cart is updated
			    WebElement cart = Driver.findElement(By.xpath("//span[@class = 'cart-qty']"));
				WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(60));
				wait.pollingEvery(Duration.ofMillis(1000));
				wait.until(ExpectedConditions.textToBePresentInElement(cart, "(" + x + ")"));
		    }
		    myReader.close();
		} 
		catch (FileNotFoundException e) {
		    System.out.println("An error occurred while reading from data1.txt.");
		    e.printStackTrace();
		}
		Driver.findElement(By.xpath("//span[text() = 'Shopping cart']")).click();
		Driver.findElement(By.xpath("//input[@id = 'termsofservice']")).click();
		Driver.findElement(By.xpath("//button[@id = 'checkout']")).click();
		Driver.findElement(By.xpath("//select[@id = 'BillingNewAddress_CountryId']")).sendKeys(Keys.DOWN, Keys.RETURN);
		Driver.findElement(By.xpath("//input[@id = 'BillingNewAddress_City']")).sendKeys("New York");
		Driver.findElement(By.xpath("//input[@id = 'BillingNewAddress_Address1']")).sendKeys("Despreso st. 666");
		Driver.findElement(By.xpath("//input[@id = 'BillingNewAddress_ZipPostalCode']")).sendKeys("1256");
		Driver.findElement(By.xpath("//input[@id = 'BillingNewAddress_PhoneNumber']")).sendKeys("(555) 555-1234");
	}
	
	@Test
	public void test2() {
		try {
			File myObj = new File("C:\\Users\\Augistina\\eclipse-workspace\\tricentis\\src\\test\\java\\tricentis\\data2.txt");
		    Scanner myReader = new Scanner(myObj);
		    int x = 0;
		    while (myReader.hasNextLine()) {
		    	++x;
			    String data = myReader.nextLine();
			    Driver.findElement(By.xpath("//a[text() = '" + data + "']/../following-sibling::div[@class = 'add-info']/div[@class = 'buttons']/input[@value ='Add to cart']")).click();
			    //wait till the cart is updated
			    WebElement cart = Driver.findElement(By.xpath("//span[@class = 'cart-qty']"));
				WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(60));
				wait.pollingEvery(Duration.ofMillis(1000));
				wait.until(ExpectedConditions.textToBePresentInElement(cart, "(" + x + ")"));
		    }
		    myReader.close();
		} 
		catch (FileNotFoundException e) {
		    System.out.println("An error occurred while reading from data2.txt.");
		    e.printStackTrace();
		}
		Driver.findElement(By.xpath("//span[text() = 'Shopping cart']")).click();
		Driver.findElement(By.xpath("//input[@id = 'termsofservice']")).click();
		Driver.findElement(By.xpath("//button[@id = 'checkout']")).click();
	}
}
