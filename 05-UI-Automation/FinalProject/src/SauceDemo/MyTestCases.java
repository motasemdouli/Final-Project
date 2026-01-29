package SauceDemo;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {

	WebDriver driver = new EdgeDriver();
	String TheWebsite = "https://www.saucedemo.com";
	String TheUserName = "standard_user";
	String ThePassword = "secret_sauce";

	@BeforeTest
	public void mySetup() {
		driver.get(TheWebsite);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(priority = 1)
	public void Login() {
		WebElement theUserNameInputField = driver.findElement(By.id("user-name"));
		WebElement thePasswordInputField = driver.findElement(By.id("password"));
		WebElement theLoginButton = driver.findElement(By.id("login-button"));
		theUserNameInputField.sendKeys(TheUserName);
		thePasswordInputField.sendKeys(ThePassword);
		theLoginButton.click();
	}

	@Test(priority = 2)
	public void addItemToTheCart() {
		WebElement AddBacPack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
		AddBacPack.click();
	}

	@Test(priority = 3)
	public void Remove() {
		WebElement RemoveBackPack = driver.findElement(By.id("remove-sauce-labs-backpack"));
		RemoveBackPack.click();
		addItemToTheCart();
	}

	@Test(priority = 4)
	public void Cart() {
		WebElement theCart = driver.findElement(By.className("shopping_cart_link"));
		theCart.click();
	}

	@Test(priority = 5)
	public void Chekout() {
		WebElement chekoutBtn = driver.findElement(By.id("checkout"));
		chekoutBtn.click();

		WebElement firstName = driver.findElement(By.id("first-name"));
		WebElement lastName = driver.findElement(By.id("last-name"));
		WebElement postalCode = driver.findElement(By.id("postal-code"));
		WebElement continueBtn = driver.findElement(By.id("continue"));

		firstName.sendKeys("Motasem");
		lastName.sendKeys("Doleh");
		postalCode.sendKeys("161718");
		continueBtn.click();

		WebElement finishBts = driver.findElement(By.id("finish"));
		finishBts.click();

		WebElement confirmationMessage = driver.findElement(By.className("complete-header"));
		String confirmationText = confirmationMessage.getText();
		assert confirmationText.equals("Thank you for your order!") : "Test failed, message not found!";

		WebElement backHome = driver.findElement(By.id("back-to-products"));
		backHome.click();
	}

	@Test(priority = 6)
	public void randomSortTest() {
		String[] sortOptions = { "az", "za", "lohi", "hilo" };

		Random random = new Random();
		int randomIndex = random.nextInt(sortOptions.length);
		String selectedSortOption = sortOptions[randomIndex];

		WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
		Select sortSelect = new Select(sortDropdown);
		sortSelect.selectByValue(selectedSortOption);

		if (selectedSortOption.equals("az")) {
			WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='inventory_item_name'])[1]"));
			String firstProductName = firstProduct.getText();
			assert firstProductName.equals("Sauce Labs Backpack") : "First product is not sorted as expected (A to Z).";
		} else if (selectedSortOption.equals("za")) {
			WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='inventory_item_name'])[1]"));
			String firstProductName = firstProduct.getText();
			assert firstProductName.equals("Test.allTheThings() T-Shirt (Red)")
					: "First product is not sorted as expected (Z to A).";
		} else if (selectedSortOption.equals("lohi")) {
			WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[1]"));
			String firstProductPrice = firstProduct.getText();
			assert firstProductPrice.equals("$7.99") : "First product is not sorted as expected (Low to High).";
		} else if (selectedSortOption.equals("hilo")) {
			WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[1]"));
			String firstProductPrice = firstProduct.getText();
			assert firstProductPrice.equals("$49.99") : "First product is not sorted as expected (High to Low).";
		}
	}

	@Test(priority = 7)
	public void validateCartFunctionality() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

	    if (driver.findElements(By.id("login-button")).size() > 0) {
	        Login();
	    }

	    driver.get("https://www.saucedemo.com/inventory.html");

	    
	    if (driver.findElements(By.id("remove-sauce-labs-backpack")).size() > 0) {
	        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
	    }

	    
	    driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

	    
	    driver.findElement(By.className("shopping_cart_link")).click();

	    
	    WebElement cartItemName = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
	    assert cartItemName.getText().equals("Sauce Labs Backpack") : "Product not found in the cart.";

	    
	    driver.findElement(By.id("remove-sauce-labs-backpack")).click();

	    
	    wait.until(ExpectedConditions.numberOfElementsToBe(By.className("cart_item"), 0));
	    List<WebElement> itemsAfterRemove = driver.findElements(By.className("cart_item"));
	    assert itemsAfterRemove.isEmpty() : "Cart is not empty after removal.";
	}


	@Test(priority = 8)
	public void logout() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

		WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
		menuButton.click();

		WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
		logoutButton.click();

		WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		assert loginButton.isDisplayed() : "Logout failed, login button is not visible.";
	}

	@AfterTest
	public void AfterFinishinhTheTest() {
		driver.quit();
	}
}
