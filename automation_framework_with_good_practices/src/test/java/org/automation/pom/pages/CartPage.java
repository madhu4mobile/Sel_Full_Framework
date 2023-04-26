package org.automation.pom.pages;

import org.automation.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
    private By title_of_product_appearance_in_cart_page =  By.cssSelector("td[class='product-name'] a");
    private By chekout_button = By.cssSelector(".checkout-button.button.alt.wc-forward");



    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Methods in cart page


    public String getProductName(){
        return driver.findElement(title_of_product_appearance_in_cart_page).getText();
    }


    public CheckoutPage clickCheckoutButton(){
        driver.findElement(chekout_button).click();
        return new CheckoutPage(driver);
    }


}
