package com.ajnavi.selenium.ui;

import com.ajnavi.selenium.utils.Browser;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class AbstractPage extends LoadableComponent <AbstractPage> {
    public AbstractPage() {
        PageFactory.initElements (Browser.getDriver (), this);
        this.get ();
    }

    @Override
    protected final void isLoaded () throws Error {

    }

    @Override
    protected final void load () {

    }
}
