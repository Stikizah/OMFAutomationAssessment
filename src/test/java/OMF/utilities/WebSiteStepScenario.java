package OMF.utilities;

import OMF.utilities.commonMethods.CommonScripts;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class WebSiteStepScenario {

    private CommonScripts commonScripts;


    public void executeScenario(WebDriver driver,String scenario) throws IOException {
        commonScripts=new CommonScripts(driver);
            switch (scenario.toUpperCase()){
                case "LOGIN WITH EMAIL VERIFIED ACCOUNT":
                    commonScripts.loginEmailWebVerifiedUser();
                    break;
                case "LOGIN WITH EMAIL UNVERIFIED ACCOUNT":
                    commonScripts.loginEmailWebUnverifiedUser();
                    break;
                case "LOGIN WITH MOBILE UNVERIFIED ACCOUNT":
                    commonScripts.loginMobileUnverifiedUser();
                    break;
                case "LOGOUT":
                    commonScripts.LogoutUser();
                    break;
                case "LOGIN WITH MOBILE VERIFIED ACCOUNT":
                    commonScripts.loginMobileVerifiedUser();
                    break;
                case "UPDATE - LOGIN WITH MOBILE VERIFIED ACCOUNT":
                    commonScripts.loginMobileEditableUser();
                    break;
                case "UPDATE - LOGIN WITH EMAIL VERIFIED ACCOUNT":
                    commonScripts.loginEmailEditableUser();

                    break;
                case "UPLOAD IMAGE":
                    commonScripts.uploadImage(driver);
                    break;
                case "DRAG AND DROP":
                    commonScripts.DragAndDrop(driver);
                    break;

            }


    }
    public void executeScenario(WebDriver driver,String scenario, String DataToUse) throws IOException {
        commonScripts=new CommonScripts(driver);
        switch (scenario.toUpperCase()){

            case "UPLOAD IMAGE":
                commonScripts.uploadImage(DataToUse);
                break;


        }


    }

    public void checkPageIsReady(JavascriptExecutor javaExecutor) {

        //Initially bellow given if condition will check ready state of page.
        if (javaExecutor.executeScript("return document.readyState").toString().equals("complete")) {
            return;
        }
        //This loop will rotate for 25 times to check If page Is ready after every 1 second.
        //You can replace your value with 25 If you wants to Increase or decrease wait time.
        for (int i = 0; i < 25; i++) {

            //To check page ready state.
            if (javaExecutor.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
