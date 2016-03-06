package com.qa.selenium.google;

import com.qa.selenium.utils.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.selenium.google.ui.HomePage;
import com.qa.selenium.google.ui.SearchResultPage;

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
