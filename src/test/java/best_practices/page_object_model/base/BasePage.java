package best_practices.page_object_model.base;

import org.openqa.selenium.WebDriver;

public class BasePage {

    /*
    Use of Base Page is to control WebDriver Driver and use it for all sub pages without duplication.
     */

    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }
    //Base Page should be clean and it should have only a url to load at the maximum.
}
