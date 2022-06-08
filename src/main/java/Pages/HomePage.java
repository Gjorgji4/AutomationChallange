package Pages;
import BaseUtilities.HelperMethods;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class HomePage extends HelperMethods{

    public String homePageUrl = getProperties("homepageUrl", "config");

    public HomePage() throws Exception {}

    /**
     * Check open homepage and check url status code
     * @throws Exception
     */
    public void openHomePage() throws Exception {
        openPage(homePageUrl);
        checkAndAcceptCookieBanner();
        checkStatusCode(homePageUrl,200);
    }

    public void checkAndAcceptCookieBanner() throws Exception {
        String bannerSelector = getProperties( "bannerSelector", "selector" );
        WebElement cookieBanner = getElement(By.cssSelector(bannerSelector));
        Assert.assertThat("Cookie banner is not displayed on homepage! ",
                cookieBanner != null && cookieBanner.isDisplayed(), CoreMatchers.equalTo(true));
        String acceptButtonSelector = getProperties("acceptButton", "selector");
        WebElement acceptButton = getElement(By.cssSelector(acceptButtonSelector));
        Assert.assertThat("Cookie banner accept button  is not displayed on cookie banner on homepage! ",
                acceptButton != null && acceptButton.isDisplayed(), CoreMatchers.equalTo(true));
        acceptButton.click();
    }
}
