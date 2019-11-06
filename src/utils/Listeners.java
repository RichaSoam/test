package utils;


import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listeners implements ISuiteListener,ITestListener {

	  @Override
	    public void onStart(ISuite suite) {
	           Log.info("********************************************************");
	           Log.info("****Started executing "+suite.getName()+"****************");
	           Log.info("********************************************************");
	           Reporter.log("Started executing test suite: "+suite.getName());	           
	    }
	 
	    @Override
	    public void onFinish(ISuite suite) {
	    	   Log.info("********************************************************");
	           Log.info("****Completed executing "+suite.getName()+"****************");
	           Log.info("********************************************************");
	           Reporter.log("Completed executing test suite: "+suite.getName());	  
	    }
	    
	   
	    @Override
		public void onTestSuccess(ITestResult result) {
			// TODO Auto-generated method stub
	    	printTestResults(result);
			
		}
	    @Override
		public void onTestFailure(ITestResult result) {
	    	printTestResults(result);
			
		}
	    @Override
		public void onTestSkipped(ITestResult result) {
			// TODO Auto-generated method stub
	    	printTestResults(result);
			
		}
	    @Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			// TODO Auto-generated method stub
			
		}
	    @Override
		public void onStart(ITestContext context) {
			// TODO Auto-generated method stub
	    	Log.info("Started executing test " + context.getName());
	    	Reporter.log("Started executing test " + context.getName(), true);
			
		}
	    @Override
		public void onFinish(ITestContext context) {
			// TODO Auto-generated method stub
	    	Log.info("Completed executing test " + context.getName());
	    	Reporter.log("Completed executing test " + context.getName(), true);
			
		}
	    @Override
	    public void onTestStart(ITestResult result) {
			// TODO Auto-generated method stub
	    		
		}
	    private void printTestResults(ITestResult result) {
			Reporter.log("TestName = " + result.getTestName(), true);
			String status = null;
			switch (result.getStatus()) {
			case ITestResult.SUCCESS:
				status = "Pass";
				break;
			case ITestResult.FAILURE:
				status = "Failed";
				break;
			case ITestResult.SKIP:
				status = "Skipped";
			}
			Reporter.log("Test Status: " + status, true);
		}

}


