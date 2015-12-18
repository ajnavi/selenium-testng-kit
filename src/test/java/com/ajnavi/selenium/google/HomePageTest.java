package com.ajnavi.selenium.google;

import com.ajnavi.selenium.google.ui.HomePage;
import com.ajnavi.selenium.google.ui.SearchResultPage;
import com.ajnavi.selenium.utils.BaseTest;
import com.ajnavi.selenium.utils.Browser;
import com.ajnavi.selenium.utils.Config;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
    /*
    @Test()
    public void testHomePageGmailLink() throws Exception {
        HomePage homePage = HomePage.loadPage();
        Assert.assertEquals(homePage.getGmailUrl(), "https://mail.google.com/mail/?tab=wm", "Failed to test link for gmail link"
                + "Expected was : https://mail.google.com/mail/?tab=wm, but actual is : " + homePage.getGmailUrl());
    }

    @Test()
    public void testHomePageGmailText() throws Exception {
        HomePage homePage = HomePage.loadPage();
        Assert.assertEquals(homePage.getGmailText(), "Gmail", "Failed to test text for gmail link"
                + "Expected was : Gmail, but actual is : " + homePage.getGmailText());
    }

    @Test()
    public void testResultsPage() throws Exception {
        HomePage homePage = HomePage.loadPage();
        SearchResultPage searchPage = homePage.enterSearchTerm("couponcodes");
    }
    */

    @Test
    public void testYahoo() {
       // Browser.load(((String) Config.getInstance().get ("google.url")));
        Browser.load("http://www.yahoo.com");
       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("Yahoo Thread is " + Thread.currentThread().getId());
        Browser.getDriver().getCurrentUrl().contains("yahoo");
        Assert.assertNotNull(Browser.getDriver().findElement(By.id("nav-mail")));
        Assert.assertNotNull(Browser.getDriver().findElement(By.id("nav-news")));
    }

    @Test
    public void testGoogle() {
       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
       //Browser.load((String) Config.getInstance().get("yahoo.url"));
        Browser.load("http://www.google.com");
        System.out.println("Google Thread is " + Thread.currentThread().getId());
        Browser.getDriver().getCurrentUrl().contains("google");
    }
}
