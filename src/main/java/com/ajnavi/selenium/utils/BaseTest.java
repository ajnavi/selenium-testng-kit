package com.ajnavi.selenium.utils;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    private void init (ITestContext context /*This is the way to get access to testng XML file*/) {
        Browser.init (context.getSuite ().getXmlSuite ().getParameters ());
    }

    private void cleanup () {
        Browser.close ();
    }

    @BeforeClass
    public void initBeforeClass (ITestContext context) {
        init (context);
    }

    @AfterClass
    public void cleanupAfterClass () {
        cleanup ();
    }

    @BeforeSuite
    public void initBeforeSuite (ITestContext context) {
        init (context);
    }

    @AfterSuite
    public void cleanupAfterSuite () {
        cleanup ();
    }
}
