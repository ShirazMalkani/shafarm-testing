package com.raqami.shafarm.selenium.config;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Getter
public class SeleniumConfig {

    private static final String driverFile = "C:\\Users\\alish\\Downloads\\maju\\Testing and automation\\project + asgns\\chromiumdriver\\chromedriver.exe";
    private ChromeDriverService service;
    private ChromeOptions options;
    private WebDriver driver;

    public SeleniumConfig() {
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverFile))
                .build();
        options = new ChromeOptions();
        options.addArguments("--no-sandbox");                                           // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized");                                        // open Browser in maximized mode
        options.addArguments("disable-infobars");                                       // disabling infobars
        options.addArguments("--disable-extensions");                                   // disabling extensions
        options.addArguments("--disable-gpu");                                          // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage");                                // overcome limited resource problems
//        options.merge(capabilities);
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