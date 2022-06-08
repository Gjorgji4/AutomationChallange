package BaseUtilities;

import Drivers.ChromeDriverOption;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.List;
import java.util.Properties;


public class HelperMethods extends ChromeDriverOption  {

    public HelperMethods() {}

    /**
     * Helper method for get property value from selector.properties and config.properties
     * @parater property name
     * @param typeOfProp name the type of property
     * @return property value
     * @throws Exception
     */
    public String getProperties(String property, String typeOfProp) throws Exception  {
        String filePath;
        switch(typeOfProp) {
            case "selector":
                filePath = "src/main/resources/selectors.properties";
                break;
            case "config":
                filePath = "src/main/resources/config.properties";
                break;
            default:
                filePath = "";

        }

//        String filePath = isSelector ? "src/main/resources/selectors.properties" : "src/main/resources/config.properties";
        try {
            InputStream input = new FileInputStream(filePath);
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(property);
        }
        catch(Exception e) {
            throw new FileNotFoundException();
        }

    }

    /**
     * Use this method to scroll to web element using JavaScript
     * @param element web element
     * @throws Exception
     */
    public void scrollIntoElement (WebElement element) {
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e){}
    }

    public void scrollDown () {
        ((JavascriptExecutor)getWebDriver()).executeScript("window.scrollBy(0,350)", "");
    }

    public void hoverOverElement (WebElement webElement) {
        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        ((JavascriptExecutor)getWebDriver()).executeScript(javaScript, webElement);
    }

    public void clearFocus(WebElement element ) {
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].blur();", element);
    }


    /**
     * Use this function for getting element
     * @param by
     * @return web element, or null if is empty
     */
    public WebElement getElement(By by) {
        try {
            return getWebDriver().findElement(by);
        } catch(Exception e) {
            return null;
        }
    }
    public WebElement getInnerElement (WebElement element, By by) {
        try {
            return element.findElement(by);
        }
        catch(Exception e) {
           return null;
        }
    }

    public List<WebElement> getElements (By by) {
         return getWebDriver().findElements(by);
    }

    /**
     * Helper method for checking status code  from element    *
     *
     **/

    public void checkStatusCode(String url, int expectedStatusCode) {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(url);
        int statusCode = response.getStatusCode();
        System.out.println( "Url: " + url + "Status code " + statusCode);
        MatcherAssert.assertThat("Message: Element or page has wrong status code, url: " + url,
                statusCode, CoreMatchers.equalTo(expectedStatusCode));
    }


    /**
     * Helper method for geting status code
     * @param url
     * @return status code
     */
    public int getStatusCode (String url ) {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(url);
        int statusCode = response.getStatusCode();
        return statusCode;
    }



}
