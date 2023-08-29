package com.equipo5.carina.demo.gui.pages.desktop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FeelFlowLoginPage extends AbstractPage {
    @FindBy(xpath = "/html/body/div[1]/div[1]/div[1]/div/div/div/div[2]/div/div[1]/form/div[1]/div[1]/input")
    private ExtendedWebElement emailInput;

    @FindBy(xpath = "//*[@id=\"pass\"]")
    private ExtendedWebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"u_0_5_ld\"]")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = "//*[contans(class, 'media media--hero media--primary media--overlay block-link') and contains(text(), 'More drones coming, Ukraine tells Russia after skyscraper hit')]")
    private ExtendedWebElement postBBC;

    public FeelFlowLoginPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://www.facebook.com/");
        //TODO Auto-generated constructor stub
    }
    
    public void open() {
        this.openURL(this.pageURL);
    }

    public void login(String email, String password) {
        emailInput.type(email);
        passwordInput.type(password);
        loginButton.click();
    }

    public void checkElements() {
        Assert.assertTrue(emailInput.isElementPresent());
        Assert.assertTrue(passwordInput.isElementPresent());
        // Assert.assertTrue(loginButton.isElementPresent());
    }

    public void checkLoginError(String errorMessage) {
        // Implementar la verificación del mensaje de error
        System.out.println("Error message: " + errorMessage);
    }
}
