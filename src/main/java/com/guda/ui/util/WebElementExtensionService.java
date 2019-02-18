package com.guda.ui.util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public class WebElementExtensionService {
    private static int timeoutInSeconds = 15;

    public static boolean isElementEnabled(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(d -> element.isEnabled());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(d -> element.isDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementClickable(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean titleContains(WebDriver driver, String title) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.titleContains(title));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean urlContains(WebDriver driver, String url) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.urlContains(url));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementTextPresent(WebDriver driver, WebElement element, String elementText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            return wait.until(ExpectedConditions.textToBePresentInElement(element, elementText));
        } catch (Exception e) {
            return false;
        }
    }

    public static void clickActionWithCoordinates(WebDriver driver, WebElement element, int x, int y) {
        Actions action = new Actions(driver);
        action.moveToElement(element).moveByOffset(x, y).click().build().perform();
    }

    public static void clickAction(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click().build().perform();
    }

    public static void doubleClickAction(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).doubleClick().build().perform();
    }

    public static void clickWithJavaScript(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public static void selectComboboxOptionByText(WebDriver driver, WebElement element, String value) {
        if (isElementDisplayed(driver, element)) {
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(value);
        }
    }

    public static void selectComboboxOptionByValue(WebDriver driver, WebElement element, String value) {
        if (isElementDisplayed(driver, element)) {
            Select dropdown = new Select(element);
            dropdown.selectByValue(value);
        }
    }

    public static void selectComboboxOptionByIndex(WebDriver driver, WebElement element, int index) {
        if (isElementDisplayed(driver, element)) {
            Select dropdown = new Select(element);
            dropdown.selectByIndex(index);
        }
    }

    public static void scrollToViewElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static void clearAndSendKeys(WebDriver driver, WebElement element, String value) {
        if (isElementDisplayed(driver, element)) {
            scrollToViewElement(driver, element);
            element.clear();
            element.sendKeys(value);
        }
    }

    public static void clearElementWithControls(WebDriver driver, WebElement element) {
        if (isElementDisplayed(driver, element)) {
            scrollToViewElement(driver, element);
            element.sendKeys(Keys.ARROW_LEFT + "a" + Keys.DELETE);
        }
    }

    public static void clickInAndSendKeysElement(WebDriver driver, WebElement element, String value) {
        if (isElementClickable(driver, element)) {
            scrollToViewElement(driver, element);
            element.click();
            clearAndSendKeys(driver, element, value);
        }
    }

    public static boolean getCheckboxValue(WebElement element) {
        boolean result = false;

        if (element.isSelected())
            result = true;

        return result;
    }

    public static void refreshPageJavaScript(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("location.reload();");
    }

    public static void selectDropdownListValue(WebDriver driver, WebElement selectlistElement, String optionXpath, int explicitWait) throws InterruptedException {
        if (isElementDisplayed(driver, selectlistElement)) {
            selectlistElement.click();
            Thread.sleep(explicitWait);
            WebElement selectlistOption = driver.findElement(By.xpath(optionXpath));
            Thread.sleep(explicitWait);
            selectlistOption.click();
        }

    }

    public static void sendKeysViaActions(WebDriver driver, WebElement element, String value) {
        if (isElementDisplayed(driver, element)) {
            element.click();
            Actions action = new Actions(driver);
            action.sendKeys(value).perform();
        }
    }

    public static void confirmAlert(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public static boolean isWindowDisplayed(WebDriver driver, int tabIndex) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            wait.until(d -> {
                if (d.getWindowHandles().size() > tabIndex) {
                    ArrayList<String> browserTabs = new ArrayList<>(d.getWindowHandles());
                    return d.switchTo().window(browserTabs.get(tabIndex)) != null;
                }
                return false;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void switchToWindow(WebDriver driver, int tabIndex) {
        if (isWindowDisplayed(driver, tabIndex)) {
            ArrayList<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(browserTabs.get(tabIndex));
        }
    }

    public static void switchToDefaultWindow(WebDriver driver) {
        ArrayList<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(0));
    }
}
