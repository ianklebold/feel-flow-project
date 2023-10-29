import { GetEquipo } from "./functions/GetEquipos.js";
import { GetEquipobyID } from "./functions/GetEquipos.js";

const Id_Equipo = localStorage.getItem('IdEquipo');
const tkn = localStorage.getItem('Token');

const partesToken = tkn.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);
const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;


window.addEventListener("load", function () {
    var admin;
    if (rol === "ADMIN") {
        admin = true;
        GetEquipobyID(tkn, Id_Equipo)
            .then(data => {
                MostrarDatos(data, admin);
            })
            .catch(error => {
                window.location.href = "../pages/Teams.html"; // Error al recuperar el equipo solicitado
                console.error(error); 
            });
    } else {
        admin = false;
        GetEquipo(tkn)
            .then(data => {
                MostrarDatos(data, admin);
            })
            .catch(error => {
                //window.location.href = "../pages/sign_in.html"; // Usuario no logueado
                console.error(error); 
            });
    }
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
    path_myteam.textContent = info[0].nameTeam;

    var path = document.getElementById('02-01-Path');
    path.insertAdjacentElement("beforeend", path_myteam);

    // Para la parte de detalles
    var detalleNombre = document.createElement('h5');
    detalleNombre.textContent = info[0].nameTeam; // Aca se inserta el nombre del equipo

    var NombreEquipoXDetalles = document.getElementById('03-01-Detalles-Equipo');
    NombreEquipoXDetalles.insertAdjacentElement("afterbegin", detalleNombre);

    // Para la parte de información del equipo

    // var elementoNombre = document.createElement('h4');
    // elementoNombre.textContent = info[0].nameTeam; // Aca se inserta el nombre del equipo
    
    var elementoDescripcion = document.createElement('textarea');
    elementoDescripcion.classList.add('py-1', 'form-control', 'mt-3');
    elementoDescripcion.textContent = info[0].descriptionTeam; // Aca se inserta la descripción del equipo
    elementoDescripcion.rows = 3;
    elementoDescripcion.disabled = true;

    let miembros = [];
    
    info[0].regularUsers.forEach(item => {
        miembros.push(' ' + item.name + ' ' + item.surname)
    });

    var elementoMiembros = document.createElement('textarea');
    elementoMiembros.classList.add('py-1', 'form-control', 'mt-3');
    elementoMiembros.textContent = miembros; // Aca se inserta la la lista de participantes del equipo
    elementoMiembros.rows = 3;
    elementoMiembros.disabled = true;

    // var NombreEquipo = document.getElementById('04-01-datos-equipo');
    // NombreEquipo.insertAdjacentElement("afterbegin", elementoNombre);

    var DescripcionEquipo = document.getElementById('04-02-Descripcion-equipo');
    DescripcionEquipo.insertAdjacentElement("beforeend", elementoDescripcion);

    var MiembrosEquipo = document.getElementById('04-02-Miembros-equipo');
    MiembrosEquipo.insertAdjacentElement("beforeend", elementoMiembros);
}

