import { GetUser } from "../js/functions/GetPerfil.js";
import { GetIdEquipo } from "./functions/GetEquipos.js";
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

var idTeam = null
var mensaje = ""

function openPopup() {
    document.getElementById('overlay').style.display = 'flex';
}

function closePopup() {
    document.getElementById('overlay').style.display = 'none';
}

function MostrarPantalla() {
    
    if (rol == "USER_REGULAR") {
        document.getElementById("crearModuloButton").classList.add("hidden");
    }

    GetIdEquipo(token)
        .then(uuid => {
            if (uuid !== null) {
                console.log('UUID del equipo:', uuid);
                idTeam = uuid;
            } else {
                console.log('No se pudo obtener el UUID del equipo.');
            }
        })
        .catch(error => {
            console.error('Error al obtener el UUID del equipo:', error);
        });

}


Logueado(idLocation, token) ? MostrarPantalla() : window.location.href = "../pages/sign_in.html"


document.getElementById("crearModuloButton").addEventListener("click", function () {
    if (rol == "TEAM_LEADER") {
        crearModulo(token, idTeam)
            .then(result => {
                if (result === 'Module created successfully') {
                    mensaje = "Se creo el modulo exitosamente";
                } else {
                    mensaje = "Error: " + result;
                    document.getElementById("MensajeRequest").classList.add("error")
                }

                document.getElementById("MensajeRequest").textContent = mensaje
                openPopup()
            })
            .catch(error => {
                console.error('Error al llamar a crearModulo:', error);
            });
    } else {
        mensaje = "Solo el Team Leader del equipo puede abrir el modulo"
        document.getElementById("MensajeRequest").classList.add("alerta")
        document.getElementById("MensajeRequest").textContent = mensaje
        openPopup()
    }
});

document.getElementById("closePopup").addEventListener("click", closePopup);
