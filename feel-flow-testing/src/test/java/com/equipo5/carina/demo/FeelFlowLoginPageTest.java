package com.equipo5.carina.demo;

import org.testng.annotations.Test;
import org.testng.Assert;

import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowLoginPage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;

public class FeelFlowLoginPageTest implements IAbstractTest {
    
    String username = "user";
    String password = "pass";

    public void loginBCC() {
        System.out.println("Login BCC");
    }

    @Test(description = "Check login with invalid credentials")
    @MethodOwner(owner = "Jorge")
    public void InvalidLoginFeelFlowTest() {
        FeelFlowLoginPage feelFlowLoginPageTest = new FeelFlowLoginPage(getDriver());
        feelFlowLoginPageTest.open();

        feelFlowLoginPageTest.clickSignIn();
        feelFlowLoginPageTest.enterParameters(username, password);
        feelFlowLoginPageTest.submitLoggin();
        
        Assert.assertTrue(feelFlowLoginPageTest.isErrorMessageDisplayed(), "Error message is not displayed");
    }

    @Test(description = "Check login with invalid credentials")
    @MethodOwner(owner = "Jorge")
    public void ValidloginFeelFlowTest() {
        FeelFlowLoginPage feelFlowLoginPageTest = new FeelFlowLoginPage(getDriver());
        feelFlowLoginPageTest.open();

        feelFlowLoginPageTest.clickSignIn();
        feelFlowLoginPageTest.enterParameters(username, password);
        feelFlowLoginPageTest.submitLoggin();
        
        Assert.assertTrue(feelFlowLoginPageTest.isErrorMessageDisplayed(), "Error message is not displayed");
    }
}
