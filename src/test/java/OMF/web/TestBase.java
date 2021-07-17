package OMF.web;

import atu.alm.wrapper.ITestCaseRun;
import OMF.models.TestStepsModel;
import OMF.utilities.ALM_Wrapper;
import OMF.utilities.TestDataCreator;
import OMF.utilities.datahelpers.XtraSavingsCardHandlers;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class TestBase {

    public HashMap<String, ArrayList<TestStepsModel>> testCaseArray;
    public WebDriver driver;
    public static Robot robot;
    public static String baseUrl;
    public WebFlowExecution webFlowExecution;
    public static XtraSavingsCardHandlers cardHandler;
    public static Workbook cardWorkbook;
    public static Properties prop;
    public static String sStoredValue;
    public static ITestCaseRun myrun;
    public static boolean isALMExecuted;
    @BeforeSuite
    public void ReadInCardSheet() throws Exception {
        cardHandler = new XtraSavingsCardHandlers();
        cardWorkbook = cardHandler.GetCardWorkBook();
    }

    @Parameters({"excelData", "appURL", "browserType", "connectedDevice", "mobilePlatform", "platformVersion", "deviceName", "udid"})
    @BeforeClass
    public void initializeTestBaseSetup(String excelData, String appURL, @Optional String browserType, @Optional String connectedDevice,
                                        @Optional String mobilePlatform, @Optional String platformVersion,
                                        @Optional String deviceName, @Optional String udid) {
        try {
            testCaseArray = new TestDataCreator().CreateArrayFromExcel(excelData, "WebFlowsController");
            webFlowExecution = new WebFlowExecution();
            baseUrl = appURL;
            DriverCreator driverCreator = new DriverCreator();
            this.driver = driverCreator.setDriver(browserType, appURL);
            this.robot = new Robot();
        } catch (Exception e) {
            System.out.println("Error....." + Arrays.toString(e.getStackTrace()));
        }

        try {
            prop = XtraSavingsCardHandlers.readPropertiesFile("src/test/resources/ALM.properties");
            if (prop.getProperty("alm_update").toUpperCase().contains("TRUE")) {
                ALM_Wrapper.initialiseALM(prop.getProperty("alm_URL"), prop.getProperty("alm_UserName"), prop.getProperty("alm_Password"),
                        prop.getProperty("alm.domain"), prop.getProperty("alm.project"));
            }
        } catch(Exception e){
            System.out.println("Unable to initialize ALM : " + e.getMessage());
        }


    }
    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterSuite
    public void WriteAndCloseCardSheet() throws Exception {
        //     cardHandler.WriteUpdateUsedCards(cardWorkbook);
    }
}