package withBadpractices;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class MyFirstTestCase {

    @Test
    public void firstDummyTest(){

        //// https://www.selenium.dev/documentation/webdriver/getting_started/install_drivers/
        //Using WebDriverManger
        WebDriverManager.chromedriver().setup();  // starting Sell > 4.0 this method of getting right browser driver is automatted

        /*
        //Using hardcoded path
        System.setProperty("webdriver.chrome.driver","bin/browsers/chromedriver.exe");

         */

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.kabum.com.br");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        //end the session
        driver.quit();



    }
}
