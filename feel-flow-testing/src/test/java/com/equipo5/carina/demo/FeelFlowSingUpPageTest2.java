package com.equipo5.carina.demo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowLoginPage;
import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowSingUpPage;
import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
public class FeelFlowSingUpPageTest2 implements IAbstractTest {
    
    @Test(dataProvider = "validUserCredentials", description = "Checks user registration with valid credentials")
    @MethodOwner(owner = "Jorge")
    @TestLabel(name = "testType", value = "regression")
    public void testValidUserRegistration(String name, String lastName, String birthDate, String address, String city, String country, String company, String email, String password) {
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

        feelFlowSingUpPage.enterParameters(name, lastName, email, password, company);
        feelFlowSingUpPage.clickTermsAndConditionsCheckbox();
        feelFlowSingUpPage.clickSignUp();
        
        // Add assertions for successful registration
        // Aserciones para el registro exitoso
        // softAssert.assertTrue(feelFlowSingUpPage.isRegistrationSuccessful(), "Registration was not successful");
        // softAssert.assertTrue(feelFlowSingUpPage.isConfirmationMessageDisplayed(), "Confirmation message is not displayed");
        softAssert.assertFalse(feelFlowSingUpPage.isErrorMessageDisplayed(), "Error message is not displayed");
    }

    @Test(dataProvider = "invalidUserCredentials", description = "Checks user registration with invalid credentials")
    @MethodOwner(owner = "Jorge")
    @TestLabel(name = "testType", value = "regression")
    public void testInvalidUserRegistration(String name, String lastName, String birthDate, String address, String city, String country, String company, String email, String password) {
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

        feelFlowSingUpPage.enterParameters(name, lastName, email, password, company);
        feelFlowSingUpPage.clickTermsAndConditionsCheckbox();
        feelFlowSingUpPage.clickSignUp();
        
        // Add assertions for unsuccessful registration
        // Aserciones para el registro fallido
        // softAssert.assertFalse(feelFlowSingUpPage.isRegistrationSuccessful(), "Registration was successful");
        softAssert.assertTrue(feelFlowSingUpPage.isErrorMessageDisplayed(), "Error message is not displayed");
    }

    @DataProvider(name = "validUserCredentials")
    public Object[][] validUserCredentials() {
        return new Object[][] {
            // {"Cristina", "Torres", "10/05/1999", "Avenida Italia 350", "Resistencia", "Argentina", "Accenture", "cristina.torres@accenture.com", "A12346789l@"},
            {"Jhon", "Smith", "25/08/1985", "32th Street 453", "Londres", "Inglaterra", "Amazon", "j.smith@amazon.com", "Bc71!8293"},
            {"Carolina Mariela", "Ramirez", "30/10/1978", "Avenida Bolivar 1097", "Medellín", "Colombia", "Microsoft", "carolina.mariela.ramirez@outlook.com", "m123*4567T"},
            {"Carlota", "Torres", "10/05/1999", "Avenida Italia 350", "Resistencia", "Argentina", "Accenture", "carlota.torres@accenture.com", "A12346789l@"},
            {"Juan", "Smith", "25/08/1985", "32th Street 453", "Londres", "Inglaterra", "Amazon", "juan.smith@amazon.com", "Bc71!8293"}
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

//     Basandote en hacer las combinaciones válidas e invalidad de esto:
// Datos personales:
//  	"Ejemplo 1:
// *Nombre: Cristina 
// *Apellido: Torres
// *Fecha de Nacimiento: 10/05/1999
// *Dirección: Avenida Italia 350
// *Ciudad: Resistencia
// *País: Argentina 
// *Empresa: Accenture

// Ejemplo 2 
// *Nombre: Jhon
// *Apellido: Smith
// *Fecha de Nacimiento: 25/08/1985
// *Dirección: 32th Street 453
// *Ciudad: Londres
// *País: Inglaterra
// *Empresa: Amazon 

// Ejemplo 3 
// *Nombre: Carolina Mariela
// *Apellido: Ramirez
// *Fecha de Nacimiento: 30/10/1978
// *Dirección: Avenida Bolivar 1097
// *Ciudad: Medellín
// *País: Colombia
// *Empresa: Microsoft "

// Usuarios:
//  	- "Correctos:
// *cristina.torres@accenture.com
// *j.smith@amazon.com
// *carolina.mariela.ramirez@outlook.com
// *juan.reverdito@gmail.com"

// 	- "Incorrectos:
// *cristina.torres@acenture.com
// *j.smith@amazon.con
// *cristina.torres.accenture.com
// *j.smith@amazon,com"

// Contraseñas:
// 	- "Correctas:
// * A12346789l@
// *Bc71!8293
// *m123*4567T
// *&123456cD"
// 	- "Incorrectas:
// *123456789
// *abcdefghi
// *e1876T
// *ABC167855"

// @DataProvider(name = "validUserCredentials")
// public Object[][] validUserCredentials() {
//     List<Object[]> data = new ArrayList<>();
//     for (DatosPersonales datosPersonales : getDatosPersonales()) {
//         for (String email : getCorrectEmails()) {
//             for (String password : getCorrectPasswords()) {
//                 Object[] testCaseData = new Object[]{datosPersonales.getNombre(), datosPersonales.getApellido(), datosPersonales.getFechaNacimiento(), datosPersonales.getDireccion(), datosPersonales.getCiudad(), datosPersonales.getPais(), datosPersonales.getEmpresa(), email, password};
//                 data.add(testCaseData);
//             }
//         }
//     }
//     return data.toArray(new Object[data.size()][9]);
// }

// @DataProvider(name = "invalidUserCredentials")
// public Object[][] invalidUserCredentials() {
//     List<Object[]> data = new ArrayList<>();
//     for (DatosPersonales datosPersonales : getDatosPersonales()) {
//         for (String email : getIncorrectEmails()) {
//             for (String password : getIncorrectPasswords()) {
//                 Object[] testCaseData = new Object[]{datosPersonales.getNombre(), datosPersonales.getApellido(), datosPersonales.getFechaNacimiento(), datosPersonales.getDireccion(), datosPersonales.getCiudad(), datosPersonales.getPais(), datosPersonales.getEmpresa(), email, password};
//                 data.add(testCaseData);
//             }
//         }
//     }
//     return data.toArray(new Object[data.size()][9]);
// }

private List<DatosPersonales> getDatosPersonales() {
    List<DatosPersonales> datosPersonalesList = new ArrayList<>();
    datosPersonalesList.add(new DatosPersonales("Cristina", "Torres", "10/05/1999", "Avenida Italia 350", "Resistencia", "Argentina", "Accenture"));
    datosPersonalesList.add(new DatosPersonales("Jhon", "Smith", "25/08/1985", "32th Street 453", "Londres", "Inglaterra", "Amazon"));
    datosPersonalesList.add(new DatosPersonales("Carolina Mariela", "Ramirez", "30/10/1978", "Avenida Bolivar 1097", "Medellín", "Colombia", "Microsoft"));
    return datosPersonalesList;
}

private String[] getCorrectEmails() {
    return new String[]{"cristina.torres@accenture.com", "j.smith@amazon.com", "carolina.mariela.ramirez@outlook.com", "juan.reverdito@gmail.com"};
}

private String[] getIncorrectEmails() {
    return new String[]{"cristina.torres@acenture.com", "j.smith@amazon.con", "cristina.torres.accenture.com", "j.smith@amazon,com"};
}

private String[] getCorrectPasswords() {
    return new String[]{"A12346789l@", "Bc71!8293", "m123*4567T", "*&123456cD"};
}

private String[] getIncorrectPasswords() {
    return new String[]{"123456789", "abcdefghi", "e1876T", "ABC167855"};
}

private static class DatosPersonales {
    private final String nombre;
    private final String apellido;
    private final String fechaNacimiento;
    private final String direccion;
    private final String ciudad;
    private final String pais;
    private final String empresa;

    public DatosPersonales(String nombre, String apellido, String fechaNacimiento, String direccion, String ciudad, String pais, String empresa) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public String getEmpresa() {
        return empresa;
    }
}



}
