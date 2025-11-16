package com.example.saucedemo.base;

import com.example.saucedemo.driver.DriverType;
import com.example.saucedemo.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String BASE_URL = "https://www.saucedemo.com/";

    protected WebDriver startDriver(DriverType browserType) {
        WebDriver driver = WebDriverFactory.createDriver(browserType);
        driver.get(BASE_URL);
        logger.info("Test START: Navigated to SauceDemo with browser: {}", browserType);
        return driver;
    }

    protected void stopDriver() {
        WebDriverFactory.quitDriver();
        logger.info("Test END: Driver quit.");
    }
}