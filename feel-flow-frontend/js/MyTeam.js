import { GetEquipo } from "./functions/GetEquipos.js";
import { GetEquipobyID } from "./functions/GetEquipos.js";

const Id_Equipo = localStorage.getItem('IdEquipo');
const tkn = localStorage.getItem('token');

window.addEventListener("load", function () {
    console.log(tkn);
    console.log(Id_Equipo);
    if (Id_Equipo) {
        GetEquipobyID(tkn, Id_Equipo)
            .then(data => {
                console.log(data)
            })
            .catch(error => {
                //window.location.href = "../pages/Teams.html"; // Error al recuperar el equipo solicitado
                console.error(error); 
            });
    } else {
        GetEquipo(token)
            .then(data => {
                console.log(data)
            })
            .catch(error => {
                window.location.href = "../pages/sign_in.html"; // Usuario no logueado
                console.error(error); 
            });
    }


    
})

function MostrarDatos() {
    var lista = document.createElement('ul');
    lista.classList.add('list-group', 'list-group-horizontal', 'text-sm');

    var elemento = document.createElement('li');
    elemento.classList.add('list-group-item', 'border-0', 'ps-0');
    elemento.textContent = "Nombre del Equipo: ";

    lista.appendChild(elemento);
}

