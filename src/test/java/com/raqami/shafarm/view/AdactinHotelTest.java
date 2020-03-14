package com.raqami.shafarm.view;

import com.raqami.shafarm.selenium.config.SeleniumConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
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
