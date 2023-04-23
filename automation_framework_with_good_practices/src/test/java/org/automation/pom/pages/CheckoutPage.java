package org.automation.pom.pages;

import org.automation.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage extends BasePage {
    //User details for CHECKOUT PAGE hardcoded

    private final String checkout_page_order_notes = "automated by Madhu Muppala";

    private final String successful_payment_message = "Thank you. Your order has been received.";

    //checkout page parameters
    private final By biller_firstName_txtBox = By.cssSelector("input[id='billing_first_name']");
    private final By biller_lastName_txtBox = By.cssSelector("#billing_last_name");
    private final By biller_StreetName_txtBox = By.cssSelector("#billing_address_1");
    private final By biller_City_txtBox = By.cssSelector("#billing_city");
    private final By biller_ZIPCode_txtBox = By.cssSelector("#billing_postcode");
    private final By biller_EmailAddress_txtBox = By.cssSelector("#billing_email");

    private final By biller_OrderNotes_txtBox = By.cssSelector("#order_comments");

//    Handled in the click_PlaceOrder_btn()
//    private final By billing_PlaceOrder_btn = By.cssSelector("#place_order");



    //constructors
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    //methods

    public CheckoutPage enter_FirstName(String user_first_name){
        driver.findElement(biller_firstName_txtBox).clear();
        driver.findElement(biller_firstName_txtBox).sendKeys(user_first_name);
        return this;
    }

    public CheckoutPage enter_LastName(String user_last_name){
        driver.findElement(biller_lastName_txtBox).clear();
        driver.findElement(biller_lastName_txtBox).sendKeys(user_last_name);
        return this;
    }
    public CheckoutPage enter_StreetAddress(String user_street_address){
        driver.findElement(biller_StreetName_txtBox).clear();
        driver.findElement(biller_StreetName_txtBox).sendKeys(user_street_address);
        return this;
    }

    public CheckoutPage enter_City(String user_city){
        driver.findElement(biller_City_txtBox).clear();
        driver.findElement(biller_City_txtBox).sendKeys(user_city);
        return this;
    }
    public CheckoutPage enter_ZipCode(String user_zipcode){
        driver.findElement(biller_ZIPCode_txtBox).clear();
        driver.findElement(biller_ZIPCode_txtBox).sendKeys(user_zipcode);
        return this;
    }

    public CheckoutPage enter_EmailAddress(String user_email){
        driver.findElement(biller_EmailAddress_txtBox).clear();
        driver.findElement(biller_EmailAddress_txtBox).sendKeys(user_email);
        return this;
    }
    public CheckoutPage enter_OrderNotes(String billing_order_notes){
        driver.findElement(biller_OrderNotes_txtBox).clear();
        driver.findElement(biller_OrderNotes_txtBox).sendKeys(checkout_page_order_notes);
        return this;
    }

    //functional methods

    public CheckoutPage click_PlaceOrder_btn(){
        // wait for the place_order button is clickable
        //Explicit wait ---> https://www.lambdatest.com/blog/expected-conditions-in-selenium-examples/
        WebDriverWait wait_for_place_order_btn_to_be_clickable = new WebDriverWait(driver, Duration.ofSeconds(30));
        By element_place_order_button = By.cssSelector("#place_order");
        WebElement place_order_button = wait_for_place_order_btn_to_be_clickable.until((ExpectedConditions.elementToBeClickable(element_place_order_button)));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", place_order_button);

        return this;
    }
    public OrderConfirmationPage enter_biller_details_and_click_PlaceOrder_Button(String user_first_name, String user_last_name,
                                                                                  String user_street_address, String user_city, String user_zipcode,
                                                                                  String user_email, String checkout_page_order_notes){
        enter_FirstName(user_first_name);
        enter_LastName(user_last_name);
        enter_StreetAddress(user_street_address);
        enter_City(user_city);
        enter_ZipCode(user_zipcode);
        enter_EmailAddress(user_email);
        enter_OrderNotes(checkout_page_order_notes);

        click_PlaceOrder_btn();

        return new OrderConfirmationPage(driver);
    }

}
