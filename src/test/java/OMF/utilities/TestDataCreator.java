package OMF.utilities;

import OMF.models.TestStepsModel;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TestDataCreator {
    public static String TestSetPath = null;
    public static String TestSetName = null;
    public static String TestSetID = null;
    public static String TestcaseID = null;
    public static HashMap<String, ArrayList<TestStepsModel>> CreateArrayFromExcel(String excelWorkbookName, String controllerName) throws Exception {
        ExcelDataHandlers excelDataHandlers = new ExcelDataHandlers();
        Sheet controllerSheet = excelDataHandlers.GetExcelSheet(excelWorkbookName, controllerName);
        int rowCount = controllerSheet.getPhysicalNumberOfRows();



        HashMap<String, ArrayList<TestStepsModel>> testHashMap = new LinkedHashMap<>();

        for (int i = 1; i < rowCount; i++) {

            //TestSetPath  = excelDataHandlers.GetCellData("TestSetPath", i, controllerSheet);
            //TestSetName  = excelDataHandlers.GetCellData("TestSetName", i, controllerSheet);
            //TestSetID  = excelDataHandlers.GetCellData("TestSetID", i, controllerSheet);
            TestcaseID = excelDataHandlers.GetCellData("TestcaseID", i, controllerSheet);
            String testFlow = excelDataHandlers.GetCellData("Testcase", i, controllerSheet);
            String execute = excelDataHandlers.GetCellData("Execute", i, controllerSheet);

            if (execute.toUpperCase().equals("TRUE")) {
                ArrayList<TestStepsModel> testStepList = new ArrayList<>();
                Sheet testSheet = excelDataHandlers.GetExcelSheet(excelWorkbookName, testFlow);
                if (testSheet == null) {
                    throw new Exception("Tab not found in workbook - " + testFlow);
                }
                int testStepsCount = testSheet.getPhysicalNumberOfRows();
                for (int j = 1; j < testStepsCount; j++) {
                    TestStepsModel testStepsModel = new TestStepsModel();
                    testStepsModel.Description = excelDataHandlers.GetCellData("Description", j, testSheet);
                    testStepsModel.Action = excelDataHandlers.GetCellData("Action", j, testSheet);
                    testStepsModel.WebElementType = excelDataHandlers.GetCellData("WebElementType", j, testSheet);
                    testStepsModel.WebElementIdentifier = excelDataHandlers.GetCellData("WebElementIdentifier", j, testSheet);
                    testStepsModel.DataToUse = excelDataHandlers.GetCellData("DataToUse", j, testSheet);
                    testStepsModel.Scenario = excelDataHandlers.GetCellData("Scenario", j, testSheet);
                    if (!testStepsModel.Description.equalsIgnoreCase("")) testStepList.add(testStepsModel);
                }
                testHashMap.put(testFlow, testStepList);
            }
        }
        return testHashMap;
    }
}
