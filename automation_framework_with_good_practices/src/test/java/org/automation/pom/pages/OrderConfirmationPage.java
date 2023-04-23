package org.automation.pom.pages;

import org.automation.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class OrderConfirmationPage extends BasePage {

    //parameters

    String confirmationPage_messge = "Thank you. Your order has been received.";


    private final By thankYou_message_text_element = By.cssSelector(".woocommerce-thankyou-order-received");
    private final By order_number_value_element = By.cssSelector("li.woocommerce-order-overview__order.order > strong");



    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public OrderConfirmationPage confirm_order_recieved(){
        WebDriverWait wait_for_presense_of_order_number = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait_for_presense_of_order_number.until(ExpectedConditions.presenceOfElementLocated(order_number_value_element));
        // now assert the value of order number is not null and it is integer.
        //-------->https://stackoverflow.com/questions/41589576/asserting-integer-values-in-selenium-webdriver-java
        int order_number_value = Integer.parseInt(driver.findElement(order_number_value_element).getText());

        System.out.println("order_number_value = " + order_number_value);


        //Assertions for the order is successful
        Assert.assertEquals(driver.findElement(thankYou_message_text_element).getText(),confirmationPage_messge);
        Assert.assertTrue (order_number_value > 0); // to assert the value is something greater than zero.

        return this;
    }








}
