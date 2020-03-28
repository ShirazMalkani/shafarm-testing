package com.raqami.shafarm.config;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Getter
public class SeleniumConfig {

    private static String driverFile;
    private ChromeDriverService service;
    private ChromeOptions options;
    private WebDriver driver;

    public SeleniumConfig() throws IOException {

        driverFile = PropertiesLoader.loadProperties("application.properties").getProperty("selenium.chromedriver");
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverFile))
                .build();

        options = new ChromeOptions();
        options.addArguments("--no-sandbox");                                           // Bypass OS security model, MUST BE THE VERY FIRST OPTION

        // browser won't be opened
        // FOLLOWING SEVERE LEVEL LOG IS ALSO DUE TO THIS
        // [1584195551.689][SEVERE]: Timed out receiving message from renderer: 0.100
        options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized");                                        // open Browser in maximized mode
        options.addArguments("disable-infobars");                                       // disabling infobars
        options.addArguments("--disable-extensions");                                   // disabling extensions
        options.addArguments("--disable-gpu");                                          // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage");                                // overcome limited resource problems
    }

    public void setupDriver() {
        this.driver = new ChromeDriver(service, options);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    static {
        System.setProperty("webdriver.chrome.driver", "/home/shiraz/Documents/maju - spring 2020/Testing/chromedriver_linux64/chromedriver");
//        System.setProperty("webdriver.gecko.driver", findFile("geckodriver"));
    }

    static private String findFile(String filename) {
        String[] paths = {"/home/shiraz/Documents/maju - spring 2020/Testing/", "bin/", "target/classes"};
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        System.out.println("hello");
        return "";
    }
}