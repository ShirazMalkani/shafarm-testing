package com.raqami.shafarm.view;

import com.raqami.shafarm.config.SeleniumConfig;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
public class DataRowAdactinHotelTest {

    private static SeleniumConfig seleniumConfig;
    private static WebDriver driver;

    @Value("${adactin.hotel.url.login:https://adactin.com/HotelApp/}")
    private String adactinHotelLoginpageUrl;

    public void setUp() throws IOException {
        seleniumConfig = new SeleniumConfig();
    }

    public void setDriver() {
        seleniumConfig.setupDriver();
        driver = seleniumConfig.getDriver();
    }

    private void setURL(String url) {
        driver.get(url);
    }

    @ParameterizedTest
    @CsvSource({"invalid username 01, invalid password 01", "invalid username 02, invalid password 02"})
    public void loginWithInvalidUsernameAndPassword(String username, String password) throws IOException {
        setUp();
        setDriver();
        setURL(adactinHotelLoginpageUrl);

        System.out.println(username);
        System.out.println(password);

        WebElement userNameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));

        userNameElement.sendKeys(username);
        passwordElement.sendKeys(password);

        WebElement loginButtonElement = driver.findElement(By.className("login_button"));
        loginButtonElement.click();

        WebElement authErrorElement = driver.findElement(By.className("auth_error"));

        if (null == authErrorElement) {
            assert false;
        } else {
            assert true;
            assertEquals("Invalid Login details or Your Password might have expired. Click here to reset your password", authErrorElement.getText());
        }
        closeDriver();
    }

    @ParameterizedTest
    @CsvSource({"ShirazMalkani, adactinpassword"})
    public void testLoginWithCorrectUserNameAndCorrectPassword(String username, String password) throws IOException {
        setUp();
        setDriver();
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
    public void tempTest() {

    }

    public void closeDriver() {
        driver.close();
    }
}
