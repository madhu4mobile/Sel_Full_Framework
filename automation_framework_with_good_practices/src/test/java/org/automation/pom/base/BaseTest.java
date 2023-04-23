package org.automation.pom.base;

import org.automation.pom.factory.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeTest
    public synchronized void startDriver(@Optional String browser){
        browser = System.getProperty("browser", browser);
        if(browser == null) browser = "chrome";
        driver = new DriverManager().initializeDriver(browser);
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();

    }
}
