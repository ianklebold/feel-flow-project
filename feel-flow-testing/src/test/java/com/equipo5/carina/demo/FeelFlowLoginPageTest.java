package com.equipo5.carina.demo;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.Assert;

import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowHomePage;
import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowLoginPage;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;

public class FeelFlowLoginPageTest implements IAbstractTest {
    
    @DataProvider(name = "validUserCredentials")
    public Object[][] validUserCredentials() {
        return new Object[][] {
            {"Cristina", "Torres", "10/05/1999", "Avenida Italia 350", "Resistencia", "Argentina", "Accenture", "cristina.torres@accenture.com", "A12346789l@"},
            {"Jhon", "Smith", "25/08/1985", "32th Street 453", "Londres", "Inglaterra", "Amazon", "j.smith@amazon.com", "Bc71!8293"},
            {"Carolina Mariela", "Ramirez", "30/10/1978", "Avenida Bolivar 1097", "Medellín", "Colombia", "Microsoft", "carolina.mariela.ramirez@outlook.com", "m123*4567T"},
            {"Admin", "Admin", "01/01/2000", "Avenida Bolivar 1097", "Medellín", "Colombia", "EmpresaAdmin1", "admin1@gmail.com", "RiverPlatecapo@123"},
            {"tlteam1", "tlteam1", "01/01/2000", "Avenida Bolivar 1097", "Medellín", "Colombia", "EmpresaAdmin1", "tlteam1@gmail.com", "RiverPlatecapo@123"}
        };
    }

    @DataProvider(name = "invalidUserCredentials")
    public Object[][] invalidUserCredentials() {
        return new Object[][] {
            {"Cristina", "Torres", "10/05/1999", "Avenida Italia 350", "Resistencia", "Argentina", "Accenture", "cristina.torres@acenture.com", "123456789"},
            {"Jhon", "Smith", "25/08/1985", "32th Street 453", "Londres", "Inglaterra", "Amazon", "j.smith@amazon.con", "abcdefghi"},
            {"Carolina Mariela", "Ramirez", "30/10/1978", "Avenida Bolivar 1097", "Medellín", "Colombia", "Microsoft", "cristina.torres.accenture.com", "e1876T"}
        };
    }

    @Test(description = "Verificar inicio y cierre de sesión con credenciales válidas",
          dataProvider = "validUserCredentials")
    @MethodOwner(owner = "Jorge")
    public void ValidLoginFeelFlowTest(String firstName, String lastName, String birthDate, String address, String city, String country, String company, String email, String password) {
        FeelFlowLoginPage feelFlowLoginPage = new FeelFlowLoginPage(getDriver());
        feelFlowLoginPage.open();

        feelFlowLoginPage.enterParameters(email, password);
        feelFlowLoginPage.submitLoggin();
        
        Assert.assertFalse(feelFlowLoginPage.isErrorMessageDisplayed(), "El mensaje de error se muestra");

        FeelFlowHomePage feelFlowHomePage = new FeelFlowHomePage(getDriver());
        feelFlowHomePage.clickProfileButton();
        feelFlowHomePage.clickLogOutButton();
        Assert.assertTrue(feelFlowHomePage.isWarningLabelDisplayed(), "La ventada de advertencia no se muestra");

        feelFlowHomePage.clickConfirmButton();
        Assert.assertTrue(feelFlowLoginPage.isOpened(), "La página de inicio no se muestra");
    }

    @Test(description = "Verificar inicio de sesión con credenciales inválidas",
          dataProvider = "invalidUserCredentials")
    @MethodOwner(owner = "Jorge")
    public void InvalidLoginFeelFlowTest(String firstName, String lastName, String birthDate, String address, String city, String country, String company, String email, String password) {
        FeelFlowLoginPage feelFlowLoginPageTest = new FeelFlowLoginPage(getDriver());
        feelFlowLoginPageTest.open();

        // feelFlowLoginPageTest.clickSignIn();
        feelFlowLoginPageTest.enterParameters(email, password);        
        feelFlowLoginPageTest.submitLoggin();
        
        Assert.assertTrue(feelFlowLoginPageTest.isErrorMessageDisplayed(), "El mensaje de error no se muestra");
    }

    @DataProvider(name = "validUserCredentials2")
    public Object[][] validUserCredentials2() {
        return new Object[][] {
            {"cristina.torres@accenture.com", "A12346789l"},
            {"j.smith@amazon.com", "Bc718293"},
            {"carolina.mariela.ramirez@outlook.com", "m1234567T"},
            {"juan.reverdito@gmail.com", "123456cD"}
        };
    }

    @DataProvider(name = "invalidUserCredentials2")
    public Object[][] invalidUserCredentials2() {
        return new Object[][] {
            {"cristina.torres@acenture.com", "123456789"},
            {"j.smith@amazon.con", "abcdefghi"},
            {"cristina.torres.accenture.com", "e1876T"},
            {"j.smith@amazon,com", "ABC167855"}
        };
    }

    @DataProvider(name = "registeredUsers")
    public Object[][] registeredUsers() {
        return new Object[][] {
            {"jacintoperez@qubikdigital.com", "Jp123456"},
            {"eulalio.sosa@accenture.com", "E123456s"},
            {"m.bracho.linari@amazon", "1234mM5678"},
            {"matina.boni@outlook.com", "051088mB"},
            {"sasvata.liliana@meli.com", "b12345678T"}
        };
    }

}
