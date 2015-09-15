package com.ajnavi.selenium.google.ui;

import com.ajnavi.selenium.annotations.WaitForTitleIs;
import com.ajnavi.selenium.annotations.WaitToBeVisible;
import com.ajnavi.selenium.ui.AbstractPage;
import com.ajnavi.selenium.utils.Browser;
import com.ajnavi.selenium.utils.Config;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import java.lang.String;

@WaitForTitleIs (title = "Google")
public class HomePage extends AbstractPage {

    @WaitToBeVisible
    @FindBy (how = How.CLASS_NAME, using = "gsfi")
    private WebElement input;

    public HomePage () {
        super ();
    }

    public static HomePage loadPage () {
        Browser.load ((String) Config.getInstance ().get ("mobile.homepage.url"));
        return new HomePage ();
    }
}
