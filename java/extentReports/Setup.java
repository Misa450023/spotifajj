package extentReports;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Setup implements ITestListener {

	public static ExtentReports extentReports;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@BeforeTest
	public void onStart(ITestContext context) {

		String fileName = ExtentReportClass.getReportNameWithTimeStamp();
		String fullReportPath = System.getProperty("user.dir") + "\\myReports\\" + fileName;
		extentReports = ExtentReportClass.createInstance(fullReportPath, "Test API Automation", "Test report");
	}

	@AfterTest
	public void onFinish(ITestContext context) {

		if (extentReports != null)
			extentReports.flush();
	}

	@BeforeMethod
	public void onTestStart(ITestResult result) {

		ExtentTest test = extentReports.createTest(
				"Test Name " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		extentTest.set(test);
	}

	@AfterMethod
	public void onTestFailure(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				ExtentReportClass.logFailureDetails("assertions not passed. 😒");
				ExtentReportClass.logFailureDetails(result.getThrowable().getMessage());
				String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
				stackTrace = stackTrace.replaceAll(",", "<br>");
				String formmatedTrace = "<details>\n" + "    <summary>Click Here To See Exception Logs</summary>\n"
						+ "    " + stackTrace + "\n" + "</details>\n";
				ExtentReportClass.logExceptionDetails(formmatedTrace);
				ExtentReportClass.logInfoDetails(result.getMethod().toString() + " IS FAILED");
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	};

	@AfterMethod
	public void onTestSuccses(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {

			try {

				ExtentReportClass.logPassDetails("assertions are passed. 😊");
				ExtentReportClass.logInfoDetails(result.getMethod().toString() + "IS PASSED");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
