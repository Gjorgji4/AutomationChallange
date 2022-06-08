package Pages;

import BaseUtilities.HelperMethods;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.asserts.SoftAssert;


import java.util.Arrays;
import java.util.List;

public class AboutUsPage extends HelperMethods {
    SoftAssert softAssert = new SoftAssert();

    public AboutUsPage() {}

    public AboutUsPage (SoftAssert softAssert) {
        this.softAssert = softAssert;
    }

    /**
     * Click on about us, verify left values in meta description widget, and verify the current url
     * @throws Exception
     */
    public void secondTask() throws Exception{
        String aboutUsSelector = getProperties("homepageAboutUsButton", "selector");
        WebElement aboutUsButton = getElement(By.cssSelector(aboutUsSelector));
        Thread.sleep(1000);
        Assert.assertThat("About us element in homepage menu is not displayed! ",
                aboutUsButton != null && aboutUsButton.isDisplayed(), CoreMatchers.equalTo(true));
        aboutUsButton.click();
        Thread.sleep(1000);
        // Check meta details widget displayed
        String metaDetailsWidgetSelector = getProperties("metaDetailsWidget", "selector");
        WebElement pageMetaDetailsList = getElement(By.cssSelector(metaDetailsWidgetSelector));
        MatcherAssert.assertThat("Widget meta details is missing on about us page! " + "selector: " + metaDetailsWidgetSelector,
                pageMetaDetailsList !=null && pageMetaDetailsList.isDisplayed(), CoreMatchers.equalTo(true));
        scrollIntoElement(pageMetaDetailsList);
        // Adding manually values that be checked, it would be more precise if we source like API or DB...
        List<String> expectedValues = Arrays.asList("HQ", "Founded", "Consulting Offices", "Engineering Hubs", "Clients");
        // Getting values
        Thread.sleep(1000);
        String tableActualValuesSelector = getProperties("metaDetailsWidgetLeftValues", "selector");
        List<WebElement> actualValues = getElements(By.cssSelector(tableActualValuesSelector));
        Assert.assertThat("Number of expected and actual results on meta details widget is not as expected!",
                actualValues.size(), CoreMatchers.equalTo(expectedValues.size()));
        for(int index = 0; index < expectedValues.size(); index++) {
            String actualValue = actualValues.get(index).getText();
            String expectedValue = expectedValues.get(index);
            softAssert.assertEquals(actualValue, expectedValue,
                    "Text value on meta details widgets " + actualValue + " is not as expected: " + expectedValue);
        }
        // Check about us url
        String currentUrl = getWebDriver().getCurrentUrl();
        String aboutUsUrl = getProperties("aboutUsUrl", "config");
        checkStatusCode(currentUrl, 200 );
        MatcherAssert.assertThat("About us url is not as expected! ", currentUrl, CoreMatchers.equalTo(aboutUsUrl));
//        softAssert.assertAll();
    }
}
