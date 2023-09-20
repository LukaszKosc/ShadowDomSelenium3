package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver driver;

    @BeforeSuite
    public void initializeWebDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\Tools\\webdriver\\116\\chromedriver.exe");
        getDriver();
        // To open Gmail site
//        driver.get("https:// www.gmail.com");
    }
    public static WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("headless");
            options.setHeadless(true);
            driver = new ChromeDriver(options);

            // To maximize browser
            driver.manage().window().maximize();

            // Implicit wait
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }
    @AfterSuite
    public void quitDriver() {
        driver.quit();
    }
}