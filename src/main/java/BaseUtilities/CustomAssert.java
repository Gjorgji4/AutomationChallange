package BaseUtilities;

import Drivers.ChromeDriverOption;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class CustomAssert extends Assert {
   public  CustomAssert(){}

   private static  ChromeDriverOption driver = new ChromeDriverOption();

    /**
     * Overriden asserter with added screenshot
     * @param reason
     * @param actual
     * @param matcher
     * @param screenShoot If is true will save the screnshoot into test-output/html, as jpg file
     * @param <T>
     */
    public   <T> void assertThat(String reason, T actual, Matcher<? super T> matcher, boolean screenShoot) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            if(screenShoot){
                System.out.println("Assert Failed...");
                File scrFile = ((TakesScreenshot) driver.getWebDriver())
                        .getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(scrFile, new
                            File("test-output/html/" + reason + ".jpg"));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            throw new AssertionError(description.toString());
        }
    }
}