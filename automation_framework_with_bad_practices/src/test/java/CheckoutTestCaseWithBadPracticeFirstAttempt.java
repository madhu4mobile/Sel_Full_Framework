import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;

        /*
        Test steps :
            navigate to store
            search with term "Blue"
            Add "Blue Shoes" to the cart
            Checkout
         */

public class CheckoutTestCaseWithBadPracticeFirstAttempt {
    //parameters
    //User details for CHECKOUT PAGE hardcoded
    private final String user_first_name = "One";
    private final String user_last_name = "AutoUser";
    //private final String user_country = "United States (US)";  // Not changing default value.
    private final String user_street_address = "203 Farns Avenue";
    private final String user_city = "Dallas";
    //private final String user_state = "Texas"; // Not changing default value.
    private final String user_zipcode = "75001";
    private final String user_email = "autotestuser1@askomdch.com";
    private final String successful_payment_message = "Thank you. Your order has been received.";

    public CheckoutTestCaseWithBadPracticeFirstAttempt(){
    }

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException {
        //Initiation of WebDriverManger with chrome
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver = new ChromeDriver(chromeOptions);

        //Into the application
        driver.get("https://askomdch.com");
        driver.manage().window().maximize();

        //01 click on store menu link
        driver.findElement(By.cssSelector("#menu-item-1227 > a")).click();
        /*
        validate that you are in products page and ....
        enter Blue into search filed in products page and click SEARCH button
        Then wait for 3 seconds for the page to refresh
        and then assert the Search results: “Blue” is seen.
        */
        Assert.assertEquals(driver.getTitle(),"Products – AskOmDch");
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys("Blue");
        driver.findElement(By.cssSelector("button[value='Search']")).click();
        Thread.sleep(3000); //inorder for the page to refresh
        Assert.assertEquals(
                driver.findElement(By.cssSelector(".woocommerce-products-header__title.page-title")).getText(),
                "Search results: “Blue”");
      /*
        now click on add to clart button for the desired product
        and wait 5 second for the ajax call to be updated from server
        and click on add blue shoes add to cart link
        and again wait for 2 seconds for page to load.
        Then validate that you are in CART PAGE by validating the product name "Blue Shoes".
      */
        driver.findElement(By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("a[title='View cart']")).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.cssSelector("td[class='product-name'] a")).getText(),"Blue Shoes");
      /*
        now on CART PAGE, click on PROCEED TO CHECKOUT button
        and again wait for 2 seconds for page to load. ( use implicit wait time )
        Then validate that you are in CHECKOUT PAGE by validating that you can see first name text box by its visibility.
      */
        driver.findElement(By.cssSelector(".checkout-button.button.alt.wc-forward")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //https://testsigma.com/blog/selenium-wait-for-page-to-load/
        Assert.assertNotNull(driver.findElement(By.cssSelector("input[id='billing_first_name']")));

       /*
        now on CHECKOUT PAGE, enter details of the user. ( stick to madatory fileds for now )
            ===> first you have to clear the fileds before entering value
        Then click on PLACE ORDER button
        and again wait for 2 seconds for page to load. ( use explicit wait time ) //https://testsigma.com/blog/selenium-wait-for-page-to-load/
                    using
                    WebElement res = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(“//a/h1”)));
        Then validate that you are in CHECKOUT PAGE by validating that you can see ORDER NUMBER text.
      */
        driver.findElement(By.cssSelector("input[id='billing_first_name']")).clear();
        driver.findElement(By.cssSelector("input[id='billing_first_name']")).sendKeys(user_first_name);
        driver.findElement(By.cssSelector("#billing_last_name")).clear();
        driver.findElement(By.cssSelector("#billing_last_name")).sendKeys(user_last_name);
        driver.findElement(By.cssSelector("#billing_address_1")).clear();
        driver.findElement(By.cssSelector("#billing_address_1")).sendKeys(user_street_address);
        driver.findElement(By.cssSelector("#billing_city")).clear();
        driver.findElement(By.cssSelector("#billing_city")).sendKeys(user_city);
        driver.findElement(By.cssSelector("#billing_postcode")).clear();
        driver.findElement(By.cssSelector("#billing_postcode")).sendKeys(user_zipcode);
        driver.findElement(By.cssSelector("#billing_email")).clear();
        driver.findElement(By.cssSelector("#billing_email")).sendKeys(user_email);

        driver.findElement(By.cssSelector("#place_order")).click();
        //Explicit wait ---> https://www.lambdatest.com/blog/expected-conditions-in-selenium-examples/
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By elem_order_number = By.cssSelector(".woocommerce-order-overview__order.order");
        wait.until(ExpectedConditions.presenceOfElementLocated(elem_order_number));
        // now assert the value of order number is not null and it is integer.
        //-------->https://stackoverflow.com/questions/41589576/asserting-integer-values-in-selenium-webdriver-java
        int order_number_value = Integer.parseInt(driver.findElement(By.cssSelector("li.woocommerce-order-overview__order.order > strong")).getText());
        Assert.assertTrue (order_number_value > 0); // to assert the value is something greater than zero.

        Thread.sleep(3000);
        driver.quit();

    }

    @Test
    public void loginAsUserAndCheckoutUsingDirectBankTransfer() throws InterruptedException, NoSuchFieldException {

        //in order to get the value of registered user from TestCasesToRegisterATestUser, the object has to be called
        TestCasesToRegisterATestUser registeredUser = new TestCasesToRegisterATestUser();
        Field registeredUserName = registeredUser.getClass().getDeclaredField("myUser");
    /*
        these are obtained from getters in TestCasesToRegisterATestUser Class
        as the parameters are private in that class.
    */
        String myRegisteredUserName = registeredUser.getMyUser();
        String myRegisteredUserPassword = registeredUser.getMyUserPassword();

        //Initiation of WebDriverManger with chrome
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver = new ChromeDriver(chromeOptions);

        //Into the application
        driver.get("https://askomdch.com");
        driver.manage().window().maximize();

        //01 click on store menu link
        driver.findElement(By.cssSelector("#menu-item-1227 > a")).click();
      /*
        validate that you are in products page and ....
        enter Blue into search filed in products page and click SEARCH button
        Then wait for 3 seconds for the page to refresh
        and then assert the Search results: “Blue” is seen.
      */
        Assert.assertEquals(driver.getTitle(),"Products – AskOmDch");
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys("Blue");
        driver.findElement(By.cssSelector("button[value='Search']")).click();
        Thread.sleep(3000); //inorder for the page to refresh
        Assert.assertEquals(
                driver.findElement(By.cssSelector(".woocommerce-products-header__title.page-title")).getText(),
                "Search results: “Blue”");
      /*
        now click on add to clart button for the desired product
        and wait 5 second for the ajax call to be updated from server
        and click on add blue shoes add to cart link
        and again wait for 2 seconds for page to load.
        Then validate that you are in CART PAGE by validating the product name "Blue Shoes".
      */
        driver.findElement(By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("a[title='View cart']")).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.cssSelector("td[class='product-name'] a")).getText(),"Blue Shoes");
      /*
        now on CART PAGE, click on PROCEED TO CHECKOUT button
        and again wait for 2 seconds for page to load. ( use implicit wait time )
        Then validate that you are in CHECKOUT PAGE by validating that you can see first name text box by its visibility.
      */
        driver.findElement(By.cssSelector(".checkout-button.button.alt.wc-forward")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //https://testsigma.com/blog/selenium-wait-for-page-to-load/
        Assert.assertNotNull(driver.findElement(By.cssSelector("input[id='billing_first_name']")));
      /*
        now on CHECKOUT PAGE, click on LOGIN LINK
        and then enter userName and Password which is registered. ( get them from TestCasesToRegisterATestUser.java)
        ----> Using getters, myRegisteredUserName, myRegisteredUserPassword can be obtained
        click the LOGIN BUTTON
        Wait explicitly you don't see the LOGIN LINK.
        ======> Now validate that you dont see the click on login link is visible anymore.
      */
        driver.findElement(By.cssSelector(".showlogin")).click();
        driver.findElement(By.cssSelector("#username")).clear();
        driver.findElement(By.cssSelector("#username")).sendKeys(myRegisteredUserName);
        driver.findElement(By.cssSelector("#password")).clear();
        driver.findElement(By.cssSelector("#password")).sendKeys(myRegisteredUserPassword);
        driver.findElement(By.cssSelector("button[value='Login']")).click();
        //Now wait for some time.
        WebDriverWait wait_for_login_link = new WebDriverWait(driver, Duration.ofSeconds(15));
        By link_to_login = By.cssSelector(".showlogin");
        wait_for_login_link.until(ExpectedConditions.invisibilityOfElementLocated(link_to_login));
        //Now to validate that the click on login link is not visible anymore after a wait time.
        List<WebElement> login_link_elements = driver.findElements(By.cssSelector(".showlogin"));
        if (login_link_elements.size() == 0 ) {
            System.out.println("customer_signin_link not found - Which means the login is successful !!");
        } else {
            System.out.println("customer_signin_link found");
        }
       /*
        now on CHECKOUT PAGE, enter details of the user. ( stick to madatory fileds for now )
            ===> first you have to clear the fileds before entering value
        Then click on PLACE ORDER button
        and again wait for 2 seconds for page to load. ( use explicit wait time ) //https://testsigma.com/blog/selenium-wait-for-page-to-load/
                    using
                    WebElement res = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(“//a/h1”)));
        Then validate that you are in CHECKOUT PAGE by validating that you can see ORDER NUMBER text.
      */
        driver.findElement(By.cssSelector("input[id='billing_first_name']")).clear();
        driver.findElement(By.cssSelector("input[id='billing_first_name']")).sendKeys(user_first_name);
        driver.findElement(By.cssSelector("#billing_last_name")).clear();
        driver.findElement(By.cssSelector("#billing_last_name")).sendKeys(user_last_name);
        driver.findElement(By.cssSelector("#billing_address_1")).clear();
        driver.findElement(By.cssSelector("#billing_address_1")).sendKeys(user_street_address);
        driver.findElement(By.cssSelector("#billing_city")).clear();
        driver.findElement(By.cssSelector("#billing_city")).sendKeys(user_city);
        driver.findElement(By.cssSelector("#billing_postcode")).clear();
        driver.findElement(By.cssSelector("#billing_postcode")).sendKeys(user_zipcode);
        driver.findElement(By.cssSelector("#billing_email")).clear();
        driver.findElement(By.cssSelector("#billing_email")).sendKeys(user_email);

        driver.findElement(By.cssSelector("#place_order")).click();
        //Explicit wait ---> https://www.lambdatest.com/blog/expected-conditions-in-selenium-examples/
        WebDriverWait wait_for_order_number = new WebDriverWait(driver, Duration.ofSeconds(15));
        By elem_order_number = By.cssSelector(".woocommerce-order-overview__order.order");
        wait_for_order_number.until(ExpectedConditions.presenceOfElementLocated(elem_order_number));
        // now assert the value of order number is not null and it is integer.
        //-------->https://stackoverflow.com/questions/41589576/asserting-integer-values-in-selenium-webdriver-java
        int order_number_value = Integer.parseInt(driver.findElement(By.cssSelector("li.woocommerce-order-overview__order.order > strong")).getText());
        Assert.assertTrue (order_number_value > 0); // to assert the value is something greater than zero.

        Thread.sleep(3000);
        driver.quit();
    }
}
