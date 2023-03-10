package org.automation.pom.pages;

import org.automation.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    //Params

    //Locators
    private By store_menu_link = By.cssSelector("#menu-item-1227 > a");



    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Action Methods
    public StorePage click_Store_Menu_Link(){
        driver.findElement(store_menu_link).click();
        return new StorePage(driver);
    }

}
