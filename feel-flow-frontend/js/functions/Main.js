window.addEventListener("load", function () {
    GetUser(idLocation, token)
        .then(data => {
            MostrarPantalla(data)
        })
        .catch(error => {
            window.location.href = "../pages/sign_in.html"; // Usuario no logueado
            console.error(error); 
        });
})