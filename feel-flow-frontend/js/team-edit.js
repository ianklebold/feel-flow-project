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

const botonGuardar = document.getElementById('miBoton');
botonGuardar.disabled = true;

GetEquipobyID(auth, equipo)
    .then(data => {
        MostrarDatos(data, payloadObjeto.isAdmin);
        localStorage.setItem('nombreEquipo', data.nameTeam);
        localStorage.setItem('descripcionEquipo', data.descriptionTeam);
    })
    .catch(error => {
        console.error(error); 
    });
comprobarCambios();


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
    textareaDescripcion.id = 'upDescripcion';
    textareaDescripcion.textContent = info.descriptionTeam; // Aca se inserta la descripción del equipo
    textareaDescripcion.rows = 3;

    var NombreEquipo = document.getElementById('04-02-Descripcion-Equipo');
    NombreEquipo.insertAdjacentElement("afterend", textareaDescripcion);
}

function comprobarCambios() {
  if (document.getElementById('upNombre') !== null && document.getElementById('upDescripcion') !== null) {
    const campos = {
      Nombre: document.getElementById('upNombre').value,
      Descripcion: document.getElementById('upDescripcion').value
    };
    
    if (campos.Nombre !== localStorage.getItem('nombreEquipo') || campos.Descripcion !== localStorage.getItem('descripcionEquipo')) {
      if (campos.Nombre !== '' && campos.Descripcion !== '') {
        botonGuardar.disabled = false;
      } else {
        botonGuardar.disabled = true;
      }     
    } else {
      botonGuardar.disabled = true;
    }  
  }  
}

setInterval(comprobarCambios, 1000);

// Agrego el enviar los datos

botonGuardar.addEventListener('click', function() {
  UpdateEquipo(equipo, auth, document.getElementById('upNombre').value, document.getElementById('upDescripcion').value)
  .then(response => {
    if (response) {
      localStorage.removeItem('nombreEquipo');
      localStorage.removeItem('descripcionEquipo');
      window.location.href = '../pages/MyTeam.html';
    }
  })
})