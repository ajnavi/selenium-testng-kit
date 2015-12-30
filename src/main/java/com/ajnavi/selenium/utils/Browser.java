package com.ajnavi.selenium.utils;

import com.ajnavi.selenium.annotations.WaitForH1;
import com.ajnavi.selenium.annotations.WaitForTitleContains;
import com.ajnavi.selenium.annotations.WaitForTitleIs;
import com.ajnavi.selenium.annotations.WaitToBeVisible;
import com.ajnavi.selenium.ui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public final class Browser {

    private static class WebDriverManager {
        private int _refCounter = 0;
        private RemoteWebDriver _driver = null;

        void init() {
            ++_refCounter;
            if (_refCounter == 1) {
                System.out.println ("Initializing driver for thread " + Thread.currentThread().getId());
                if (Config.getInstance().getBoolean("use.selenium.grid", false)) {
                    // String nodeUrl = "http://10.30.27.73:5555/wd/hub";
                    String nodeUrl = Config.getInstance().get("selenium.grid.url", null);
                    DesiredCapabilities capability = DesiredCapabilities.firefox();
                    capability.setBrowserName("firefox");
                    try {
                        _driver = new RemoteWebDriver(new URL(nodeUrl), capability);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    _driver = new FirefoxDriver();
                }
            }
        }

        boolean close() {
            --_refCounter;
            if (_refCounter == 0) {
                System.out.println ("Quitting driver for thread " + Thread.currentThread().getId());
                _driver.quit();
                _driver = null;
                return true;
            } else {
                return false;
            }
        }

        RemoteWebDriver get() {
            return _driver;
        }
    }

    private static ThreadLocal<WebDriverManager> driverPerThreadNew = new ThreadLocal<WebDriverManager>() {
        @Override protected WebDriverManager initialValue()  {
            return new WebDriverManager();
        }

        @Override public void remove() {
            if (get().close())
                super.remove();
        }
    };

    public static synchronized void initNew(Map<String, String> settings) {
        // Override properties from TestNG XML file.
        Config.init(settings);
        driverPerThreadNew.get().init();
    }

    public static synchronized void closeNew() {
        driverPerThreadNew.remove();
    }

    private static Logger logger = LoggerFactory.getLogger (Browser.class);

    private static List<RemoteWebDriver> _drivers = new ArrayList<>();

    private synchronized static void addDriver(RemoteWebDriver driver) {
        _drivers.add(driver);
    }

    private synchronized static void removeDriver(RemoteWebDriver driver) {
        _drivers.remove(driver);
    }

    private static synchronized void quitAllDrivers() {
        for (RemoteWebDriver driver: _drivers) {
            driver.quit();
        }
        _drivers.clear();
    }

    private static ThreadLocal<RemoteWebDriver> driverPerThread = new ThreadLocal<RemoteWebDriver>() {

        @Override protected RemoteWebDriver initialValue()  {
            System.out.println ("Initializing driver for thread " + Thread.currentThread().getId());
            RemoteWebDriver driver = null;
            if (Config.getInstance().getBoolean("use.selenium.grid", false)) {
                String nodeUrl = Config.getInstance().get("selenium.grid.url", null);
                DesiredCapabilities capability = DesiredCapabilities.firefox();
                capability.setBrowserName("firefox");
                try {
                    driver = new RemoteWebDriver(new URL(nodeUrl), capability);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                driver = new FirefoxDriver();
            }

            addDriver(driver);

            return driver;
        }

        @Override public void remove() {
            System.out.println ("Quitting driver for thread " + Thread.currentThread().getId());
            RemoteWebDriver driver = get();
            removeDriver(driver);
            super.remove();
            driver.quit();
        }
    };

    // Default polling wait in milliseconds.
    private final static long DEFAULT_WAIT_SLEEP = WebDriverWait.DEFAULT_SLEEP_TIMEOUT;

    // Default timeout in seconds.
    private final static long DEFAULT_WAIT_TIMEOUT = 2;

    private static WebDriverWait getWebDriverWait () {
        return new WebDriverWait (Browser.getDriver (),
                Config.getInstance ().getLong ("mobile.waitTimeOutInSeconds", DEFAULT_WAIT_TIMEOUT),
                Config.getInstance ().getLong ("mobile.waitSleepInMillis", DEFAULT_WAIT_SLEEP));
    }

    public static RemoteWebDriver getDriver() {
        return driverPerThread.get();
    }

    public static synchronized void init(Map<String, String> settings) {
        // Override properties from TestNG XML file.
        Config.init(settings);
    }

    public static synchronized void close () {
        if(! Config.getInstance().getBoolean("selenium.driver.per.thread", true))
            driverPerThread.remove();
    }

    public static synchronized void closeAll() {
        quitAllDrivers();
        Config.destroy();
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
    	logger.debug("Loading URL {}", url);
        // remoteWebDriver.get (url);
        Browser.getDriver().get(url);
    }
}
