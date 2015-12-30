package com.ajnavi.selenium.utils;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


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
        long id = Thread.currentThread().getId();
        System.out.println("BeforeMethod - Thread id is: " + id);
        init(context);
    }

    @AfterMethod
    public void cleanupAfterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("AfterMethod - Thread id is: " + id);
        cleanup();
    }
}
