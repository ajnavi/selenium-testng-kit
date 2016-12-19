package com.qa.selenium.utils;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({com.qa.selenium.utils.MySuiteListener.class, com.qa.selenium.utils.MyTestListener.class})
public abstract class BaseTest {
    private void init(ITestContext context /*This is the way to get access to testng XML file*/) {
        Browser.init(context.getSuite().getXmlSuite().getParameters());
    }

    private void cleanup() {
        Browser.close();
    }
/*
    @BeforeClass
    public void initBeforeClass(ITestContext context) {
        long id = Thread.currentThread().getId();
        System.out.println("BeforeClass - Thread id is: " + id);
        init(context);
    }

    @AfterClass
    public void cleanupAfterClass() {
        long id = Thread.currentThread().getId();
        System.out.println("AfterClass - Thread id is: " + id);
        cleanup();
    }

    @BeforeSuite
    public void initBeforeSuite(ITestContext context) {
        long id = Thread.currentThread().getId();
        System.out.println("BeforeSuite - Thread id is: " + id);
        init(context);
    }

    @AfterSuite
    public void cleanupAfterSuite() {
        long id = Thread.currentThread().getId();
        System.out.println("AfterSuite - Thread id is: " + id);
        cleanup();
    }
*/
    @BeforeMethod
    public void initBeforeMethod(ITestContext context) {
    	String user = System.getProperty("user.name") ;
    	System.setProperty("webdriver.gecko.driver","C:\\Users\\"+user+"\\tools\\geckodriver-v0.11.1-win64\\geckodriver.exe");

        long id = Thread.currentThread().getId();
        System.out.println("BeforeMethod - Thread id is: " + id);
        init(context);
    }

    @AfterMethod
    public void cleanupAfterMethod(ITestResult result) {
        long id = Thread.currentThread().getId();
        System.out.println("AfterMethod - Thread id is: " + id);
        if (! result.isSuccess()) {
    		Browser.captureScreenShot(Browser.getDriver(), result.getName());
		}    
        cleanup();
    }
}
