package withBadpractices;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

public class CheckoutTestCaseWithBadPractice {

    private String link_to_store = "li[id='menu-item-1227'] a[class='menu-link']";
    private String product_search_field = "input[id='woocommerce-product-search-field-0']";
    private String button_search = "button[value='Search']";
    private String title_seach_item = ".woocommerce-products-header__title.page-title";
    private String expected_search_title = "Search results: “Blue”";
    private String button_add_blue_shoes_to_cart = "a[aria-label='Add “Blue Shoes” to your cart']";
    private String link_blue_shoes_view_cart_link = "a[title='View cart']";
    private String product_name_in_cart = "td[class='product-name'] a";
    private String expected_Product_name = "Blue Shoes";
    private String button_proceed_to_checkout = ".checkout-button";  //using compound class

    //User details hardcoded
    private String user_first_name = "One";
    private String user_last_name = "AutoUser";
    private String user_country = "United States (US)";
    private String user_street_address = "203 Farns Avenue";
    private String user_city = "Dallas";
    private String user_state = "Texas";
    private String user_zipcode = "75001";
    private String user_email = "autotestuser1@askomdch.com";

    private String successful_payment_message = "Thank you. Your order has been received.";
    //locators
    private String user_first_name_locator = "#billing_first_name";
    private String user_last_name_locator = "#billing_last_name";
    private String user_country_dropdown_locator = "#select2-billing_country-container";
    private String user_street_address_locator = "#billing_address_1";
    private String user_city_locator = "#billing_city";
    private String user_state_drodown_locator = "#select2-billing_state-container";
    private String user_zipcode_locator = "#billing_postcode";
    private String user_email_locator = "#billing_email";

    private String place_order_button_locator = "#place_order";
    private String successful_payment_message_locator = ".woocommerce-notice.woocommerce-notice--success.woocommerce-thankyou-order-received";

    private String returning_customer_message_locator_at_place_order_page = "form[class='woocommerce-form woocommerce-form-login login'] p:nth-child(1)";
    private String returning_customer_message_text_at_place_order_page = "If you have shopped with us before, please enter your details below. If you are a new customer, please proceed to the Billing section.";
    private String customer_signin_link = ".showlogin";
    private String user_name_textbox_locator_at_place_order_page = "#username";
    private String user_password_textbox_locator_at_place_order_page = "#password";
    private String user_login_button_locator_at_place_order_page = "button[value='Login']";

    //in order to get the value of registered user from TestCasesToRegisterATestUser, the object has to be called
    TestCasesToRegisterATestUser registeredUser = new TestCasesToRegisterATestUser();
    Field registeredUserName = registeredUser.getClass().getDeclaredField("myUser");


    String myRegisteredUserName = registeredUser.getMyUser();
    String myRegisteredUserPassword = registeredUser.getMyUserPassword();

    public CheckoutTestCaseWithBadPractice() throws NoSuchFieldException {
    }

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
                expected_search_title);
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
        driver.findElement((By.cssSelector(user_first_name_locator))).sendKeys(user_first_name);
        driver.findElement(By.cssSelector(user_last_name_locator)).sendKeys(user_last_name);

        /*
        // aproach for dropdown selection
        WebElement countrySelection = driver.findElement(By.cssSelector("#select2-billing_country-result-w8k0-US"));
        countrySelection.click();
        // Select countrySelect = new Select(countrySelection);
        // countrySelect.selectByValue(user_country);
        */

        driver.findElement(By.cssSelector(user_street_address_locator)).sendKeys(user_street_address);
        driver.findElement(By.cssSelector(user_city_locator)).sendKeys(user_city);

        /*
        //when there is a second dropdown
        WebElement stateSelection = driver.findElement(By.cssSelector(user_state_drodown_locator));
        Select stateSelect = new Select(stateSelection);
        stateSelect.selectByValue(user_state);
        */

        driver.findElement(By.cssSelector(user_zipcode_locator)).sendKeys(user_zipcode);
        driver.findElement(By.cssSelector(user_email_locator)).sendKeys(user_email);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(place_order_button_locator)).click();
        Thread.sleep(5000);
        System.out.println("success payments text = " + driver.findElement(By.cssSelector(successful_payment_message_locator)).getText());

        Assert.assertEquals(
                driver.findElement(By.cssSelector(successful_payment_message_locator)).getText(),
                successful_payment_message);

        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void loginAsUserAndCheckoutUsingDirectBankTransfer() throws InterruptedException {
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
                expected_search_title);
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
        driver.findElement((By.cssSelector(user_first_name_locator))).sendKeys(user_first_name);
        driver.findElement(By.cssSelector(user_last_name_locator)).sendKeys(user_last_name);

        /*
        // aproach for dropdown selection
        WebElement countrySelection = driver.findElement(By.cssSelector("#select2-billing_country-result-w8k0-US"));
        countrySelection.click();
        // Select countrySelect = new Select(countrySelection);
        // countrySelect.selectByValue(user_country);
        */

        driver.findElement(By.cssSelector(user_street_address_locator)).sendKeys(user_street_address);
        driver.findElement(By.cssSelector(user_city_locator)).sendKeys(user_city);

        /*
        //when there is a second dropdown
        WebElement stateSelection = driver.findElement(By.cssSelector(user_state_drodown_locator));
        Select stateSelect = new Select(stateSelection);
        stateSelect.selectByValue(user_state);
        */

        driver.findElement(By.cssSelector(user_zipcode_locator)).sendKeys(user_zipcode);
        driver.findElement(By.cssSelector(user_email_locator)).sendKeys(user_email);
        Thread.sleep(5000);

        //Now to login as a registered user
        driver.findElement(By.cssSelector(customer_signin_link)).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(user_name_textbox_locator_at_place_order_page)).sendKeys(myRegisteredUserName);
        driver.findElement(By.cssSelector(user_password_textbox_locator_at_place_order_page)).sendKeys(myRegisteredUserPassword);
        driver.findElement(By.cssSelector(user_login_button_locator_at_place_order_page)).click();
        Thread.sleep(3000);
        //confirm that the user is logged by not seeing the customer singin link. - Chat GPT
        //Assert.assertTrue(!driver.findElement(By.cssSelector(customer_signin_link)).isDisplayed());
        List<WebElement> elements = driver.findElements(By.cssSelector(customer_signin_link));
        if (elements.size() == 0) {
            System.out.println("customer_signin_link not found");
        }
        else {
            System.out.println("customer_signin_link found");
        }

        driver.findElement(By.cssSelector(place_order_button_locator)).click();

        Thread.sleep(5000);
        System.out.println("success payments text = " + driver.findElement(By.cssSelector(successful_payment_message_locator)).getText());

        Assert.assertEquals(
                driver.findElement(By.cssSelector(successful_payment_message_locator)).getText(),
                successful_payment_message);

        Thread.sleep(5000);
        driver.quit();
    }

}
