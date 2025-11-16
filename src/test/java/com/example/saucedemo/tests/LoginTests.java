package com.example.saucedemo.tests;

import com.example.saucedemo.base.BaseTest;
import com.example.saucedemo.driver.DriverType;
import com.example.saucedemo.pages.LoginPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);

    @ParameterizedTest(name = "{index}: UC-1 Empty Credentials on {0}")
    @EnumSource(DriverType.class) // Runs on CHROME, FIREFOX, EDGE
    public void testEmptyCredentials(DriverType browserType) {
        WebDriver driver;
        try {
            driver = startDriver(browserType);
            LoginPage loginPage = new LoginPage(driver);

            loginPage.clickLogin();

            WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.errorMessage));

            String msg = loginPage.getErrorMessage();
            logger.info("[{}] Error message displayed (UC-1): {}", browserType, msg);

            assertThat(msg, containsString("Username is required"));

        } finally {
            stopDriver();
        }
    }

    @ParameterizedTest(name = "{index}: UC-2 Missing Password on {0}")
    @EnumSource(DriverType.class) // Runs on CHROME, FIREFOX, EDGE
    public void testMissingPassword(DriverType browserType) {
        WebDriver driver;
        try {
            driver = startDriver(browserType);
            LoginPage loginPage = new LoginPage(driver);

            loginPage.enterUsername("standard_user");

            loginPage.clickLogin();

            WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.errorMessage));

            String msg = loginPage.getErrorMessage();
            logger.info("[{}] Error message displayed (UC-2): {}", browserType, msg);

            assertThat(msg, containsString("Password is required"));

        } finally {
            stopDriver();
        }
    }

    private static Stream<Arguments> validLoginArguments() {
        String[][] users = {
                {"standard_user", "secret_sauce", "Swag Labs"},
                {"problem_user", "secret_sauce", "Swag Labs"},
                {"performance_glitch_user", "secret_sauce", "Swag Labs"}
        };

        return Arrays.stream(users)
                .flatMap(userData ->
                        Arrays.stream(DriverType.values())
                                .map(browserType -> Arguments.of(
                                        userData[0],
                                        userData[1],
                                        userData[2],
                                        browserType
                                ))
                );
    }

    @ParameterizedTest(name = "{index}: UC-3 Valid Login on {3} with user: {0}")
    @MethodSource("validLoginArguments")
    public void testValidLogin(String username, String password, String expectedTitle, DriverType browserType) {
        WebDriver driver;
        try {
            driver = startDriver(browserType);
            LoginPage loginPage = new LoginPage(driver);

            loginPage.enterUsername(username);
            loginPage.enterPassword(password);

            loginPage.clickLogin();

            WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
            wait.until(ExpectedConditions.titleIs(expectedTitle));

            String actualTitle = driver.getTitle();
            logger.info("[{}] Successful login. Dashboard Title: {} for user: {}", browserType, actualTitle, username);

            assertThat(actualTitle, equalTo(expectedTitle));

        } finally {
            stopDriver();
        }
    }
}