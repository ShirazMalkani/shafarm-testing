package com.raqami.shafarm.view;

import com.raqami.shafarm.selenium.config.SeleniumConfig;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class AdactinHotelTest {

    private static SeleniumConfig seleniumConfig;
    private static WebDriver driver;

    private final String adactinHotelLoginpageUrl = "https://adactin.com/HotelApp/";
    private final String username = "ShirazMalkani";
    private final String password = "Malkani";
    private final String invalidUsername = "abc";
    private final String emptyUsername = "";
    private final String emptyPassword = "";

    @BeforeClass
    public static void setUp() {
        seleniumConfig = new SeleniumConfig();
    }

    @Before
    public void setDriver()
    {
        seleniumConfig.setupDriver();
        driver = seleniumConfig.getDriver();
    }

    private void setURL(String url) {
        driver.get(url);
    }

    @Test
    public void testLoginWithCorrectUserNameAndCorrectPassword() throws URISyntaxException {
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        userNameElement.sendKeys(username);
        passwordElement.sendKeys(password);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        assertEquals("https://adactin.com/HotelApp/SearchHotel.php", driver.getCurrentUrl());
    }

    @Test
    public void testLoginWithIncorrectUserNameAndCorrectPassword() throws URISyntaxException {
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

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
    public void testLoginFailureWithEmptyUsernameAndEmptyPassword() {
        setURL(adactinHotelLoginpageUrl);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        userNameElement.sendKeys(emptyUsername);
        passwordElement.sendKeys(emptyPassword);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        WebElement usernameErrorSpan = driver.findElement(By.className("login_error"));

        if(null == usernameErrorSpan) {
            assert false;
        }
        else {
            assert true;
            assertEquals("Enter Username", usernameErrorSpan.getText());
        }
    }


    @After
    public void closeDriver() {
        driver.close();
    }

}
