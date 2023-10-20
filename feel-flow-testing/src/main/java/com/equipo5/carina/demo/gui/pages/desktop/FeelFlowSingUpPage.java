package com.equipo5.carina.demo.gui.pages.desktop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;

public class FeelFlowSingUpPage extends AbstractPage {
    
    public FeelFlowSingUpPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("http://127.0.0.1:8000/pages/sign_up.html");
    }
    
    @FindBy(xpath = "//*[@id=\"FF-Background\"]/div[1]/div/img")
    private ExtendedWebElement logoElement;

    @FindBy(xpath = "//*[@id=\"FF-Background\"]/div[1]/div/h1")
    private ExtendedWebElement titleElement;

    @FindBy(xpath = "//*[@id=\"Nombre\"]")
    private ExtendedWebElement nameInput;

    @FindBy(xpath = "//*[@id=\"Apellido\"]")
    private ExtendedWebElement lastNameInput;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private ExtendedWebElement emailInput;

    @FindBy(xpath = "//*[@id=\"floatingInputGroup1\"]")
    private ExtendedWebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"Empresa\"]")
    private ExtendedWebElement companyInput;

    @FindBy(xpath = "//*[@id=\"registro-formulario\"]/div[7]/button")
    private ExtendedWebElement signUpButton;

    @FindBy(xpath = "//*[@id=\"flexCheckDefault\"]")
    private ExtendedWebElement termsAndConditionsCheckbox;

    @FindBy(xpath = "//*[@id=\"message-container\"]")
    private ExtendedWebElement errorMessage;


    public void open() {
        this.openURL(this.pageURL);
    }

    
    public void clickSignUp() {
        signUpButton.click();
    }

    public void enterParameters(String name, String lastName, String email, String password, String company) {
        emailInput.type(email);
        passwordInput.type(password);
        nameInput.type(name);
        lastNameInput.type(lastName);
        companyInput.type(company);
    }

    public void submitSingUp() {
        signUpButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isElementPresent();
    }

    // todos los boolean if isElementPresent
    public boolean isLogoDisplayed() {
        return logoElement.isElementPresent();
    }

    public boolean isTitleDisplayed() {
        return titleElement.isElementPresent();
    }

    public boolean isNameInputDisplayed() {
        return nameInput.isElementPresent();
    }

    public boolean isLastNameInputDisplayed() {
        return lastNameInput.isElementPresent();
    }

    public boolean isEmailInputDisplayed() {
        return emailInput.isElementPresent();
    }

    public boolean isPasswordInputDisplayed() {
        return passwordInput.isElementPresent();
    }

    public boolean isCompanyInputDisplayed() {
        return companyInput.isElementPresent();
    }

    public boolean isSignUpButtonDisplayed() {
        return signUpButton.isElementPresent();
    }

    public boolean isTermsAndConditionsCheckboxDisplayed() {
        return termsAndConditionsCheckbox.isElementPresent();
    }

}
