package com.ajnavi.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener {
    private static Logger logger = LoggerFactory.getLogger(MyTestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.debug("onTestStart {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.debug("onTestSuccess {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.debug("onTestFailure {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.debug("onTestSkipped {}", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.debug("onTestFailedButWithinSuccessPercentage {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.debug("onStart {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.debug("onFinish {}", context.getName());
    }
}
