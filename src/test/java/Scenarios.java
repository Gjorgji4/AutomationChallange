import BaseUtilities.HelperMethods;
import Drivers.ChromeDriverOption;
import Pages.AboutUsPage;
import Pages.Careers;
import Pages.HomePage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Scenarios {
    public HelperMethods tools = new HelperMethods();
    public HomePage homePage = new HomePage();
    public ChromeDriverOption driver = new ChromeDriverOption();
    public SoftAssert softAssert = new SoftAssert();
    public AboutUsPage aboutUsPage = new AboutUsPage(softAssert);
    public Careers careers = new Careers(softAssert);


    public Scenarios() throws Exception {}

    @Test
    public void fullTask () throws Exception {
        homePage.openHomePage();
        aboutUsPage.secondTask();
        careers.thirdTask();
        careers.fourthTask();
        careers.fifthTask();
        careers.sixthTask();
        softAssert.assertAll();
        driver.closeWebDriver();
    }
    @Test
    public void firstTask() throws Exception {
        homePage.openHomePage();
        driver.closeWebDriver();
    }

    @Test
    public void secondTask() throws Exception{
        homePage.openHomePage();
        aboutUsPage.secondTask();
        driver.closeWebDriver();
    }

    @Test
    public void thirdTask() throws Exception {
        homePage.openHomePage();
        careers.thirdTask();
        driver.closeWebDriver();
    }

    @Test
    public void fourthTask() throws Exception {
        homePage.openHomePage();
        careers.fourthTask();
        driver.closeWebDriver();
    }

    @Test
    public void fifthTask() throws Exception {
        homePage.openHomePage();
        careers.fifthTask();
        driver.closeWebDriver();
    }

    @Test
    //Driver should be manually closed
    public void sixtTask() throws Exception {
        homePage.openHomePage();
        careers.sixthTask();
    }
}
