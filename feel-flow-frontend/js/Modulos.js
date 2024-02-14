import { GetUser } from "../js/functions/GetPerfil.js";
import { GetIdEquipo } from "./functions/GetEquipos.js";
import { Logueado } from "./functions/User.js";
import { ObtenerEncuestas, crearModulo, CerrarModulo, ObtenerIDModulo, enableToClose } from "./functions/post/twelve_steps.js";
/*import { crearModulo } from "./functions/post/twelve_steps";*/

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

let idTeam = null;
let idModulo = null;
let mensaje = "";
let habilitado_para_cerrar = false;

function openPopup(id_popup) {
    document.getElementById(id_popup).style.display = 'flex';
}

function closePopup(id_popup) {
    document.getElementById(id_popup).style.display = 'none';
}

async function tieneEncuestas() {
    return ObtenerEncuestas(token)
        .then(data => {
            let tiene_encuesta
            data.length > 0 ? tiene_encuesta = true : tiene_encuesta = false
            return data.length;
        })
        .catch(error => {
            console.error("Error al obtener las encuestas activas: " + error);
            return false;
        })
}

async function MostrarPantalla() {

    if (rol == "USER_REGULAR") {
        document.getElementById("crearModuloButton").classList.add("hidden");
        document.getElementById("cerrarModuloButton").classList.add("hidden");
        tieneEncuestas()
            .then(activo => {
                if (activo === 0) {
                    document.getElementById("sinModulos").classList.remove("hidden");

                } else {
                    document.getElementById("twelveSteps").classList.remove("hidden");


                }
            })
            .catch(error => {
                console.error("Error al obtener las encuestas activas:", error);
            });
    } else {
        document.getElementById("twelveSteps").classList.remove("hidden");
        document.getElementById("contestarModuloButton").classList.add("hidden");
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
            console.error('Error sending request: ', error);
        });

    ObtenerIDModulo(token, "TWELVE_STEPS")
        .then(id => {
            if (id !== null) {
                idModulo = id;
            } else {
                console.error('Error when obtaining the module id');
            }
        })
        .catch(error => {
            console.error('Error sending request: ', error);
        });

    enableToClose(token, "TWELVE_STEPS")
        .then(status => {
            if (status !== null) {
                console.log('estado del modulo:', status);
                habilitado_para_cerrar = status;
            } else {
                console.error('Could not get module status');
            }
        })
        .catch(error => {
            console.error('Error sending request: ', error);
        });
}

function tknValido() {
    Logueado(idLocation, token)
        .then(data => {
            data ? MostrarPantalla() : window.location.href = "../pages/sign_in.html";
        })
        .catch(error => {
            console.error(error)
        })
}

tknValido()

document.getElementById("crearModuloButton").addEventListener("click", function () {
    if (rol == "TEAM_LEADER") {
        crearModulo(token, idTeam)
            .then(result => {
                if (result === 'Module created successfully') {
                    mensaje = "Se creo el modulo exitosamente";
                } else {
                    mensaje = "Error: " + result;
                }

                document.getElementById("MensajeRequest").textContent = mensaje
                openPopup('overlay')
            })
            .catch(error => {
                console.error('Error al llamar a crearModulo:', error);
            });
    } else {
        mensaje = "Solo el Team Leader del equipo puede abrir el modulo"
        document.getElementById("MensajeRequest").textContent = mensaje
        openPopup('overlay')
    }

});

document.getElementById("contestarModuloButton").addEventListener("click", function () {
    window.location.href = "../pages/Encuesta_TSM.html"
});

document.getElementById("closePopup").addEventListener("click", function () {
    closePopup('overlay');
});

document.getElementById("CancelarAlertPopup").addEventListener("click", function () {
    closePopup('CloseAlertPopUp');
});

document.getElementById("cerrarModuloButton").addEventListener("click", function () {


    if (rol == "TEAM_LEADER") {
        if (habilitado_para_cerrar) {
            CerrarModulo(token, idModulo)
                .then(result => {
                    if (result === 'Request Processed successfully') {
                        mensaje = "Se cerro el modulo exitosamente";
                    } else {
                        mensaje = "Error: " + result;
                    }

                    document.getElementById("MensajeRequest").textContent = mensaje;
                    openPopup('overlay');
                })
                .catch(error => {
                    console.error('Error: ', error);
                });
        } else {
            openPopup('CloseAlertPopUp');
            console.log(idModulo)
        }
    } else {
        mensaje = "Solo el Team Leader del equipo puede cerrar el modulo";
        document.getElementById("MensajeRequest").textContent = mensaje;
        openPopup('overlay');
    }
});

document.getElementById("ContinuarAlertPopup").addEventListener("click", function () {
    closePopup('CloseAlertPopUp');
    console.log(idModulo)
    CerrarModulo(token, idModulo)
        .then(result => {
            if (result === 'Request Processed successfully') {
                mensaje = "Se cerro el modulo exitosamente";
            } else {
                mensaje = "Error: " + result;
            }

            document.getElementById("MensajeRequest").textContent = mensaje;
            openPopup('overlay');
        })
        .catch(error => {
            console.error('Error: ', error);
        });
})
