package OMF.utilities.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;

public class MobileDeviceActions {

    public void MobileAndroidScrollDownToElement(AppiumDriver<?> driver, By webelementToUse) {
        TouchAction touchAction = new TouchAction(driver);
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        int i = 0;
        boolean elementDisplayed;
        while (i <= 5) {
            try {
                elementDisplayed = driver.findElement(webelementToUse).isDisplayed();
                if (elementDisplayed) break;
            } catch (Exception e) {
                touchAction.press(PointOption.point(width / 2, height * 3 / 4)).waitAction()
                        .moveTo(PointOption.point(width / 2, height * 1 / 4)).release().perform();
                i++;
            }
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void MobileAndroidSwipeLeft(AppiumDriver<?> driver) {
        Dimension size = driver.manage().window().getSize();   //swipe horizontal initialization
        int anchor = size.height / 2;
        int pointLeft = (int) (size.width * 0.05);
        int pointRight = (int) (size.width * 0.90);

        TouchAction swipeL = new TouchAction(driver)
                .press(PointOption.point(pointRight,anchor))
                .waitAction(waitOptions(ofMillis(800)))
                .moveTo(PointOption.point(pointLeft,anchor))
                .release()
                .perform();
    }

    public void MobileAndroidSwipeRight(AppiumDriver<?> driver) {
        Dimension size = driver.manage().window().getSize();   //swipe horizontal initialization
        int anchor = size.height / 2;
        int pointLeft = (int) (size.width * 0.05);
        int pointRight = (int) (size.width * 0.90);

        TouchAction swipeR = new TouchAction(driver)
                .press(PointOption.point(pointLeft,anchor))
                .waitAction(waitOptions(ofMillis(800)))
                .moveTo(PointOption.point(pointRight,anchor))
                .release()
                .perform();
    }

    public void MobileAndroidSwipeUp(AppiumDriver<?> driver) {
        TouchAction touchAction = new TouchAction(driver);
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        touchAction.press(PointOption.point(width / 2, height * 3 / 4))
                .waitAction()
                .moveTo(PointOption.point(width / 2, height * 1 / 4))
                .release()
                .perform();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void MobileAndroidClickAction(AppiumDriver<?> driver) {
        TouchAction touchAction = new TouchAction(driver);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        int pointLeft =  363;
        int pointRight = 1360;

        touchAction.tap(new PointOption()
                .point(pointLeft, pointRight))
                .perform();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void MobileAndroidScrollDown(AppiumDriver<?> driver, By webelementToUse) {
        Dimension size = driver.manage().window().getSize();   //swipe horizontal initialization
        int anchor = size.width / 2;
        int pointStart = (int) (size.height * 0.80);
        int pointEnd = (int) (size.height * 0.20);

        while (driver.findElements(webelementToUse).size()==0) {
            TouchAction swipeR = new TouchAction(driver)
                    .press(PointOption.point(pointStart, anchor))
                    .waitAction(waitOptions(ofMillis(800)))
                    .moveTo(PointOption.point(pointEnd, anchor))
                    .release()
                    .perform();
        }
//        While (driver.findElements(By.id(“your_id”)).size()==0){
//
//            size = driver.manage().window().getSize();
//            int starty = (int) (size.height * 0.80);
//            int endy = (int) (size.height * 0.20);
//            int startx = size.width / 2;
//            System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);
//
//            driver.swipe(startx, starty, startx, endy, 3000);
//            Thread.sleep(2000);
//            driver.swipe(startx, endy, startx, starty, 3000);
//            Thread.sleep(2000);
//        }

    }
}
