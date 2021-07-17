package OMF.web;

import OMF.models.TestStepsModel;
import OMF.models.XtraSavingsCardModel;
import OMF.utilities.*;
import OMF.utilities.datahelpers.XtraSavingsCardHandlers;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;


public class WebFlowExecution {

    private WebSiteStepScenario webSiteStepScenario;
    private SeleniumHelpers seleniumHelpers;
    private DataGenerators dataGenerators;
    private XtraSavingsCardModel newCard;
    private XtraSavingsCardHandlers cardHandler;
    private Workbook cardWorkbook;
    String lastGeneratedData = "";
    public String DataHandle;

    public WebFlowExecution() {
        seleniumHelpers = new SeleniumHelpers();
        seleniumHelpers.SetupScreenshotsFolder();
        dataGenerators = new DataGenerators();
        webSiteStepScenario = new WebSiteStepScenario();


    }

    public void WebStepExecutor(WebDriver driver, ExtentTest extentTestCase, TestStepsModel testData, SoftAssert assertion, String platformName, String testFlow) throws IOException {
        String description = testData.Description;
        String action = testData.Action;
        String webElementType = testData.WebElementType;
        String webElementIdentifier = testData.WebElementIdentifier;
        String dataToUse = testData.DataToUse;
        String scenario = testData.Scenario;
        JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
        By webelementToUse;
        ExtentTest stepNode = extentTestCase.createNode(description); // level = 2
        Duration interval = Duration.ofSeconds(1);
        Duration timeout = Duration.ofSeconds(10);
        Wait<WebDriver> verificationWait = new FluentWait<>(driver).withTimeout(timeout)
                .ignoring(NoSuchElementException.class).pollingEvery(interval);
        webSiteStepScenario.checkPageIsReady(javaExecutor);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            switch (action.toUpperCase()) {
                case "NAVIGATE TO PAGE":
                    Boolean validUrl = seleniumHelpers.ParseUrl(dataToUse);
                    if (validUrl) {
                        driver.navigate().to(dataToUse);
                    } else {
                        String combinedUrl;
                        if (dataToUse != null) {
                            combinedUrl = TestBase.baseUrl + dataToUse.trim().toLowerCase();
                        } else {
                            combinedUrl = TestBase.baseUrl;
                        }
                        validUrl = seleniumHelpers.ParseUrl(combinedUrl);
                        if (validUrl) {
                            driver.navigate().to(combinedUrl);
                        } else {
                            assertion.fail("Invalid url used: " + combinedUrl);
                        }
                    }
                    break;

                case "CLICK ELEMENT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);

                    try {
                        driver.findElement(webelementToUse).click();
                    } catch (ElementClickInterceptedException e) {
                        javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                        driver.findElement(webelementToUse).click();
                    }
                    break;

                case "CLICK ELEMENT USING JSCRIPT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);

                    try {
                        WebElement element = driver.findElement(webelementToUse);
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].click();", element);
                    } catch (ElementClickInterceptedException e) {
                        javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                        driver.findElement(webelementToUse).click();
                    }
                    break;

                case "INPUT TEXT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    try {
                        driver.findElement(webelementToUse);
                    } catch (NoSuchElementException e) {
                        javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                    }
                    driver.findElement(webelementToUse).clear();
                    if (dataToUse.toUpperCase().contains("GENERATED")) {
                        String dataRequired = dataGenerators.GenerateRequiredData(dataToUse);
                        driver.findElement(webelementToUse).sendKeys(dataRequired);
                        lastGeneratedData = dataRequired;
                    } else if (dataToUse.toUpperCase().contains("NUMBER")) {
                        String dataRequired = dataGenerators.generateRandomValue(10);
                        driver.findElement(webelementToUse).sendKeys(dataRequired);
                        DataHandle = dataRequired;
                    } else if (dataToUse.toUpperCase().contains("VALUE")) {
                        String dataRequired = dataGenerators.generateRandomString(10);
                        driver.findElement(webelementToUse).sendKeys(dataRequired);
                        DataHandle = dataRequired;
                    } else if (dataToUse.toUpperCase().contains("SEARCHBONUSBUY")) {
                        driver.findElement(webelementToUse).sendKeys(DataHandle);
                    } else if (dataToUse.toUpperCase().contains("BONUSBUY")) {
                        String dataRequired = dataGenerators.generateRandomValue(10);
                        DataHandle = "testBonusBuy" + dataRequired;
                        driver.findElement(webelementToUse).sendKeys(DataHandle);
                    }else if(dataToUse.toUpperCase().contains("PRIORSTORED"))  {
                        driver.findElement(webelementToUse).sendKeys(TestBase.sStoredValue);
                    }
                    else {
                        if (dataToUse.toUpperCase().contains("LAST GEN DATA")) {
                            driver.findElement(webelementToUse).sendKeys(lastGeneratedData);
                        } else if(dataToUse.toUpperCase().contains("LASTDATA")) {
                            driver.findElement(webelementToUse).sendKeys(DataHandle);

                        }else{
                            driver.findElement(webelementToUse).sendKeys(dataToUse);
                        }
                    }
                    break;

                case "MOUSEOVER ELEMENT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    try {
                        driver.findElement(webelementToUse);
                    } catch (NoSuchElementException e) {
                        javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                    }
                    Actions builder = new Actions(driver);
                    WebElement element = driver.findElement(webelementToUse);
                    builder.moveToElement(element).build().perform();
                    break;

                case "VERIFY ELEMENT PRESENT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    verificationWait.until(ExpectedConditions.visibilityOfElementLocated(webelementToUse)).isDisplayed();
                    break;

                case "VERIFY ELEMENT NOT PRESENT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    assertion.assertEquals(driver.findElements(webelementToUse).size(), 0, "Element found on page: " + webelementToUse.toString());
                    break;
                case "VERIFY ELEMENT CLICKABLE":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    verificationWait.until(ExpectedConditions.elementToBeClickable(webelementToUse));
                    break;
                case "VERIFY ELEMENT UN-CLICKABLE":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    verificationWait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(webelementToUse)));
                    break;
                case "VERIFY ELEMENT TEXT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    verificationWait.until(ExpectedConditions.textToBePresentInElementLocated(webelementToUse, dataToUse));
                    break;
                case "VERIFY ELEMENT VALUE":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    verificationWait.until(ExpectedConditions.textToBePresentInElementValue(webelementToUse, dataToUse));
                    break;
                case "ACCEPT ALERT":
                    verificationWait.until(ExpectedConditions.alertIsPresent());
                    driver.switchTo().alert().accept();
                    break;
                case "DISMISS ALERT":
                    verificationWait.until(ExpectedConditions.alertIsPresent());
                    driver.switchTo().alert().dismiss();
                    break;
                case "VERIFY PAGE TITLE":
                    assertion.assertTrue(driver.getTitle().contains(dataToUse));
                    break;
                case "WAIT":
                    int seconds = Integer.parseInt(dataToUse);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
                    break;

                case "INPUT NEW CARD NUMBER":
                    cardHandler = TestBase.cardHandler;
                    cardWorkbook = TestBase.cardWorkbook;
                    ArrayList<XtraSavingsCardModel> cardlist = cardHandler.CreateArrayFromCardList(cardWorkbook, platformName);
                    newCard = cardHandler.GetNewCard(cardlist);
                    Iterable<String> cardParts = Splitter.fixedLength(4).split(newCard.CardNumber);
                    String[] parts = Iterables.toArray(cardParts, String.class);
                    for (int i = 1; i <= 4; i++) {
                        driver.findElement(By.id("xtraSavingCard_" + i)).sendKeys(parts[i - 1]);
                    }
                    break;
                case "UPDATE USED CARD":
                    String dataRequired = null;
                    if (dataToUse.toUpperCase().contains("GENERATED")) {
                        dataRequired = dataGenerators.GenerateRequiredData(dataToUse);
                    }
                    cardWorkbook = cardHandler.UpdateWorkbookCardUsed(cardWorkbook, newCard.RowNumber, newCard.CardNumber, "True", dataRequired, platformName);
                    break;

                case "REFRESH PAGE":
                    driver.navigate().refresh();
                    break;

                case "TAB SWITCH":
                    ArrayList<String> mytab = new ArrayList<>(driver.getWindowHandles());
                    driver.switchTo().window(mytab.get(1));
                    break;
                case "EXECUTE SCENARIO":
                    if (dataToUse.isEmpty() || (dataToUse == null))
                    {
                        webSiteStepScenario.executeScenario(driver,scenario);}else{
                        webSiteStepScenario.executeScenario(driver,scenario, dataToUse);
                    }
                    break;
                case "DRAG AND DROP":
                    WebElement webelementFrom = (WebElement) seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    WebElement webelementTo = (WebElement) seleniumHelpers.DetermineWebElementType(webElementType, dataToUse);
                    try {
                        Actions act=new Actions(driver);
                        //Dragged and dropped.
                        act.dragAndDrop(webelementFrom, webelementTo).build().perform();
                    } catch (ElementClickInterceptedException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "VERIFY ELEMENT TEXT IS SET TO":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    String currentValue = driver.findElement(webelementToUse).getText();
                    if (dataToUse.toUpperCase().contains("GENERATED")) {
                        dataRequired = dataGenerators.GenerateRequiredData(dataToUse);
                        verificationWait.until(ExpectedConditions.textToBe(webelementToUse, dataRequired));
                        assertTrue(currentValue.contains(dataRequired));

                    } else {
                        verificationWait.until(ExpectedConditions.textToBe(webelementToUse, dataToUse));
                        assertTrue(currentValue.contains(dataToUse));
                    }
                    break;
                case "CLICK ELEMENT IF EXISTS":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);

                    Boolean isPresent = driver.findElements(webelementToUse).size()>0;
                    if (isPresent) {
                        driver.findElement(webelementToUse).click();
                    }
                    break;

                case "GET TEXT FROM ELEMENT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    try
                    {
                        TestBase.sStoredValue = driver.findElement(webelementToUse).getText();
                    } catch (NoSuchElementException e) {
                        javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                    }
                    break;

                case "GET VALUE FROM ELEMENT":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    try
                    {
                        TestBase.sStoredValue = driver.findElement(webelementToUse).getAttribute("value");
                    } catch (NoSuchElementException e) {
                        javaExecutor.executeAsyncScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                    }
                    break;

                case "COMPARE TEXT OF ELEMENT WITH":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    try
                    {
                        String currentTextValue = driver.findElement(webelementToUse).getText();
                        if(dataToUse.toUpperCase().contains("PRIORSTORED"))
                        {
                            assertNotEquals(currentTextValue,TestBase.sStoredValue,"Stored value and current value match.");
                            TestBase.sStoredValue = currentTextValue;
                        }

                    } catch (NoSuchElementException e) {
                        javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(webelementToUse));
                    }
                    break;

                case "VERIFY ELEMENT VALUE EQUALS":
                    webelementToUse = seleniumHelpers.DetermineWebElementType(webElementType, webElementIdentifier);
                    String elementValue = driver.findElement(webelementToUse).getAttribute("value");
                    verificationWait.until(ExpectedConditions.attributeToBe(webelementToUse, "value",dataToUse));
                    assertTrue(elementValue.contains(dataToUse));
            }
            Thread.sleep(2000);
            //TODO - replace with wait for angular ready
            String screenshotPath = seleniumHelpers.logWebScreenShotToFile(driver);
            assert screenshotPath != null;
            stepNode.pass(description, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            if (TestBase.prop.getProperty("alm_update").toUpperCase().contains("TRUE"))
            {
                ALM_Wrapper.addTestStep(TestBase.myrun, description,  description, description ,description +" Successful", "PASSED");

            }
            seleniumHelpers.LogScreenshotToRP(description, "INFO", screenshotPath);

        } catch (Exception e) {
            assertion.fail(e.getMessage());
            String screenshotPath = seleniumHelpers.logWebScreenShotToFile(driver);
            assert screenshotPath != null;
            stepNode.fail(description + " - " + e, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            if (TestBase.prop.getProperty("alm_update").toUpperCase().contains("TRUE"))
            {
                if(!TestBase.isALMExecuted) {

                    try {
                        TestBase.myrun = ALM_Wrapper.updateTestResult(TestDataCreator.TestSetPath, TestDataCreator.TestSetName, Integer.parseInt(TestDataCreator.TestSetID), testFlow, "FAILED");
                    } catch (Exception s) {
                        System.out.println("Update Test Result Error : " + s.getMessage());
                    }
                    TestBase.isALMExecuted = true;
                }
                ALM_Wrapper.addTestStep(TestBase.myrun, description, description, description, e.toString(), "FAILED");

            }

            seleniumHelpers.LogScreenshotToRP(description + " - " + e, "ERROR", screenshotPath);
        }
    }

}
