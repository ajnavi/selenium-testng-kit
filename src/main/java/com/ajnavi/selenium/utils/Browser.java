package com.ajnavi.selenium.utils;

import com.ajnavi.selenium.ui.AbstractPage;
import com.ajnavi.selenium.annotations.WaitForH1;
import com.ajnavi.selenium.annotations.WaitForTitleContains;
import com.ajnavi.selenium.annotations.WaitForTitleIs;
import com.ajnavi.selenium.annotations.WaitToBeVisible;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public final class Browser {
    private static Logger logger = LoggerFactory.getLogger (Browser.class);

    private static int _RefCounter = 0;

    private static RemoteWebDriver remoteWebDriver = null;

    // Default polling wait in milliseconds.
    private final static long DEFAULT_WAIT_SLEEP = WebDriverWait.DEFAULT_SLEEP_TIMEOUT;

    // Default timeout in seconds.
    private final static long DEFAULT_WAIT_TIMEOUT = 2;

    private static WebDriverWait getWebDriverWait () {
        return new WebDriverWait (Browser.getDriver (),
                Config.getInstance ().getLong ("mobile.waitTimeOutInSeconds", DEFAULT_WAIT_TIMEOUT),
                Config.getInstance ().getLong ("mobile.waitSleepInMillis", DEFAULT_WAIT_SLEEP));
    }

    private static void initDriver() {
        Config config = Config.getInstance ();
        remoteWebDriver = new FirefoxDriver ();
    }

    public static void init (Map<String, String> settings) {
        ++_RefCounter;
        if (_RefCounter == 1) {
            // Override properties from TestNG XML file.
            Config.init (settings);
            initDriver ();
        }
    }

    public static void close () {
        if (remoteWebDriver != null) {
            --_RefCounter;
            if (_RefCounter == 0) {
                remoteWebDriver.quit ();
                remoteWebDriver = null;
            }
        }
    }

    public static RemoteWebDriver getDriver () {
        return remoteWebDriver;
    }

    private static void processWaitToBeVisible (Field field, Object page, WebDriverWait webDriverWait) {
        try {
            field.setAccessible (true);
            Object variable = field.get (page);

            if (field.getType () == List.class)
                webDriverWait.until (ExpectedConditions.visibilityOfAllElements ((List<WebElement>) variable));
            else
                webDriverWait.until (ExpectedConditions.visibilityOf ((WebElement) variable));
        } catch (Throwable e) {
            logger.error ("Waiting for element " + field.getName () + " of class " + page.getClass ().getName ());
            e.printStackTrace ();
        }
    }

    private static boolean processWait (Field field) {
        boolean process = false;

        if (field.getAnnotation (WaitToBeVisible.class) != null) {
            if (field.getAnnotation (FindBy.class) == null)
                logger.warn ("Field {} has WaitToBeVisible annotation, but no FindBy, so skipping it.", field.getName ());
            else
                process = true;
        }

        return process;
    }

    private static void processWaitFor (Class <?> pageClass, Object page, WebDriverWait webDriverWait) throws Error {
        Field[] fields = pageClass.getDeclaredFields ();
        for (Field field: fields) {
            if (processWait (field))
                processWaitToBeVisible (field, page, webDriverWait);
        }
    }

    public static void processPageWait (Class<?> pageClass, WebDriverWait webDriverWait) {
        WaitForTitleIs titleIs = (WaitForTitleIs) pageClass.getAnnotation (WaitForTitleIs.class);
        if (titleIs != null)
            webDriverWait.until (ExpectedConditions.titleIs (titleIs.title ()));

        WaitForTitleContains contains = (WaitForTitleContains) pageClass.getAnnotation (WaitForTitleContains.class);
        if (contains != null)
            webDriverWait.until (ExpectedConditions.titleContains (contains.contains ()));

        WaitForH1 h1 = (WaitForH1) pageClass.getAnnotation (WaitForH1.class);
        if (h1 != null)
            webDriverWait.until (ExpectedConditions.textToBePresentInElementLocated (By.tagName ("h1"), h1.h1 ()));
    }

    public static void waitFor (Object page) throws Error {
        WebDriverWait webDriverWait = getWebDriverWait ();
        Class <?> pageClass = page.getClass ();

        processPageWait (pageClass, webDriverWait);

        while (pageClass != AbstractPage.class) {
            processWaitFor (pageClass, page, webDriverWait);
            pageClass = pageClass.getSuperclass ();
        }
    }

    public static void load (String url) {
        remoteWebDriver.get (url);
    }
}
