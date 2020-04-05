package com.raqami.shafarm.view;

import com.raqami.shafarm.config.SeleniumConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AdactinHotelTest {

    private static SeleniumConfig seleniumConfig;
    private static WebDriver driver;

    @Value("${adactin.hotel.url.login:https://adactin.com/HotelApp/}")
    private String adactinHotelLoginpageUrl;

    @Value("${login.username}")
    private String username;

    @Value("${login.password}")
    private String password;

    @Value("${login.invalid.username}")
    private String invalidUsername;

    private final String emptyUsername = "";
    private final String emptyPassword = "";

    @BeforeClass
    public static void setup() throws IOException {
        log.info("setup() function called");
        seleniumConfig = new SeleniumConfig();
    }

    @Before
    public void setDriver()
    {
        log.info("setDriver() function called");
        seleniumConfig.setupDriver();
        driver = seleniumConfig.getDriver();
    }

    private void setURL(String url) {
        driver.get(url);
    }

    @Test
    public void testLoginWithCorrectUserNameAndCorrectPassword() {
        log.info("Running testLoginWithCorrectUserNameAndCorrectPassword");
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        log.info("Parameters username:{}, password:{}", username, password);

        userNameElement.sendKeys(username);
        passwordElement.sendKeys(password);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        assertEquals("https://adactin.com/HotelApp/SearchHotel.php", driver.getCurrentUrl());
    }

    @Test
    public void testLoginWithIncorrectUserNameAndCorrectPassword() {
        log.info("Running testLoginWithIncorrectUserNameAndCorrectPassword");
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        log.info("Parameters username:{}, password:{}", invalidUsername, password);
        userNameElement.sendKeys(invalidUsername);
        passwordElement.sendKeys(password);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        WebElement authErrorElement = driver.findElement(By.className("auth_error"));

        if(null == authErrorElement) {
            assert false;
        }
        else {
            assert true;
            assertEquals("Invalid Login details or Your Password might have expired. Click here to reset your password", authErrorElement.getText());
        }
    }

    @Test
    public void testLoginFailureWithEmptyUsername() {
        log.info("Running testLoginFailureWithEmptyUsernameAndEmptyPassword");
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        log.info("Parameters username:{}, password:{}", emptyUsername, emptyPassword);
        userNameElement.sendKeys(emptyUsername);
        passwordElement.sendKeys(password);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        WebElement usernameErrorSpan = driver.findElement(By.id("username_span"));

        if(null == usernameErrorSpan) {
            assert false;
        }
        else {
            assert true;
            assertEquals("Enter Username", usernameErrorSpan.getText());
        }
    }

    @Test
    public void testLoginFailureWithCorrectUsernameButEmptyPassword() {
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        userNameElement.sendKeys(username);
        passwordElement.sendKeys(emptyPassword);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        WebElement passwordErrorSpan = driver.findElement(By.id("password_span"));

        if(null == passwordErrorSpan) {
            assert false;
        }
        else {
            assert true;
            assertEquals("Enter Password", passwordErrorSpan.getText());
        }
    }

    @After
    public void closeDriver() {
        log.info("closeDriver() function called.");
        driver.close();
    }

}
