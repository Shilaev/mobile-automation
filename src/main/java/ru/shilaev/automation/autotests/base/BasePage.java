package ru.shilaev.automation.autotests.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shilaev.automation.core.driver.DriverManager;
import ru.shilaev.automation.core.exceptions.ElementException;

import java.time.Duration;

/**
 * Базовый класс для всех тестов (общие методы для всех тестов)
 */
public abstract class BasePage {
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);
    protected final AppiumDriver driver;
    protected final WebDriverWait wait;

    //region timeouts
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration SLEEP_INTERVAL = Duration.ofSeconds(500);
    //endregion

    //region constructor
    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT, SLEEP_INTERVAL);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    //endregion

    //region app controlls
    public void launchApp(){

    }
    //endregion

    //region find element methods

    /**
     * Поиск элемента по локаторам
     */
    public WebElement findElementByLocator(By... locators) {
        for (By locator : locators) {
            try {
                return driver.findElement(locator);
            } catch (NoSuchElementException e) {
                log.error("Element {} not found.{}", locator.toString(), e);
                throw new ElementException(locator.toString(), "not found");
            }
        }
        throw new NoSuchElementException("Element not found");
    }

    /**
     * Поиск элемента по resource_id
     */
    public WebElement findElementByResourceId(String resourceId) {
        try {
            String locator = driver instanceof AndroidDriver
                    ? "//*[resource-id='" + resourceId + "']" //android
                    : "//*[@name='" + resourceId + "']";      //ios
            return findElementByLocator(By.xpath(locator));
        } catch (NoSuchElementException e) {
            log.error("Element {} not found.{}", resourceId, e);
            throw new ElementException(resourceId, "not found");
        }
    }

    /**
     * Поиск по тексту
     */
    public WebElement findElementByText(String text) {
        return findElementByLocator(
                By.xpath("//*[contains(@text, '" + text + "')]"),
                By.xpath("//*[contains(@content-desc, '" + text + "')]"),
                By.xpath("//*[contains(@label, '" + text + "')]")
        );
    }
    //endregion

    //region waiters

    /**
     * Ожидание элемента
     */
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Ожидание появления эелмента
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Ожидание скрытия элемента
     */
    public boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Ожидание возможности клика по элементу
     */
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    //endregion

    //region actions
    public void click(By locator) {
        WebElement element = waitForElement(locator);
        element.click();
        log.debug("Clicked element: {}", locator);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        log.debug("Clicked element: {}", element);
    }
    //endregion
}
