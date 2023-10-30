package com.equipo5.carina.demo.gui.pages.desktop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;

public class FeelFlowLoginPage extends AbstractPage {
    
    public FeelFlowLoginPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("http://127.0.0.1:5500/feel-flow-frontend/pages/sign_in.html");
    }
    
    @FindBy(xpath = "//*[@id=\"login-form\"]/div[3]/button")
    private ExtendedWebElement signInButton;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private ExtendedWebElement emailInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private ExtendedWebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"login-form\"]/div[3]/button")
    private ExtendedWebElement submitLogginButton;

    @FindBy(xpath = "//*[@id=\"message-container\"]")
    private ExtendedWebElement errorMessage;


    public void open() {
        this.openURL(this.pageURL);
    }

    
    public void clickSignIn() {
        signInButton.click();
    }

    public void enterParameters(String email, String password) {
        emailInput.type(email);
        passwordInput.type(password);
    }

    public void submitLoggin() {
        submitLogginButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isElementPresent();
    }

}
