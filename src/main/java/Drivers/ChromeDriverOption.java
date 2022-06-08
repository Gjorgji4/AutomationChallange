package Drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriverOption{

    private static WebDriver webDriver = new ChromeDriver(chromeOptions());
    private Wait wait = new WebDriverWait(webDriver, 20);


    public ChromeDriverOption() {
        super();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public static ChromeOptions chromeOptions(){
        System.setProperty("webdriver.chrome.driver","src/main/java/Drivers/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        // Start window maximized
        chromeOptions.addArguments("window-size=1920,1080");
        // Headless function comment this for using UI mode
        chromeOptions.addArguments("headless");
        return chromeOptions;
    }

    public void waitUntil(String selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    public void switchToWindow (){
        for(String winHandle : webDriver.getWindowHandles()){
            webDriver.switchTo().window(winHandle);
        }
    }

    public void backToMainWindow (String windowHandle){
        webDriver.close();
        webDriver.switchTo().window(windowHandle);
    }

    public void refresh(){
        webDriver.navigate().refresh();
    }

    public void openPage(String url) {
        try {
            webDriver.get(url);
        }
        catch(InvalidArgumentException exception) {
            System.out.println("Url: " + url + " is not valid");
        }
    }

    public void clickOnElement(WebElement element) {
        try {
            element.click();
        }
        catch(ElementClickInterceptedException | NoSuchElementException e ) {
            clickElementWithJS(element);
        }
    }

    public void clickElementWithJS (WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void closeWebDriver() {
        System.out.println("Driver is closing...");
        webDriver.close();
        webDriver.quit();
        webDriver = null;
    }
}