package OMF.utilities.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class poLogin {

    WebDriver driver;
    @FindBy(id="emailLogin")
    WebElement Emailogin;

    @FindBy(id="formButton")
    WebElement buttonForm;

    @FindBy(id="j_password")
    WebElement password;

    @FindBy(id="mobileNumberLogin")
    WebElement MobileNumber;

    @FindBy(id="Oval")
    WebElement profileElement;
    @FindBy(xpath="//a[contains(@href, '/logout')]")
    WebElement logout;

   public poLogin(WebDriver driver){
        this.driver = driver;
    }
    public void profileElement(){
        profileElement.click();
    }
    public void logout(){
        logout.click();
    }

    public void setUserName(String strUserName){
        Emailogin.sendKeys(strUserName);
    }
    public void setPassword(String passwordText){
        password.sendKeys(passwordText);
    }
    public void setMobileNumber(String passwordText){
        MobileNumber.sendKeys(passwordText);
    }
    public void clickButton(){
        buttonForm.click();
    }

}
