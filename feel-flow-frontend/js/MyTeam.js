import { GetEquipo } from "./functions/GetEquipos.js";
import { GetEquipobyID } from "./functions/GetEquipos.js";

const Id_Equipo = localStorage.getItem('IdEquipo');
const tkn = localStorage.getItem('Token');

window.addEventListener("load", function () {
    if (Id_Equipo) {
        GetEquipobyID(tkn, Id_Equipo)
            .then(data => {
                MostrarDatos(data);
            })
            .catch(error => {
                //window.location.href = "../pages/Teams.html"; // Error al recuperar el equipo solicitado
                console.error(error); 
            });
    } else {
        GetEquipo(token)
            .then(data => {
                MostrarDatos(data);
            })
            .catch(error => {
                window.location.href = "../pages/sign_in.html"; // Usuario no logueado
                console.error(error); 
            });
    }


    
})

function MostrarDatos(info) {
    var lista = document.createElement('ul');
    lista.classList.add('list-group', 'list-group-horizontal', 'text-sm');

    var elemento = document.createElement('li');
    elemento.classList.add('list-group-item', 'border-0', 'ps-0');
    elemento.textContent = "Nombre del Equipo: ";

    var elementoNombre = document.createElement('li');
    elementoNombre.classList.add('list-group-item', 'border-0', 'ps-0');
    elementoNombre.textContent = info.nameTeam;
    
    var textareaElement = document.createElement('textarea');
    textareaElement.id = 'exampleFormControlTextarea1';
    textareaElement.rows = 3;

    var labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'exampleFormControlTextarea1');
    labelElement.textContent = 'Descripción';

    var divElement = document.createElement('div');
    divElement.classList.add('form-group');

    divElement.appendChild(labelElement);
    divElement.appendChild(textareaElement);
    lista.appendChild(elemento);
    lista.appendChild(elementoNombre);
    lista.appendChild(divElement);

    var tabla = document.getElementById('04-01-datos-equipo');
    tabla.insertAdjacentElement("afterbegin", lista);
}

