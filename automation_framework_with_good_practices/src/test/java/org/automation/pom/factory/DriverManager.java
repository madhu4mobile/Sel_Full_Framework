package org.automation.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    public DriverManager() {

    }

    public WebDriver initializeDriver(String browser){
        WebDriver driver = null;
        //default browser is choosen as chrome
        if (browser.equalsIgnoreCase("chrome") ){
            System.out.println("Initialization code for browser = " + browser);
            //Initiation of WebDriverManger with chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            driver = new ChromeDriver(chromeOptions);
        } else if (browser.equalsIgnoreCase("Firefox") || browser.equalsIgnoreCase("ff")){
            System.out.println("Initialization code for browser = " + browser);
            //Initiation of WebDriverManger with FireFox
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            driver = new FirefoxDriver(firefoxOptions);
        } else if (browser.equalsIgnoreCase("edge") || browser.equalsIgnoreCase("MicrosoftEdge")){
            System.out.println("Initialization code for browser = " + browser);
            //Initiation of WebDriverManger with FireFox
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            driver = new EdgeDriver(edgeOptions);
        }
        return driver;
    }
}
