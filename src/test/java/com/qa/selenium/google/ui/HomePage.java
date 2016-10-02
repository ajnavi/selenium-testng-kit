package com.qa.selenium.google.ui;

import com.qa.selenium.annotations.WaitForTitleIs;
import com.qa.selenium.annotations.WaitToBeVisible;
import com.qa.selenium.ui.AbstractPage;
import com.qa.selenium.utils.Browser;
import com.qa.selenium.utils.Config;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.lang.String;

@WaitForTitleIs (title = "Google")
public class HomePage extends AbstractPage {

    @WaitToBeVisible
    @FindBy (how = How.CLASS_NAME, using = "gsfi")
    private WebElement input;
    
    @WaitToBeVisible
    @FindBy (how = How.NAME  , using = "btnK")
    private WebElement searchButton;
    
    @WaitToBeVisible
    @FindBy (how = How.CLASS_NAME, using = "gb_P")
    private WebElement gmailButton;

    @FindBy (how = How.ID, using = "pnnext")
    private WebElement nextButton;
    
    public HomePage () {
        super ();
    }

    public static HomePage loadPage () {
        Browser.load ((String) Config.getInstance ().get ("mobile.homepage.url"));
        return new HomePage ();
    }
    
    /*
     * On Google page there is a link for gmail text. 
     */
    public String getGmailText () {
    	return gmailButton.getText();
    }
    
    public String getGmailUrl () {
    	return gmailButton.getAttribute("href");
    }

    public SearchResultPage enterSearchTerm (String text) {
    	input.sendKeys(text);
    	input.sendKeys(Keys.RETURN);
    	Browser.waitForElementToBeVisible(Browser.getDriver(), nextButton);
    	return new SearchResultPage ();
    } 
    

}
