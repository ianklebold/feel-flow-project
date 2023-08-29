package com.equipo5.carina.demo.gui.pages.common;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.equipo5.carina.demo.gui.components.compare.ModelSpecs;
import com.zebrunner.carina.webdriver.gui.AbstractPage;

public abstract class CompareModelsPageBase extends AbstractPage {

    public CompareModelsPageBase(WebDriver driver) {
        super(driver);
        this.setPageURL("/compare.php3");
    }

    public abstract List<ModelSpecs> compareModels(String... models);

}
