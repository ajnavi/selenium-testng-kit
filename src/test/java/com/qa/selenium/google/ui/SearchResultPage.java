package com.qa.selenium.google.ui;

import java.util.List;

import com.qa.selenium.annotations.WaitToBeVisible;
import com.qa.selenium.ui.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class SearchResultPage extends AbstractPage {

    @WaitToBeVisible
    @FindBy (how = How.CLASS_NAME, using = "gsfi")
    private WebElement input;
    
    @FindBy (how = How.NAME  , using = "btnK")
    private WebElement searchButton;
    
    @WaitToBeVisible
    @FindBy (how = How.ID, using = "hdtb-msb")
    private WebElement navigation;

    public SearchResultPage () {
        super ();
    }
    
    public List <String> getDifferentNavigation () {
    	Assert.assertTrue(navigation.isDisplayed());
    	List<WebElement> navList = navigation.findElements(By.className(".hdtb-mitem"));
    	List <String> navListString = null;
    	for (WebElement nav: navList) {
    		navListString.add(nav.getText());
    	}
    	return navListString;
    }

}
