import { GetEquipobyID } from "./functions/GetEquipos.js";
import { UpdateEquipo } from "./functions/UpdateEquipo.js";

const equipo = localStorage.getItem('IdEquipo');
var auth;
if (localStorage.getItem('token') == null) {
    auth = localStorage.getItem('Token');
} else {
    auth = localStorage.getItem('token');
}
const partesToken = auth.split('.'); 
const payloadDecodificado = atob(partesToken[1]); 
const payloadObjeto = JSON.parse(payloadDecodificado);

const upName = ``;
const upDescription = ``;

window.addEventListener("load", function() {
    GetEquipobyID(auth, equipo)
        .then(data => {
            MostrarDatos(data, payloadObjeto.isAdmin);
        })
        .catch(error => {
            console.error(error); 
        });
})

function MostrarDatos(info, admin) {
    // Insertar el path en el nav
    if (admin) {
        //<li class="breadcrumb-item text-sm"><a class="opacity-5" href="../pages/Teams.html">Equipos</a></li>
        var path_team = document.createElement('li');
        path_team.classList.add('breadcrumb-item', 'text-sm');

        var path_link = document.createElement('a');
        path_link.classList.add('opacity-5');
        path_link.href = '../pages/Teams.html';
        path_link.textContent = 'Equipos';

        path_team.appendChild(path_link);

        var path = document.getElementById('02-01-Path');
        path.insertAdjacentElement("beforeend", path_team);
    }
    // <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Equipos</li>
    var path_myteam = document.createElement('li');
    path_myteam.classList.add('breadcrumb-item', 'text-sm', 'text-dark', 'active');
    path_myteam.setAttribute('aria-current', 'page');
    path_myteam.textContent = info.nameTeam;

    var path = document.getElementById('02-01-Path');
    path.insertAdjacentElement("beforeend", path_myteam);

    // Para la parte de detalles
    var detalleNombre = document.createElement('h5');
    detalleNombre.textContent = info.nameTeam; // Aca se inserta el nombre del equipo

    var NombreEquipoXDetalles = document.getElementById('03-01-Detalles-Equipo');
    NombreEquipoXDetalles.insertAdjacentElement("afterbegin", detalleNombre);

    // Para los campos editables
    // <textarea type="text" class="form-control" id="05_01_Nombre_Equipo" placeholder="Nombre" required ></textarea>
    var textareaNombre = document.createElement('textarea');
    textareaNombre.setAttribute('type', 'text');
    textareaNombre.classList.add('form-control', 'shadow-lg');
    textareaNombre.id = 'upNombre';
    textareaNombre.textContent = info.nameTeam; // Aca se inserta la descripción del equipo
    textareaNombre.rows = 1;

    var NombreEquipo = document.getElementById('04-02-Nombre-Equipo');
    NombreEquipo.insertAdjacentElement("afterend", textareaNombre);

    // <textarea type="text" class="form-control" id="05_02_Descripcion_Equipo" placeholder="Descripción" required ></textarea>
    var textareaDescripcion = document.createElement('textarea');
    textareaDescripcion.setAttribute('type', 'text');
    textareaDescripcion.classList.add('form-control', 'shadow-lg');
    textareaNombre.id = 'upDescripcion';
    textareaDescripcion.textContent = info.descriptionTeam; // Aca se inserta la descripción del equipo
    textareaDescripcion.rows = 3;

    var NombreEquipo = document.getElementById('04-02-Descripcion-Equipo');
    NombreEquipo.insertAdjacentElement("afterend", textareaDescripcion);
}

function campoHaCambiadoYNoEstaVacio(campo) {
    return campo.value !== campo.defaultValue && campo.value.trim() !== '';
}
function campoEstaVacio(campo) {
    return campo.value.trim() === '';
}
function actualizarEstadoBoton(botonGuardar) {
    const algunCampoHaCambiado = Object.values(campos).some(campoHaCambiadoYNoEstaVacio);
    const algunCampoEstaVacio = Object.values(campos).some(campoEstaVacio);
    botonGuardar.disabled = !algunCampoHaCambiado || algunCampoEstaVacio;
}

document.addEventListener('DOMContentLoaded', function () {
  const botonGuardar = document.getElementById('miBoton');
  const campos = {
    Nombre: document.getElementById('upNombre'),
    Descripcion: document.getElementById('upDescripcion')
  };
  
  let cambiosRealizados = false;
  
  // Agregar escucha de eventos "input" a los campos
  Object.values(campos).forEach((campo) => {
    campo.addEventListener('input', function () {
      cambiosRealizados = true;
      actualizarEstadoBoton(botonGuardar);
    });
  });
  // Llama a esta función para configurar el estado inicial del botón
  actualizarEstadoBoton(botonGuardar);
});

document.getElementById("04-02-Formulario").addEventListener("submit", function (event) {
  event.preventDefault();
  let newName = document.getElementById('upNombre').value;
  let newDescription = document.getElementById('upDescripcion').value;

  UpdateUser(equipo, auth, newName, newDescription)
    .then(data => {
      console.log(data);
      console.log('Exito')
    })
    .catch(error => {
      console.error(error);
    });
  //window.location.href = "../pages/profile.html";
});