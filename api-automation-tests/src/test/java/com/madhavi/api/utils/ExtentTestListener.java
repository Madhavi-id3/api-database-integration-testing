
package com.madhavi.api.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent =
            ExtentReportManager.getReportInstance();

    private static ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        LogUtil.info("Test Suite Execution Started");

        System.out.println("================================");
        System.out.println("Test Suite Execution Started");
        System.out.println("Suite: " + context.getName());
        System.out.println("================================");
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        ExtentTest extentTest =
                extent.createTest(testName);

        test.set(extentTest);

        extentTest.log(Status.INFO,
                "Test Execution Started");

        LogUtil.info("Executing Test: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().log(Status.PASS,
                "Test Passed Successfully");

        LogUtil.info(
                "Test Passed: " +
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Throwable error = result.getThrowable();

        test.get().log(Status.FAIL,
                "Test Failed");

        if (error != null) {

            test.get().fail(error);

            LogUtil.error(
                    "Test Failed: " +
                    result.getMethod().getMethodName());

            LogUtil.error(
                    "Failure Reason: " +
                    error.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().log(Status.SKIP,
                "Test Skipped");

        LogUtil.warn(
                "Test Skipped: " +
                result.getMethod().getMethodName());

        if (result.getThrowable() != null) {

            test.get().skip(result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        LogUtil.info("Test Suite Execution Finished");

        System.out.println("================================");
        System.out.println("Test Suite Execution Completed");
        System.out.println("Passed: " + context.getPassedTests().size());
        System.out.println("Failed: " + context.getFailedTests().size());
        System.out.println("Skipped: " + context.getSkippedTests().size());
        System.out.println("Extent Report Generated Successfully");
        System.out.println("================================");
    }
}
