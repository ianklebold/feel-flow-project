package com.equipo5.carina.demo;

import org.testng.annotations.DataProvider;

/*
 * <!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Home</title>
  <link rel="icon" type="image/png" href="../img/logos/FeelFlow.png">
  <!--Iconos-->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <!--El segundo link utuliza en la barra de arriba-->
  <!--Estilos-->
  <link rel="stylesheet" href="../css/styles.css">
  <link rel="stylesheet" href="../css/Estilos-Feel-Flow.css">
</head>

<body class="g-sidenav-show  bg-gray-100">
  <aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3 "
    id="sidenav-main">
    <div class="sidenav-header">
      <a class="navbar-brand m-2" href="../pages/home.html">
        <img src="../img/logos/FeelFlow.png" class="navbar-brand-img" alt="main_logo">
        <span class="ms-1 font-weight-bold">Feel Flow</span>
      </a>
    </div>
    <hr class="horizontal dark custom-hr">
    <div class="collapse navbar-collapse  w-auto  max-height-vh-100 h-100" id="sidenav-collapse-main">
      <ul class="navbar-nav">
        <!-- Se insertan los elementos desde el js -->
        <li class="nav-item" id="separador"></li>
        <!-- Se insertan mas elementos desde el js -->
      </ul>
    </div>
  </aside>
  <main class="main-content position-relative max-height-vh-100 h-100 mt-1 border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
      navbar-scroll="true">
      <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5" href="../pages/home.html">Feel Flow</a></li>
            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Home</li>
          </ol>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4 justify-content-end" id="navbar">
          <ul class="navbar-nav align-items-center">
            <li class="nav-item dropdown pe-4 d-flex align-items-center">
              <a href="javascript:;" class="nav-link text-body p-0" id="dropdownMenuButton" data-bs-toggle="dropdown"
                aria-expanded="false">
                <i class="fa fa-bell cursor-pointer"></i>
              </a>
              <ul class="dropdown-menu  dropdown-menu-end  px-2 py-3 me-sm-n4" aria-labelledby="dropdownMenuButton">
                <li class="mb-2">
                  <a class="dropdown-item border-radius-md" href="javascript:;">
                    <div class="d-flex py-1">
                      <div class="my-auto">
                        <img src="../img/team-2.jpg" class="avatar avatar-sm  me-3 ">
                      </div>
                      <div class="d-flex flex-column justify-content-center">
                        <h6 class="text-sm font-weight-normal mb-1">
                          <span class="font-weight-bold">New message</span> from Laur
                        </h6>
                        <p class="text-xs text-secondary mb-0">
                          <i class="fa fa-clock me-1"></i>
                          13 minutes ago
                        </p>
                      </div>
                    </div>
                  </a>
                </li>
                <li class="mb-2">
                  <a class="dropdown-item border-radius-md" href="javascript:;">
                    <div class="d-flex py-1">
                      <div class="my-auto">
                        <img src="../img/small-logos/logo-spotify.svg" class="avatar avatar-sm bg-gradient-dark  me-3 ">
                      </div>
                      <div class="d-flex flex-column justify-content-center">
                        <h6 class="text-sm font-weight-normal mb-1">
                          <span class="font-weight-bold">New album</span> by Travis Scott
                        </h6>
                        <p class="text-xs text-secondary mb-0">
                          <i class="fa fa-clock me-1"></i>
                          1 day
                        </p>
                      </div>
                    </div>
                  </a>
                </li>
                <li>
                  <a class="dropdown-item border-radius-md" href="javascript:;">
                    <div class="d-flex py-1">
                      <div class="avatar avatar-sm bg-gradient-secondary  me-3  my-auto">
                        <svg width="12px" height="12px" viewBox="0 0 43 36" version="1.1"
                          xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                          <title>credit-card</title>
                          <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                            <g transform="translate(-2169.000000, -745.000000)" fill="#FFFFFF" fill-rule="nonzero">
                              <g transform="translate(1716.000000, 291.000000)">
                                <g transform="translate(453.000000, 454.000000)">
                                  <path class="color-background"
                                    d="M43,10.7482083 L43,3.58333333 C43,1.60354167 41.3964583,0 39.4166667,0 L3.58333333,0 C1.60354167,0 0,1.60354167 0,3.58333333 L0,10.7482083 L43,10.7482083 Z"
                                    opacity="0.593633743"></path>
                                  <path class="color-background"
                                    d="M0,16.125 L0,32.25 C0,34.2297917 1.60354167,35.8333333 3.58333333,35.8333333 L39.4166667,35.8333333 C41.3964583,35.8333333 43,34.2297917 43,32.25 L43,16.125 L0,16.125 Z M19.7083333,26.875 L7.16666667,26.875 L7.16666667,23.2916667 L19.7083333,23.2916667 L19.7083333,26.875 Z M35.8333333,26.875 L28.6666667,26.875 L28.6666667,23.2916667 L35.8333333,23.2916667 L35.8333333,26.875 Z">
                                  </path>
                                </g>
                              </g>
                            </g>
                          </g>
                        </svg>
                      </div>
                      <div class="d-flex flex-column justify-content-center">
                        <h6 class="text-sm font-weight-normal mb-1">
                          Payment successfully completed
                        </h6>
                        <p class="text-xs text-secondary mb-0">
                          <i class="fa fa-clock me-1"></i>
                          2 days
                        </p>
                      </div>
                    </div>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item dropdown pe-4 d-flex">
              <a href="javascript:;" class="nav-link text-body p-0" id="dropdownMenuButton" data-bs-toggle="dropdown"
                aria-expanded="false">
                <i class="fa fa-cog fixed-plugin-button-nav cursor-pointer"></i>
              </a>
              <ul class="dropdown-menu dropdown-menu-end px-2 py-3 me-sm-n4" aria-labelledby="dropdownMenuButton">
                <li class="mb-2">
                  <a class="dropdown-item border-radius-md" href="javascript:;">Personalizar</a>
                </li>
                <li class="mb-2">
                  <a class="dropdown-item border-radius-md" href="javascript:;">Opcion 2</a>
                </li>
                <li>
                  <a class="dropdown-item border-radius-md" href="javascript:;">Opcion 3</a>
                </li>
              </ul>
            </li>
            <li class="nav-item dropdown pe-4 d-flex">
              <a href="javascript:;" class="nav-link text-body font-weight-bold px-0" id="dropdownMenuButton"
                data-bs-toggle="dropdown" aria-expanded="false">
                <i class="fa fa-user me-sm-1"></i>
              </a>
              <ul class="dropdown-menu  dropdown-menu-end  px-2 py-3 me-sm-n4" aria-labelledby="dropdownMenuButton">
                <li class="mb-2">
                  <a class="dropdown-item border-radius-md" href="../pages/profile.html">Mi Perfil</a>
                </li>
                <li class="mb-2">
                  <a class="dropdown-item border-radius-md" href="javascript:;" id="logoutLink">Cerrar
                    Sesión</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <!--      Hasta aca lo que va si o si   -->
    <div class="container-fluid">
      <h2 class="text-center mt-2 mb-4">Ultimas novedades</h2>
      <p class="text-center mb-5">Aquí puedes encontrar información importante y administrar tus opciones.</p>
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="news-container">
            <div class="card news-card">
              <i class="fa fa-birthday-cake news-card-icon"></i>
              <div class="news-card-content">
                <h5 class="card-title news-card-title">Feliz cumpleaños</h5>
                <p class="card-text news-card-text">Hoy es el cumpleaños de Ian 'Pelo Lacio' F</p>
              </div>
            </div>
            <div class="card news-card">
              <i class="fas fa-bullhorn news-card-icon"></i>
              <div class="news-card-content">
                <h5 class="card-title news-card-title">Nuevo sprint comenzado</h5>
                <p class="card-text news-card-text">El sprint 3 comienza oficialmente hoy</p>
              </div>
            </div>
            <div class="card news-card">
              <i class="fa fa-bug news-card-icon"></i>
              <div class="news-card-content">
                <h5 class="card-title news-card-title">Se cayo producción</h5>
                <p class="card-text news-card-text">Mati no mergeo bien</p>
              </div>
            </div>
            <!-- Agrega más novedades aquí... -->
          </div>
        </div>
      </div>
    </div>
    </div>
  </main>
  <!--   Core JS Files   -->
  <script src="../js/core/bootstrap.min.js"></script> <!--Sin este no anda el dropdown-->
  <script type="module" src="../js/functions/GetPerfil.js"></script>
  <script type="module" src="../js/controller/aside.js"></script>

  <!-- Github buttons -->
  <script async defer src="https://buttons.github.io/buttons.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.js"></script>
  <script src="../js/cerrar_sesion.js"></script>
</body>

</html>
 */

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowLoginPage;
import com.equipo5.carina.demo.gui.pages.desktop.FeelFlowHomePage;
import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.carina.core.AbstractTest;

/*
 * public class FeelFlowHomePage extends AbstractPage {
    
    public FeelFlowHomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("http://127.0.0.1:8000/pages/home.html");
    }
    
    @FindBy(xpath = "//a[@class='navbar-brand m-2']")
    private ExtendedWebElement logoLink;

    @FindBy(xpath = "//ul[@class='navbar-nav']")
    private ExtendedWebElement navbarList;

    @FindBy(xpath = "//li[@class='nav-item']")
    private ExtendedWebElement separatorItem;

    @FindBy(xpath = "//button[@class='btn btn-icon btn-transparent-dark d-flex align-items-center ms-2 me-4']")
    private ExtendedWebElement notificationButton;

    @FindBy(xpath = "//div[@class='dropdown-menu dropdown-menu-right dropdown-menu-card pt-0 pb-0']")
    private ExtendedWebElement notificationDropdown;

    @FindBy(xpath = "//button[@class='btn btn-icon btn-transparent-dark d-flex align-items-center ms-2']")
    private ExtendedWebElement searchButton;

    @FindBy(xpath = "//input[@class='form-control form-control-sm']")
    private ExtendedWebElement searchInput;

    @FindBy(xpath = "//button[@class='btn btn-icon btn-transparent-dark']")
    private ExtendedWebElement settingsButton;

    @FindBy(xpath = "//div[@class='dropdown-menu dropdown-menu-right dropdown-menu-card']")
    private ExtendedWebElement settingsDropdown;

    @FindBy(xpath = "//button[@class='btn btn-icon btn-transparent-dark d-flex align-items-center ms-auto']")
    private ExtendedWebElement profileButton;

    @FindBy(xpath = "//div[@class='dropdown-menu dropdown-menu-right dropdown-menu-card']")
    private ExtendedWebElement profileDropdown;

    @FindBy(xpath = "//div[@class='container-fluid']")
    private ExtendedWebElement containerFluid;

    // Agrega aquí más elementos identificados según la estructura de la página Home

    public void open() {
        this.openURL(this.pageURL);
    }

    public void clickLogo() {
        logoLink.click();
    }

    public void clickNotificationButton() {
        notificationButton.click();
    }

    public boolean isNotificationDropdownDisplayed() {
        return notificationDropdown.isElementPresent();
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void enterSearchText(String text) {
        searchInput.type(text);
    }

    public void clickSettingsButton() {
        settingsButton.click();
    }

    public boolean isSettingsDropdownDisplayed() {
        return settingsDropdown.isElementPresent();
    }

    public void clickProfileButton() {
        profileButton.click();
    }

    public boolean isProfileDropdownDisplayed() {
        return profileDropdown.isElementPresent();
    }

    public Object getNavbarList() {
        return navbarList;
    }

    public boolean isElementPresent(Object navbarList2) {
        return ((ExtendedWebElement) navbarList2).isElementPresent();
    }

    public Object getNotificationButton() {
        return notificationButton;
    }

    public boolean isSearchButtonPresent() {
        return searchButton.isElementPresent();
    }

    public boolean isSearchInputVisible() {
        return searchInput.isElementPresent();
    }

    public boolean isSettingsButtonPresent() {
        return settingsButton.isElementPresent();
    }

    public boolean isProfileButtonPresent() {
        return profileButton.isElementPresent();
    }

    public boolean isContainerFluidPresent() {
        return containerFluid.isElementPresent();
    }

    public boolean isLogoLinkPresent() {
        return logoLink.isElementPresent();
    }

    public boolean isSeparatorItemPresent() {
        return separatorItem.isElementPresent();
    }
}
 */

public class FeelFlowHomePageTest extends AbstractTest {

    @DataProvider(name = "validUserCredentials")
    public Object[][] validUserCredentials() {
        return new Object[][] {
            {"cristina.torres@accenture.com", "A12346789l@"},
            {"j.smith@amazon.com", "Bc71!8293"},
            {"carolina.mariela.ramirez@outlook.com", "m123*4567T"}
        };
    }

    // Crear un test que verifique los elementos del home page utilizando los sioguientes metodos:
    /*
     *     public void clickLogo() {
        logoLink.click();
    }

    public void clickNotificationButton() {
        notificationButton.click();
    }

    public boolean isNotificationDropdownDisplayed() {
        return notificationDropdown.isElementPresent();
    }

    public void clickSettingsButton() {
        settingsButton.click();
    }

    public boolean isSettingsDropdownDisplayed() {
        return settingsDropdown.isElementPresent();
    }

    public void clickProfileButton() {
        profileButton.click();
    }

    public boolean isProfileDropdownDisplayed() {
        return profileDropdown.isElementPresent();
    }

    public Object getNavbarList() {
        return navbarList;
    }

    public boolean isSettingsButtonPresent() {
        return settingsButton.isElementPresent();
    }

    public boolean isProfileButtonPresent() {
        return profileButton.isElementPresent();
    }

    public boolean isLogoLinkPresent() {
        return logoLink.isElementPresent();
    }

    public boolean isLastNewsPresent() {
        return lastNews.isElementPresent();
    }

    public boolean isNewsContainerPresent() {
        return newsContainer.isElementPresent();
    }
     * 
     */

    @Test(description = "Verificar que muestran correctamente los elementos del home page",
        dataProvider = "validUserCredentials")
    @TestLabel(name = "feature", value = "Home")
    @TestLabel(name = "priority", value = "P4")
    public void verifySettingsButton(String email, String password) {
        SoftAssert softAssert = new SoftAssert();
        FeelFlowLoginPage loginPage = new FeelFlowLoginPage(getDriver());
        loginPage.open();
        loginPage.enterParameters(email, password);
        loginPage.submitLoggin();

        FeelFlowHomePage homePage = new FeelFlowHomePage(getDriver());
        homePage.open();

        softAssert.assertTrue(homePage.isLogoLinkPresent(), "El logo no se muestra");
        softAssert.assertTrue(homePage.isSettingsButtonPresent(), "El botón de configuración no se muestra");
        softAssert.assertTrue(homePage.isProfileButtonPresent(), "El botón de perfil no se muestra");
        softAssert.assertTrue(homePage.isNewsContainerPresent(), "El contenedor de noticias no se muestra");
        softAssert.assertTrue(homePage.isLastNewsPresent(), "La última noticia no se muestra");

        homePage.clickSettingsButton();
        softAssert.assertTrue(homePage.isSettingsDropdownDisplayed(), "El menú desplegable de configuración no se muestra");
        homePage.clickSettingsButton();

        homePage.clickProfileButton();
        softAssert.assertTrue(homePage.isProfileDropdownDisplayed(), "El menú desplegable de perfil no se muestra");
        homePage.clickProfileButton();

        homePage.clickNotificationButton();
        softAssert.assertTrue(homePage.isNotificationDropdownDisplayed(), "El menú desplegable de notificaciones no se muestra");
        homePage.clickNotificationButton();

        softAssert.assertAll();       
        
    }
}
