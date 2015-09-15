package com.ajnavi.selenium.google;

import com.ajnavi.selenium.utils.BaseTest;
import org.testng.annotations.Test;
import com.ajnavi.selenium.google.ui.HomePage;

import java.lang.Exception;

public class HomePageTest extends BaseTest {
    @Test
    public void testHomePage () throws Exception {
        HomePage homePage = HomePage.loadPage ();
    }
}
