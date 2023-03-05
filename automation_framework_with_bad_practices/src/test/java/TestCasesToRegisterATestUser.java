import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class TestCasesToRegisterATestUser {

    public TestCasesToRegisterATestUser() {
    }

    public String getMyUser() {
        return myUser;
    }

    public String getMyUserEmail() {
        return myUserEmail;
    }

    public String getMyUserPassword() {
        return myUserPassword;
    }

    private final String myUser = "autotestuser1";
    private final String myUserEmail = "autotestuser1@askomdch.com";
    private final String myUserPassword = "auto123";

    private final String registrationErrorElementPath = "//div[@id='content']//li[1]";
    private final String registrationSuccessElement_Document_path = "//a[contains(text(),'Dashboard')]";

    private final String registerUsernameElementPath = "//input[@id='reg_username']";
    private final String registerEmailaddressPath = "//input[@id='reg_email']";
    private final String registerUserPasswrodPath = "//input[@id='reg_password']";
    private final String registerButtonPath = "//button[@name='register']";




    @Test
    public void registerToCheckIfUserAvailable() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver = new ChromeDriver(chromeOptions);

        //start on the account page
        driver.get("https://askomdch.com/account/");
        driver.manage().window().maximize();
        String expectedTitle = "Account – AskOmDch";
        String originalTitle = driver.getTitle();
        Assert.assertEquals(originalTitle, expectedTitle, "Title of the website donot match");

        //Register the user
        driver.findElement(By.xpath(registerUsernameElementPath)).sendKeys(myUser);
        driver.findElement(By.xpath(registerEmailaddressPath)).sendKeys(myUserEmail);
        driver.findElement(By.xpath(registerUserPasswrodPath)).sendKeys(myUserPassword);
        driver.findElement(By.xpath(registerButtonPath)).click();

        /*try{
            driver.findElement(By.xpath(registrationSuccessElement_Document_path));
            System.out.println("The login is successful.");
        } catch (Exception e){
            System.out.println("login is not successful");
        }*/

        //Logic to see if the registration is successful by finding the presense of  "Document" side menu
        // else print user is available

        //Set implict wait to 0
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0));
        //Check for element's presence
        List<WebElement> dynamicElement = driver.findElements(By.xpath(registrationSuccessElement_Document_path));
        if (dynamicElement.size() != 0) {
            System.out.println("The registration is successful !");
            System.out.printf("The user '%s' registerd and ready to use !!", myUser);
        } else if (driver.findElement(By.xpath(registrationErrorElementPath)).isDisplayed()) {
            System.out.println("The registration is un-successful.");
            System.out.printf("The user '%s' exists and you can login !!", myUser);
        } else {
            System.out.println("Something is wrong !!!");
        }
        //Revert back to default value of implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));


        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        //Finally
        driver.quit();


    }

}
