package org.automation.pom.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.automation.pom.base.BaseTest;
import org.automation.pom.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

        /*
        Test steps :
            navigate to store
            search with term "Blue"
            Add "Blue Shoes" to the cart
            Checkout
         */

public class E2E_cleaned_execution extends BaseTest {
    //parameters
    //User details for CHECKOUT PAGE hardcoded
    private final String user_first_name = "One";
    private final String user_last_name = "AutoUser";
    //private final String user_country = "United States (US)";  // Not changing default value.
    private final String user_street_address = "203 Farns Avenue";
    private final String user_city = "Dallas";
    //private final String user_state = "Texas"; // Not changing default value.
    private final String user_zipcode = "75001";
    private final String user_email = "autotestuser1@askomdch.com";
    private final String checkout_page_order_notes = "automated by Madhu Muppala";
    //private final String successful_payment_message = "Thank you. Your order has been received.";

    public E2E_cleaned_execution(){
    }

    @Test
    public void guestCheckoutUsingDirectBankTransferWithPOMModel() throws InterruptedException {
        //Into the application
        driver.get("https://askomdch.com");
        //Instantiate  HomePage objects
        HomePage homePage = new HomePage(driver);

        //01 click on store menu link and return StorePage Objects
        StorePage storePage =  homePage.click_Store_Menu_Link();
        Assert.assertEquals(driver.getTitle(),"Products – AskOmDch");
        // CartPage
        CartPage cartPage = storePage.seachForAGivenProductWithPartialName("Blue");
        Assert.assertEquals(cartPage.getProductName(),"Blue Shoes");
        //CheckOutPage
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //https://testsigma.com/blog/selenium-wait-for-page-to-load/
        Assert.assertNotNull(driver.findElement(By.cssSelector("input[id='billing_first_name']")));
        //OrderConfirmationPage
        OrderConfirmationPage orderConfirmationPage = checkoutPage.enter_biller_details_and_click_PlaceOrder_Button(user_first_name,user_last_name,
                user_street_address, user_city, user_zipcode,user_email, checkout_page_order_notes);
        orderConfirmationPage.confirm_order_recieved();


    }

    @Test
    public void loginAsUserAndCheckoutUsingDirectBankTransferWithPOMModel() throws InterruptedException, NoSuchFieldException {

        //Into the application
        driver.get("https://askomdch.com");
        driver.manage().window().maximize();

        //in order to get the value of registered user from TestCasesToRegisterATestUser, the object has to be called
        TestCasesToRegisterATestUserForPOMTransition toGetTheRegisteredUser = new TestCasesToRegisterATestUserForPOMTransition();
//        Field myRegisteredUserName = toGetTheRegisteredUser.getClass().getDeclaredField("myTestUser");
    /*
        these are obtained from getters in TestCasesToRegisterATestUser Class
        as the parameters are private in that class.
    */
        String myRegisteredTestUserName = toGetTheRegisteredUser.getMyTestUser();
        String myRegisteredTestUserPassword = toGetTheRegisteredUser.getMyTestUserPassword();

        //01 click on store menu link
        driver.findElement(By.cssSelector("#menu-item-1227 > a")).click();
        //02 Product Page
        Assert.assertEquals(driver.getTitle(),"Products – AskOmDch");
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys("Blue");
        driver.findElement(By.cssSelector("button[value='Search']")).click();
        Thread.sleep(3000); //inorder for the page to refresh
        Assert.assertEquals(
                driver.findElement(By.cssSelector(".woocommerce-products-header__title.page-title")).getText(),
                "Search results: “Blue”");
        driver.findElement(By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("a[title='View cart']")).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.cssSelector("td[class='product-name'] a")).getText(),"Blue Shoes");
        //03 Cart Page
        driver.findElement(By.cssSelector(".checkout-button.button.alt.wc-forward")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //https://testsigma.com/blog/selenium-wait-for-page-to-load/
        Assert.assertNotNull(driver.findElement(By.cssSelector("input[id='billing_first_name']")));
      //CheckOut Page
        driver.findElement(By.cssSelector(".showlogin")).click();
        driver.findElement(By.cssSelector("#username")).clear();
        driver.findElement(By.cssSelector("#username")).sendKeys(myRegisteredTestUserName);
        driver.findElement(By.cssSelector("#password")).clear();
        driver.findElement(By.cssSelector("#password")).sendKeys(myRegisteredTestUserPassword);
        driver.findElement(By.cssSelector("button[value='Login']")).click();
        //Now wait for some time.
        WebDriverWait wait_for_login_link = new WebDriverWait(driver, Duration.ofSeconds(15));
        By link_to_login = By.cssSelector(".showlogin");
        wait_for_login_link.until(ExpectedConditions.invisibilityOfElementLocated(link_to_login));
        //Now to validate that the click on login link is not visible anymore after a wait time.
        List<WebElement> login_link_elements = driver.findElements(By.cssSelector(".showlogin"));
        if (login_link_elements.size() == 0 ) {
            System.out.println("customer_signin_link not found - Which means the login is successful !!");
        } else {
            System.out.println("customer_signin_link found");
        }
        //Checkout Page - Billing details
        driver.findElement(By.cssSelector("input[id='billing_first_name']")).clear();
        driver.findElement(By.cssSelector("input[id='billing_first_name']")).sendKeys(user_first_name);
        driver.findElement(By.cssSelector("#billing_last_name")).clear();
        driver.findElement(By.cssSelector("#billing_last_name")).sendKeys(user_last_name);
        driver.findElement(By.cssSelector("#billing_address_1")).clear();
        driver.findElement(By.cssSelector("#billing_address_1")).sendKeys(user_street_address);
        driver.findElement(By.cssSelector("#billing_city")).clear();
        driver.findElement(By.cssSelector("#billing_city")).sendKeys(user_city);
        driver.findElement(By.cssSelector("#billing_postcode")).clear();
        driver.findElement(By.cssSelector("#billing_postcode")).sendKeys(user_zipcode);
        driver.findElement(By.cssSelector("#billing_email")).clear();
        driver.findElement(By.cssSelector("#billing_email")).sendKeys(user_email);

        driver.findElement(By.cssSelector("#place_order")).click();
        //Explicit wait ---> https://www.lambdatest.com/blog/expected-conditions-in-selenium-examples/
        WebDriverWait wait_for_order_number = new WebDriverWait(driver, Duration.ofSeconds(15));
        By elem_order_number = By.cssSelector(".woocommerce-order-overview__order.order");
        wait_for_order_number.until(ExpectedConditions.presenceOfElementLocated(elem_order_number));
        // now assert the value of order number is not null and it is integer.
        //-------->https://stackoverflow.com/questions/41589576/asserting-integer-values-in-selenium-webdriver-java
        int order_number_value = Integer.parseInt(driver.findElement(By.cssSelector("li.woocommerce-order-overview__order.order > strong")).getText());
        System.out.println("order_number_value = " + order_number_value);
        Assert.assertTrue (order_number_value > 0); // to assert the value is something greater than zero.

        Thread.sleep(3000);
        driver.quit();
    }
}
