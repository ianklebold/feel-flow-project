import { GetUser } from "../js/functions/GetPerfil.js";
import { Logueado } from "./functions/User.js";
import { crearModulo } from "./functions/post/twelve_steps.js";
/*import { crearModulo } from "./functions/post/twelve_steps";*/

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;


function MostrarPantalla() {
    console.log(rol)
    

}


Logueado(idLocation, token) ? MostrarPantalla() : window.location.href = "../pages/sign_in.html"


document.getElementById("crearModuloButton").addEventListener("click", function() {
    rol == "TEAM_LEADER" || rol == "ADMIN" ?  crearModulo(token, "TWELVE_STEPS") : console.error("Permisos insuficientes")
});