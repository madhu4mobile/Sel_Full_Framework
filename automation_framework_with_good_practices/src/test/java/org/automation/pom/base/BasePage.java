package org.automation.pom.base;

import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver=driver;
    }

    public BasePage loadUrl(String endpoint){
        driver.get("https://askomdch.com"+ endpoint);
        return this;
    }
}
