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
        GetEquipo(tkn)
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
    // Insertar el path en el nav
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

    // Para la parte de información del equipo

    var elementoNombre = document.createElement('h4');
    elementoNombre.textContent = info.nameTeam; // Aca se inserta el nombre del equipo
    
    var elementoDescripcion = document.createElement('textarea');
    elementoDescripcion.classList.add('py-1', 'form-control', 'mt-3');
    elementoDescripcion.textContent = info.descriptionTeam; // Aca se inserta la descripción del equipo
    elementoDescripcion.rows = 3;
    elementoDescripcion.disabled = true;

    var elementoMiembros = document.createElement('textarea');
    elementoMiembros.classList.add('py-1', 'form-control', 'mt-3');
    elementoMiembros.textContent = info.regularUsers; // Aca se inserta la la lista de participantes del equipo
    elementoMiembros.rows = 3;
    elementoMiembros.disabled = true;

    console.log(info)

    var NombreEquipo = document.getElementById('04-01-datos-equipo');
    NombreEquipo.insertAdjacentElement("afterbegin", elementoNombre);

    var DescripcionEquipo = document.getElementById('04-02-Descripcion-equipo');
    DescripcionEquipo.insertAdjacentElement("beforeend", elementoDescripcion);

    var MiembrosEquipo = document.getElementById('04-02-Miembros-equipo');
    MiembrosEquipo.insertAdjacentElement("beforeend", elementoMiembros);
}

