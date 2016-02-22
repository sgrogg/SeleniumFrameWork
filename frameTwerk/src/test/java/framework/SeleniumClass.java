package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by sterlingg on 11/24/2015.
 */
public class SeleniumClass {

    // Using Chrome driver for our WebDriver
    private static WebDriver selenium = new ChromeDriver();
    // Passing our WebDriver into wait
    private static WebDriverWait wait = new WebDriverWait(selenium, 5);


    public static void open(String url) {
        selenium.get(url);
    }

    /**
     * Accepts By Locator and waits for it to be visible. Then returns webElement
     * @param elemLoc location of waitForElement using By class
     * @return webElement
     *
     */
    public static WebElement waitForElement(By elemLoc) {
     return wait.until(ExpectedConditions.visibilityOfElementLocated(elemLoc));
    }

    /**
     *  Doesn't wait for
     * @param element
     */
    public static void click(By element) {
        selenium.findElement(element).click();
    }

    /**
     *  Waits for
     * @param element
     */
    public static void waitClick(By element) {
        waitForElement(element).click();
    }

    /**
     * takes By Element & String types the String into the field.
     * @param element
     * @param whatyouwanttotype
     */
    public static void type(By element, String whatyouwanttotype) {
        waitForElement(element).sendKeys(whatyouwanttotype);
    }

    /**
     * Takes By Element. Waits for it to appear. Then waits for it to disappear.
     * @param element
     */
    public static void waitForElementToAppearDisappear(By element) {
        waitForElement(element);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    /**
     * Takes a String that is link text. Waits for element to be visable. Clicks on link
     * @param linkText
     */
    public static void clickLinkByText(String linkText) {
        By locator = By.linkText(linkText);
        waitForElement(locator).click();
    }

    /**
     * Takes a String & webelement. Clears data in the field then types what you want.
     * @param text
     * @param element
     */
    public static void clearDataThenType(String text, By element) {
        waitForElement(element).clear();
        selenium.findElement(element).sendKeys(text);
    }

    /**
     * Takes a By Element. Returns innerText of this element, including sub-elements, without any leading or trailing whitespace.
     * @param element
     * @return
     */
    public static String getText(By element) {
        return waitForElement(element).getText();
    }

    /**
     * Takes a By Element. reutnrs boolean if element is displayed or not.
     * @param element
     * @return
     */
    public static boolean isElementVisiable(By element) {
        return selenium.findElement(element).isDisplayed();
    }

    /**
     * Takes a By Element & Attribute String. Find element and returns a String value of the attribute specified.
     * @param element
     * @param attribute
     * @return
     */
    public static String getAttributeValue(By element, String attribute) {
        return waitForElement(element).getAttribute(attribute);
    }
}

