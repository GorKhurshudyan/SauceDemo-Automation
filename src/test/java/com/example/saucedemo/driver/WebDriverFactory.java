package com.example.saucedemo.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private WebDriverFactory() {
    }

    public static WebDriver createDriver(DriverType type) {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            WebDriver driver = createNewDriver(type);
            DRIVER_THREAD_LOCAL.set(driver);
            logger.debug("Created new driver instance for thread: {}", Thread.currentThread().getId());
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    private static WebDriver createNewDriver(DriverType type) {
        System.setProperty("webdriver.edge.silentOutput", "true");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        logger.info("Setting up driver for: {}", type);

        return switch (type) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                yield new ChromeDriver(chromeOptions);
            }
            case FIREFOX -> {

                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                yield new FirefoxDriver(firefoxOptions);
            }
            case EDGE -> {

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");

                yield new EdgeDriver(edgeOptions);
            }
        };
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            DRIVER_THREAD_LOCAL.remove();
            logger.debug("Closed driver instance for thread: {}", Thread.currentThread().getId());
        }
    }
}