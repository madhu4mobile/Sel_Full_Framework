package best_practices.page_object_model.base;

import best_practices.page_object_model.factory.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void initiateDriver(){

        driver = new DriverManager().initializeDriver();
    }

    @AfterMethod
    public void quitDriver(){
        driver.quit();
    }

    //Note - dont over load BaseTest or BasePage.
    // One more can be used to take screenshots

}
