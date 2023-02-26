package best_practices.page_object_model.pages;

import best_practices.page_object_model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    //
    private String link_to_store = "li[id='menu-item-1227'] a[class='menu-link']";
    private By store_menu_link_in_home_page = By.cssSelector(link_to_store);

    public HomePage(WebDriver driver) {
        super(driver);
    }
    /*
        As after clicking the store menu link we will no more be in homepage but we will be in store Page.
        So after clicking store menu link, the method should return StorePage object and its driver.
        ----> This is called fluent interface.
     */
    public StorePage clickStoreMenuLink(){
        driver.findElement(store_menu_link_in_home_page).click();

        return new StorePage(driver);

    }
}
