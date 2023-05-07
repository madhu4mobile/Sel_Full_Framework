package org.automation.pom.test;

import org.automation.pom.base.BaseTest;
import org.automation.pom.objects.BillingAddress;
import org.automation.pom.pages.*;
import org.automation.pom.utils.JacksonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
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
/*    private  String user_first_name = "One";
    private  String user_last_name = "AutoUser";
    //private final String user_country = "United States (US)";  // Not changing default value.
    private  String user_street_address = "203 Farns Avenue";
    private  String user_city = "Dallas";
    //private final String user_state = "Texas"; // Not changing default value.
    private  String user_zipcode = "75001";
    private  String user_email = "autotestuser1@askomdch.com";
    private  String checkout_page_order_notes = "automated by Madhu Muppala";*/
    private  String user_first_name ;
    private  String user_last_name ;
    //private final String user_country = "United States (US)";  // Not changing default value.
    private  String user_street_address  ;
    private  String user_city  ;
    //private final String user_state = "Texas"; // Not changing default value.
    private  String user_zipcode  ;
    private  String user_email  ;
    private  String checkout_page_order_notes ;
    //private final String successful_payment_message = "Thank you. Your order has been received.";

    /*//one way of setting billing object values
    BillingAddress billingAddress = new BillingAddress()
            .setFirstname(user_first_name)
            .setLastname(user_last_name)
            .setStreetaddress(user_street_address)
            .setCity(user_city)
            .setEmail(user_email)
            .setZipcode(user_zipcode)
            .setPageOrderNotes(checkout_page_order_notes);*/
    // The other way of setting
    //BillingAddress billingAddress = new BillingAddress(user_first_name,user_last_name,user_street_address,user_city,user_zipcode,user_email,checkout_page_order_notes);


    public E2E_cleaned_execution(){
    }

    @Test(priority = 1)
    public void guestCheckoutUsingDirectBankTransferWithPOMModel() throws IOException, InterruptedException {
        BillingAddress billingAddress = new BillingAddress();
        String filePath = "src/test/resources/";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("myBillingAddress.json");
        billingAddress = JacksonUtils.deserializeJson(inputStream, billingAddress);

        //Into the application
        //driver.get("https://askomdch.com");
        //Instantiate  HomePage objects
        HomePage homePage = new HomePage(driver).loadHomePage();

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
//        OrderConfirmationPage orderConfirmationPage = checkoutPage.enter_biller_details_and_click_PlaceOrder_Button(user_first_name,user_last_name,
//                user_street_address, user_city, user_zipcode,user_email, checkout_page_order_notes);
        checkoutPage.setBillingAddress(billingAddress);
        checkoutPage.confirm_order_recieved();
    }

    @Test(priority = 2)
    public void loginAsUserAndCheckoutUsingDirectBankTransferWithPOMModel() throws InterruptedException, NoSuchFieldException {

        //Into the application
        //driver.get("https://askomdch.com");
        //driver.manage().window().maximize();
        // Navigation
        HomePage homePage = new HomePage(driver).loadHomePage();

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
        //HomePage homePage = new HomePage(driver);

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
        checkoutPage.enter_biller_details_and_click_PlaceOrder_Button(user_first_name,user_last_name,
                user_street_address, user_city, user_zipcode,user_email, checkout_page_order_notes);
        checkoutPage.confirm_order_recieved();

    }
}
