package com.qa.selenium.ui;

import com.qa.selenium.utils.Browser;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class AbstractPage extends LoadableComponent <AbstractPage> {
    public AbstractPage() {
        PageFactory.initElements (Browser.getDriver (), this);
        this.get ();
        // Process various Wait* annotations at the page, and at WebElement levels
        Browser.waitFor(this);
    }

    @Override
    protected final void isLoaded () throws Error {

    }

    @Override
    protected final void load () {

    }
}
