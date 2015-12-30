package com.ajnavi.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class MySuiteListener implements ISuiteListener {
    private static Logger logger = LoggerFactory.getLogger(MySuiteListener.class);

    @Override
    public void onStart(ISuite suite) {
        logger.debug("onStart {}", suite.getName());
        Browser.init(suite.getXmlSuite().getAllParameters());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.debug("onFinish {}", suite.getName());
        Browser.closeAll();
    }
}
