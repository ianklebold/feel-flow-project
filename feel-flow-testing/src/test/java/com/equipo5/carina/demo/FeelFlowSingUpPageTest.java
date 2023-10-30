package com.equipo5.carina.demo;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowLoginPage;
import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowSingUpPage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;

public class FeelFlowSingUpPageTest implements IAbstractTest {
    
    String username = "user";
    String lastName = "lastName";
    String email = "email";
    String password = "pass";
    String company = "company";

    public void loginBCC() {
        System.out.println("Login BCC");
    }

    @Test(description = "Check login with invalid credentials")
    @MethodOwner(owner = "Jorge")
    public void CheckDefaultElements() {
        FeelFlowSingUpPage feelFlowSingUpPage = new FeelFlowSingUpPage(getDriver());
        feelFlowSingUpPage.open();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(feelFlowSingUpPage.isLogoDisplayed(), "Logo is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isTitleDisplayed(), "Title is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isNameInputDisplayed(), "Name input is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isLastNameInputDisplayed(), "Last name input is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isEmailInputDisplayed(), "Email input is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isPasswordInputDisplayed(), "Password input is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isCompanyInputDisplayed(), "Company input is not displayed");
        softAssert.assertTrue(feelFlowSingUpPage.isSignUpButtonDisplayed(), "Sign up button is not displayed");
        softAssert.assertAll();
    }

    @Test(description = "Check login with invalid credentials")
    @MethodOwner(owner = "Jorge")
    public void ValidloginFeelFlowTest() {
        FeelFlowSingUpPage feelFlowSingUpPage = new FeelFlowSingUpPage(getDriver());
        feelFlowSingUpPage.open();

        CheckDefaultElements();

        feelFlowSingUpPage.enterParameters(username, lastName, email, password, company);
        feelFlowSingUpPage.clickSignUp();
        Assert.assertTrue(feelFlowSingUpPage.isErrorMessageDisplayed(), "Error message is not displayed");
    }
}
