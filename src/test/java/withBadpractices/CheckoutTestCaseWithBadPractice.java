package withBadpractices;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTestCaseWithBadPractice {

    private String link_to_store = "li[id='menu-item-1227'] a[class='menu-link']";
    private String product_search_field = "input[id='woocommerce-product-search-field-0']";
    private String button_search = "button[value='Search']";
    private String title_seach_item = ".woocommerce-products-header__title.page-title";
    private String expected_search_title = "Search results: “Blue”";
    private String button_add_blue_shoes_to_cart = "a[aria-label='Add “Blue Shoes” to your cart']";
    private String link_blue_shoes_view_cart_link = "a[title='View cart']";
    private String product_name_in_cart="td[class='product-name'] a";
    private String expected_Product_name = "Blue Shoes";
    private String button_proceed_to_checkout=".checkout-button";  //using compound class

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException {
        /*
        Test steps :
            navigate to store
            search with term "Blue"
            Add "Blue Shoes" to the cart
            Checkout
         */

        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver = new ChromeDriver(chromeOptions);

        //start on the account page
        driver.get("https://askomdch.com");
        driver.manage().window().maximize();
        //into the application
        driver.findElement(By.cssSelector(link_to_store)).click();
        driver.findElement(By.cssSelector(product_search_field)).sendKeys("Blue");
        driver.findElement(By.cssSelector(button_search)).click();
        Assert.assertEquals(
                driver.findElement(By.cssSelector(title_seach_item)).getText(),
                expected_search_title   );
        driver.findElement(By.cssSelector(button_add_blue_shoes_to_cart)).click();
        // wait to give room to ajax call to server for getting link.
        Thread.sleep(5000);
        driver.findElement(By.cssSelector(link_blue_shoes_view_cart_link)).click();

        //Assert for the product when you are on the Cart Page
        Assert.assertEquals(
                driver.findElement(By.cssSelector(product_name_in_cart)).getText(),
                expected_Product_name
        );
        driver.findElement(By.cssSelector(button_proceed_to_checkout)).click();
        //enter user details and address at billing details page before placing order.






    }
}
