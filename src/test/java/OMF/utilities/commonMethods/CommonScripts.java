package OMF.utilities.commonMethods;

import OMF.utilities.pageModels.poLogin;
import OMF.web.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class CommonScripts extends TestBase {
    poLogin  PageOBJ;


   public CommonScripts(WebDriver driver){
       PageOBJ= PageFactory.initElements(driver,poLogin.class);

    }

    public void loginEmailWebVerifiedUser() {
        try {
            PageOBJ.setUserName("gftlucky@gmail.com");
            PageOBJ.clickButton();
            PageOBJ.setPassword("Nkomo1996@");
            PageOBJ.clickButton();
        } catch (Exception e) {
            System.out.println("Error - loginEmailWebVerifiedUser:\n" + e.getMessage());

        }
    }

    public void loginEmailWebUnverifiedUser() {
       /*Account details
       * No card
       * Mobile not verified
       * Email not verified
       * */
        try {
            PageOBJ.setUserName("shoprite.device@gmail.com");
            PageOBJ.clickButton();
            PageOBJ.setPassword("P@ssword01");
            PageOBJ.clickButton();
        } catch (Exception e) {
            System.out.println("Error - loginEmailWebUnverifiedUser:\n" + e.getMessage());

        }

    }

    public void loginMobileWebUnverifiedUser() {
        /*Account details
         * No card
         * Mobile not verified
         * Email not verified
         * */
        try {
            PageOBJ.setMobileNumber("0777555550");
            PageOBJ.clickButton();
            PageOBJ.setPassword("P@ssword01");
            PageOBJ.clickButton();
        } catch (Exception e) {
            System.out.println("Error - loginMobileWebUnverifiedUser:\n" + e.getMessage());
        }
    }
    public void loginMobileUnverifiedUser() {
        try {
            PageOBJ.setMobileNumber("834143180");
            PageOBJ.clickButton();
            PageOBJ.setPassword("P@ssword01");
            PageOBJ.clickButton();
        } catch (Exception e) {
            System.out.println("Error - loginMobileUnverifiedUser:\n" + e.getMessage());

        }
    }
    public void loginMobileVerifiedUser() {
        try {
            PageOBJ.setMobileNumber("829090909");
            PageOBJ.clickButton();
            PageOBJ.setPassword("P@ssword01");
            PageOBJ.clickButton();
        } catch (Exception e) {
        }
    }
    public void loginMobileEditableUser() {
        try {
            PageOBJ.setMobileNumber("827800003");
            PageOBJ.clickButton();
            PageOBJ.setPassword("P@ssword01");
            PageOBJ.clickButton();
        } catch (Exception e) {
            System.out.println("Error - loginMobileEditableUser:\n" + e.getMessage());

        }
    }
    public void loginEmailEditableUser() {
        try {
            PageOBJ.setUserName("uu_auto_shr@outlook.com");
            PageOBJ.clickButton();
            PageOBJ.setPassword("P@ssword01");
            PageOBJ.clickButton();
        } catch (Exception e) {
            System.out.println("Error - loginEmailEditableUser:\n" + e.getMessage());


        }
    }
    public void LogoutUser() {
        try {
            PageOBJ.profileElement();
            PageOBJ.logout();
        } catch (Exception e) {
            System.out.println("Error - LogoutUser:\n" + e.getMessage());

        }


    }


    public void uploadImage(String DataToUse) {
        try {
            String OSName = System.getProperty("os.name") ;
            Thread.sleep(5000);


            if (OSName.contains("Mac OS X")) {

                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.delay(2000);
                robot.keyPress(KeyEvent.VK_META);
                robot.delay(2000);
                robot.keyPress(KeyEvent.VK_G);
                robot.delay(2000);
                robot.keyRelease(KeyEvent.VK_G);
                robot.delay(2000);
                robot.keyRelease(KeyEvent.VK_META);
                robot.delay(2000);
                robot.keyRelease(KeyEvent.VK_SHIFT);

                robot.delay(2000);
            }
            String fileLocation = null;
            if (OSName.contains("Mac OS X")) {
                fileLocation = System.getProperty("user.dir") + "/src/test/resources/Images/"+DataToUse;
            } else {
                fileLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\Images\\"+DataToUse;
            }

            uploadFile(fileLocation,OSName,robot);
        } catch (Exception e) {
            System.out.println("Error - LogoutUser:\n" + e.getMessage());

        }

    }

    private static void setClipboardData(String string) {
        //StringSelection is a class that can be used for copy and paste operations.
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public static void uploadFile(String fileLocation, String OSName, Robot r) {
        try {
            //Setting clipboard with file location
            setClipboardData(fileLocation);
            //native key strokes for CTRL, V and ENTER keys
            Thread.sleep(3000);
            //KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
            if (OSName.contains("Mac OS X")) {
                r.keyPress(KeyEvent.VK_META);
                r.delay(2000);
                r.keyPress(KeyEvent.VK_V);
                r.delay(2000);
                r.keyRelease(KeyEvent.VK_V);
                r.delay(2000);
                r.keyRelease(KeyEvent.VK_META);
                r.delay(2000);
                PressEnter( 2);
            } else {
                try {
                    Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\autoit\\fileUpload.exe");
                } catch (Exception e) {
                    System.out.println("Error - Upload Image:\n" + e.getMessage());
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //Press Shift and Tab
    private static void PressEnter(int iteration) throws InterruptedException, AWTException
    {
        int i=1;
        while(i<=iteration)
        {
            Thread.sleep(1000);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(2000);
            robot.keyRelease(KeyEvent.VK_ENTER);
            i++;
        }
    }

    public void uploadImage(WebDriver driver) {
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\autoit\\fileUpload.exe");
        } catch (Exception e) {
            System.out.println("Error - Upload Image:\n" + e.getMessage());
        }
    }

    public void DragAndDrop(WebDriver driver){

        String sourcePath = "(.//*[text()[contains(.,'Catalog version')]])[2]/following::span[1]";
        String targetPath = "(.//*[text()[contains(.,'Drop Items here to create a Workflow')]])";

        WebElement source = driver.findElement(By.xpath(sourcePath));
        WebElement target = driver.findElement(By.xpath(targetPath));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(source,target).perform();

    }

}
