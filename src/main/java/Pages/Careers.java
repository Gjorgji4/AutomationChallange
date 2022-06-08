package Pages;

import BaseUtilities.CustomAssert;
import BaseUtilities.HelperMethods;
import Drivers.ChromeDriverOption;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.io.FileWriter;
import java.text.Normalizer;;
import java.util.List;

public class Careers extends HelperMethods  {
    SoftAssert softAssert = new SoftAssert();
    ChromeDriverOption driver = new ChromeDriverOption();
    HomePage homePage = new HomePage();
    CustomAssert customAssert = new CustomAssert();


    public Careers() throws Exception {}

    public Careers(SoftAssert softAssert) throws Exception {
        this.softAssert = softAssert;
    }


    public void thirdTask() throws Exception {
        navigateToCurrentOpening();
        Thread.sleep(1000);
        // Check current openings
        String currentOpeningsSelector = getProperties("careersCurrentOpenings", "selector");
        List<WebElement> currentOpeningsElement = getElements(By.cssSelector(currentOpeningsSelector));
        Assert.assertThat("Current openings navigation element on careers page is not displayed",
                !currentOpeningsElement.isEmpty(), CoreMatchers.equalTo(true));

        // Hardcoded solution
//        String desiredTabSelector = getProperties("careersCurrentOpeningsSkopje",true);
//        WebElement desiredTab = getElement(By.cssSelector(desiredTabSelector));

        //Click on Skopje tab
        WebElement desiredTab = currentOpeningsElement.stream().filter(webElement -> webElement.getText().equals("Skopje")).findFirst().orElse(null);
        Assert.assertThat("Skopje tab on current openings element on careers page is not displayed",
                desiredTab !=null && desiredTab.isDisplayed(), CoreMatchers.equalTo(true));
        clickOnElement(desiredTab);

        // After loading page find first job offering element
        String jobOfferingSelector = getProperties("careersJobFiltered", "selector");
        List<WebElement> jobOfferingElement = getElements(By.cssSelector(jobOfferingSelector));
        Assert.assertThat("Current job opening cards are not displayed after clicking on Skopje tab!",
                !jobOfferingElement.isEmpty(), CoreMatchers.equalTo(true));
        scrollDown();

        // Find the first element and click on it since we have checks for all opening cards, asserter is not needed
        WebElement firstCard = jobOfferingElement.get(0);

        // Check text values inside first element and get text for each field
        String firstCardTypeSelector = getProperties("currentOpeningsJobType", "selector");
        String firstCardTitleSelector = getProperties("currentOpeningsJobTitle", "selector");
        String firstCardLocationSelector =getProperties("currentOpeningsJobLocation", "selector");
        WebElement firstCardJobType = getInnerElement(firstCard, By.cssSelector(firstCardTypeSelector));
        WebElement firstCardTitle   = getInnerElement(firstCard, By.cssSelector(firstCardTitleSelector));
        WebElement firstCardLocation= getInnerElement(firstCard, By.cssSelector(firstCardLocationSelector));

        // Store values from first card into variable
        String jobTypeText = firstCardJobType !=null && firstCardJobType.isDisplayed() ?
                Normalizer.normalize(firstCardJobType.getText(), Normalizer.Form.NFD) : " ";
        String titleText   = firstCardTitle !=null && firstCardTitle.isDisplayed() ?
                Normalizer.normalize(firstCardTitle.getText(), Normalizer.Form.NFD) : " ";
        String locationText= firstCardLocation !=null && firstCardLocation.isDisplayed() ?
                Normalizer.normalize(firstCardLocation.getText(), Normalizer.Form.NFD) : "";

        String windowHandle = driver.getWebDriver().getWindowHandle();
        Thread.sleep(1000);
        clickOnElement(firstCard);

        // Window handle
        switchToWindow();
        Thread.sleep(5000);
        String jobOfferingSecondWindowsSelector = getProperties("openedJobsDescriptionNewWindow", "selector");
        waitUntil(jobOfferingSecondWindowsSelector);

        Thread.sleep(1000);
        // Wait until element become visible
        String jobOfferingLocationSelectorNewWindow = getProperties("currentOpeningsJobLocationSecondWindow", "selector");
        String jobOfferingTypeSelectorNewWindow = getProperties("currentOpeningsJobTypeSecondWindow", "selector");
        String jobOfferingTitleSelectorNewWindow = getProperties("currentOpeningsJobTitleSecondWindow", "selector");

        waitUntil(jobOfferingLocationSelectorNewWindow);
        waitUntil(jobOfferingTypeSelectorNewWindow);
        waitUntil(jobOfferingTitleSelectorNewWindow);
        Thread.sleep(1000);
        // Get job type text
        WebElement jobOfferingInformationNewWindow = getElement(By.cssSelector(jobOfferingTypeSelectorNewWindow));
        String jobOfferTypeInformationNewWindow = jobOfferingInformationNewWindow != null ?
                Normalizer.normalize(jobOfferingInformationNewWindow.getText(), Normalizer.Form.NFD) : " ";

        // get job title text
        WebElement jobOfferingTitleNewWindow = getElement(By.cssSelector(jobOfferingTitleSelectorNewWindow));
        String jobOfferTitleInformationNewWindow = jobOfferingTitleNewWindow != null ?
                Normalizer.normalize(jobOfferingTitleNewWindow.getText(),Normalizer.Form.NFD) : "";

        // get job location text
        WebElement jobOfferingLocationNewWindow = getElement(By.cssSelector(jobOfferingLocationSelectorNewWindow));
        String jobOfferLocationInformationNewWindow = jobOfferingLocationNewWindow != null ?
                Normalizer.normalize(jobOfferingLocationNewWindow.getText(), Normalizer.Form.NFD) : "";

        // Check values
        // Update: since text font or text format is different need more investigation to get appropriate filter!
        Assert.assertThat("Value for Job Type on first job element comparing to displayed values after clicking on it is not the same!",
                jobTypeText, CoreMatchers.equalTo(jobOfferTypeInformationNewWindow));
        Assert.assertThat("Value for Job Title on first job element comparing to displayed values after clicking on it is not the same!",
                titleText, CoreMatchers.equalTo(jobOfferTitleInformationNewWindow));
        Assert.assertThat("Value for Job Location on first job element comparing to displayed values after clicking on it is not the same!",
                locationText, CoreMatchers.equalTo(jobOfferLocationInformationNewWindow));
        // Back to main window
        Thread.sleep(1000);
        backToMainWindow(windowHandle);
    }

    public void fourthTask() throws Exception {
        refresh();
        navigateToCurrentOpening();

        //First solution less code with different output format
        File notepadFile = new File("textAndLocation.txt");
        FileWriter textAndLocation = new FileWriter(notepadFile);
        String  titleAndLocationSelector = getProperties("titleAndLocationJob","selector");
        List<WebElement> titleAndLocation = getElements(By.cssSelector(titleAndLocationSelector));
        Assert.assertThat("Titles and location fields on careers cards are missing on Careers page",
                titleAndLocation.isEmpty(), CoreMatchers.equalTo(false));
        for(WebElement element : titleAndLocation) {
            textAndLocation.write(element.getText() + " \n" );
        }
        textAndLocation.close();

        // Second solution with more accuracy
        File secondNotepad = new File("secondTextAndLocationFile.txt");
        FileWriter secondTextAndLocation = new FileWriter(secondNotepad);

        String cardTitleSelector = getProperties("currentOpeningsJobTitle", "selector");
        String cardLocationSelector =getProperties("currentOpeningsJobLocation", "selector");
        List<WebElement> cardTitle   = getElements(By.cssSelector(cardTitleSelector));
        List<WebElement> cardLocation= getElements(By.cssSelector(cardLocationSelector));

        // If we assume that every card should have title and location property
        Assert.assertThat("Number of title and location of current opening cards in career page is not the same!",
        cardTitle.size(), CoreMatchers.equalTo(cardLocation.size()));
        for(int index = 0; index < cardTitle.size();index++) {
            String title = cardTitle.get(index).getText();
            String location = cardLocation.get(index).getText();
            secondTextAndLocation.write(title + ", " + location + "\n");
        }
        secondTextAndLocation.close();
    }

    public void fifthTask() throws Exception{
        navigateToCurrentOpening();
        String currentOpeningsSelector = getProperties("careersCurrentJobsAll", "selector");
        List<WebElement> allCurrentOpenings = getElements(By.cssSelector(currentOpeningsSelector));
        Assert.assertThat("There is no active jobs on careers page ",
                allCurrentOpenings.isEmpty(), CoreMatchers.equalTo(false));
        int allCurrentOpeningsValue = allCurrentOpenings.size();
        int countedValues = 0;
        String openingsTabSelector = getProperties("careersCurrentByTabs", "selector");
        List<WebElement> openingsTabs = getElements(By.cssSelector(openingsTabSelector));
        Assert.assertThat("There is no location tabs on careers page",
                openingsTabs.isEmpty(), CoreMatchers.equalTo(false));
        for (WebElement tab : openingsTabs) {
            clickOnElement(tab);
            Thread.sleep(100);
            String careersJobFilteredSelector = getProperties("careersJobFiltered","selector");
            List<WebElement> currentOpeningsFiltered = getElements(By.cssSelector(careersJobFilteredSelector));
            countedValues += currentOpeningsFiltered.size();
        }
       Assert.assertThat("Number of displayed jobs vs number of counted open positions of each location is not the same!",
               allCurrentOpeningsValue, CoreMatchers.equalTo(countedValues));
    }
    // Custom asserter example with screenshot

    // Note if i figure out the correct parameters to bypass the protection of gmail services
    // in i would add send email function to this custom asserter
    public void sixthTask() throws Exception{
        String currentOpeningsSelector = getProperties("careersCurrentJobsAll", "selector");
        List<WebElement> allCurrentOpenings = getElements(By.cssSelector(currentOpeningsSelector));
        customAssert.assertThat("Example of failen test case!",
                allCurrentOpenings.size(), CoreMatchers.equalTo(2), true);

    }

    /*****************************Helper methods for careers page ******************************/

    /**
     *
     * Helper method for clicking in homepage menu on careers tab - > current openings
     * @throws Exception
     */

    public void navigateToCurrentOpening() throws Exception{
        String careersSelector = getProperties("homepageCareersButton", "selector");
        WebElement careersButton = getElement(By.cssSelector(careersSelector));
        Assert.assertThat("Careers button on home page menu is not displayed ",
                careersButton != null && careersButton.isDisplayed(), CoreMatchers.equalTo(true));
        careersButton.click();
        Thread.sleep(1000);
        // Click on dropdown current openings element
        String menuCurrentOpeningsSelector = getProperties("homepageCareersButtonCurrentOpenings", "selector");
        WebElement currentOpeningsButton = getElement(By.cssSelector(menuCurrentOpeningsSelector));
        currentOpeningsButton.click();
        Thread.sleep(1000);
    }
}
