package best_practices.page_object_model.pages;

import best_practices.page_object_model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StorePage extends BasePage {

    // element is StorePage
    private By product_search_field = By.cssSelector("input[id='woocommerce-product-search-field-0']");
    private By button_search = By.cssSelector("button[value='Search']");
    private By title_seach_item = By.cssSelector(".woocommerce-products-header__title.page-title");
    private String expected_search_title = "Search results: “Blue”";

    //element in CartPage
    private By button_add_blue_shoes_to_cart = By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']");

    public String getExpected_search_title() {
        return expected_search_title;
    }

    public void setExpected_search_title(String expected_search_title) {
        this.expected_search_title = expected_search_title;
    }

    public StorePage(WebDriver driver) {
        super(driver);
    }

    public StorePage enterTextInSearchField(String txt){
        driver.findElement(product_search_field).sendKeys(txt);
        return this;
    }

    public StorePage clickSearchBtn(){
        driver.findElement(button_search).click();
        return this;
    }
    public String getTitle(){
        return driver.findElement(title_seach_item).getText();
    }

    public CartPage clickAddToCartBtn(){

        return new CartPage(driver);
    }
}
