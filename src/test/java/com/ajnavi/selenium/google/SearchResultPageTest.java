package com.ajnavi.selenium.google;

import com.ajnavi.selenium.utils.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ajnavi.selenium.google.ui.HomePage;
import com.ajnavi.selenium.google.ui.SearchResultPage;

import java.lang.Exception;
import java.util.List;

public class SearchResultPageTest extends BaseTest {

	@Test()
    public void testResultsPage () throws Exception {
         HomePage homePage = HomePage.loadPage ();
         SearchResultPage searchPage = homePage.enterSearchTerm("couponcodes");
         //List <String> nav = searchPage.getDifferentNavigation();
         //Assert.assertTrue(nav.size() == 5);
    }
}
