package OMF.utilities;

import atu.alm.wrapper.ALMServiceWrapper;
import atu.alm.wrapper.ITestCase;
import atu.alm.wrapper.ITestCaseRun;
import atu.alm.wrapper.enums.StatusAs;
import atu.alm.wrapper.exceptions.ALMServiceException;
import com.jacob.com.LibraryLoader;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ALM_Wrapper {
	
	public static ALMServiceWrapper wrapper;
	public static ITestCaseRun myRun;
	public static ITestCase mytest;
	static String DefaultPath = System.getProperty("user.dir");
	public static String testsetpath, testsetname,testname;
	public static int tsid;
	
	public static void initialiseALM(String sTestMURL,String sTestMUsername,String sTestMPassword,String sDomain,String sProject) {
		try {

		System.setProperty("jacob.dll.path", DefaultPath+"\\jacob\\jacob-1.19\\jacob-1.19-x86.dll");
		LibraryLoader.loadJacobLibrary();

		wrapper = new ALMServiceWrapper(sTestMURL);
		wrapper.connect(sTestMUsername, sTestMPassword, sDomain, sProject);

	}catch(Exception e)
	{
		System.out.println("Initialise ALM Connection Error : "+e.getMessage());
	}
}
	
	public static ITestCaseRun updateTestResult(String sTestSetPath, String sTestSetName, int sTSID, String sTestName, String sStatus) throws ALMServiceException {
		try {
			testsetpath = sTestSetPath;
			testsetname = sTestSetName;
			tsid = sTSID;
			testname = sTestName;
			
			if (sStatus.toUpperCase().equals("PASSED")){
						
				mytest = wrapper.updateResult(sTestSetPath,sTestSetName, sTSID, sTestName, StatusAs.PASSED);
		        myRun = wrapper.createNewRun(mytest, "Run"+getCurrentTimeStamp(), StatusAs.PASSED);
			
			}else {
				mytest = wrapper.updateResult(sTestSetPath,sTestSetName, sTSID, sTestName, StatusAs.FAILED);
		        myRun = wrapper.createNewRun(mytest, "Run"+getCurrentTimeStamp(), StatusAs.FAILED);
		     //   mytest = wrapper.
			}
			
		}catch(Exception e)
        {
            System.out.println("Update Test Result Error : "+e.getMessage());
        }
		
		return myRun;
	}
	
	public static void updateStatus() throws ALMServiceException {
		
		//wrapper.updateResult(testsetpath,testsetname, tsid, testname, StatusAs.FAILED);
		//wrapper.createNewRun(mytest, "Run"+getCurrentTimeStamp(),StatusAs.FAILED); 
	}
	
	public static void addTestStep(ITestCaseRun myRun, String sTestName, String sDescription, String sExpected , String sActual, String sStatus) {
		try {			
			if (sStatus.toUpperCase().equals("PASSED")){				
				wrapper.addStep(myRun, sTestName, StatusAs.PASSED, sDescription, sExpected, sActual);
			}else if (sStatus.toUpperCase().equals("BLOCKED")){
				wrapper.addStep(myRun, sTestName, StatusAs.BLOCKED, sDescription, sExpected, sActual);
			}else {
				wrapper.addStep(myRun, sTestName, StatusAs.FAILED, sDescription, sExpected, sActual);
			}
			
		}catch(Exception e)
        {
            System.out.println("Add Test Step Error : "+e.getMessage());
        }
	}
	
	public static void closeALM() {
		try {
			
			wrapper.close();
		}catch(Exception e)
        {
            System.out.println("Close Wrapper Error : "+e.getMessage());
        }
	}
	
	 public static String getCurrentTimeStamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
		return sdf.format(timestamp);
	 }


}
