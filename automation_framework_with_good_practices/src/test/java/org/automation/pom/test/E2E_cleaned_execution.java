package org.automation.pom.test;

import org.automation.pom.base.BaseTest;
import org.automation.pom.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    @Test(priority = 1)
    public void guestCheckoutUsingDirectBankTransferWithPOMModel() throws InterruptedException {
        //Into the application
        driver.get("https://askomdch.com");
        //Instantiate  HomePage objects
        HomePage homePage = new HomePage(driver);

        //01 click on store menu link and return StorePage Objects
        StorePage storePage =  homePage.navigateToStoreUsingMenuLink();
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

    @Test(priority = 2)
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

        System.out.println("myRegisteredTestUserName = " + myRegisteredTestUserName);

        // Navigation
        HomePage homePage = new HomePage(driver);

        //01 click on store menu link and return StorePage Objects
        StorePage storePage =  homePage.navigateToStoreUsingMenuLink();
        Assert.assertEquals(driver.getTitle(),"Products – AskOmDch");
        // CartPage
        CartPage cartPage = storePage.seachForAGivenProductWithPartialName("Blue");
        Assert.assertEquals(cartPage.getProductName(),"Blue Shoes");
        //CheckOutPage
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //https://testsigma.com/blog/selenium-wait-for-page-to-load/
        Assert.assertNotNull(driver.findElement(By.cssSelector("input[id='billing_first_name']")));
      //CheckOut Page
        checkoutPage.chickHereToLoginLink(myRegisteredTestUserName, myRegisteredTestUserPassword);
        //Checkout Page - Billing details
        //OrderConfirmationPage
        OrderConfirmationPage orderConfirmationPage = checkoutPage.enter_biller_details_and_click_PlaceOrder_Button(user_first_name,user_last_name,
                user_street_address, user_city, user_zipcode,user_email, checkout_page_order_notes);
        orderConfirmationPage.confirm_order_recieved();

    }
}
