import { GetUser } from "../js/functions/GetPerfil.js";
import { GetEquipos } from "./functions/GetEquipos.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');
console.log(token)
const buscarBtn = document.getElementById('03-03-03-buscar');
const tabla = document.getElementById('03-04-Tabla-Equipos');

const campoBusqueda = document.getElementById('03-03-buscador');
campoBusqueda.addEventListener('input', Buscar);

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

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

function crearFila(logo, equipo, lider, id_equipo) {
    var fila = document.createElement('tr');
    fila.classList.add('fila')
    fila.setAttribute('data-equipo-id', id_equipo)
    var columna1 = document.createElement('td');
    var divColumna1 = document.createElement('div');
    divColumna1.classList.add('d-flex', 'px-2', 'py-1');

    var divlogo = document.createElement('div');
    var imagen = document.createElement('img');
    imagen.src = logo;
    imagen.classList.add('avatar', 'avatar-sm', 'me-3');

    var divNombre = document.createElement('div');
    divNombre.classList.add('d-flex', 'flex-column', 'justify-content-center');
    var Nombre = document.createElement('h6');
    Nombre.classList.add('mb-0', 'text-xs');
    Nombre.textContent = equipo;

    divlogo.appendChild(imagen);
    divNombre.appendChild(Nombre);
    divColumna1.appendChild(divlogo);
    divColumna1.appendChild(divNombre);
    columna1.appendChild(divColumna1);

    // Crear la segunda celda (td) y su contenido
    var columna2 = document.createElement('td');
    var Team_leader = document.createElement('p');
    Team_leader.classList.add('text-xs', 'font-weight-bold', 'mb-0');
    Team_leader.textContent = lider;

    columna2.appendChild(Team_leader);

    fila.appendChild(columna1);
    fila.appendChild(columna2);

    var tabla = document.getElementById('03-04-Equipos');
    tabla.insertAdjacentElement("afterbegin", fila);
}

// Podemos agregar una busqueda por palabras claves, x ejemplo, si tenemos el "Equipo azul" traerlo buscando por la palabra "azul"
// Se puede usar "fuse" para hacer esto, pero necesitamos la lista de equipos completa que de momento no tenemos

function Buscar() {
    const nombreEquipo = document.getElementById('03-03-01-Equipo').value.toLowerCase();
    const teamLeader = document.getElementById('03-03-02-TL').value.toLowerCase();

    // Recorre las filas de la tabla y muestra u oculta según los valores de búsqueda
    const filas = tabla.getElementsByTagName('tr');
    for (let i = 1; i < filas.length; i++) { // Empezamos desde 1 para omitir la fila de encabezado
        const fila = filas[i];
        const celdas = fila.getElementsByTagName('td');

        const nombreEquipoEnTabla = celdas[0].textContent.toLowerCase();
        const teamLeaderEnTabla = celdas[1].textContent.toLowerCase();

        if (nombreEquipoEnTabla.indexOf(nombreEquipo) || teamLeaderEnTabla.indexOf(teamLeader)) {
            fila.style.display = 'none'; // Oculta la fila si no coincide con ninguno de los criterios
        } else {
            fila.style.display = ''; // Muestra la fila si coincide con alguno de los criterios
        }
    }
}
const tablaEquipos = document.getElementById("03-04-Tabla-Equipos");

tablaEquipos.addEventListener("click", function (event) {
  const fila = event.target.closest("tr"); // Obtiene la fila clicada
  if (fila) {
    const equipoId = fila.getAttribute("data-equipo-id"); // Obtiene el ID del equipo
    if (equipoId) {
        localStorage.setItem('IdEquipo', equipoId);
        window.location.href = '../pages/MyTeam.html'; //Reemplazar por página de visualizar equipo
    }
  }
});


function MostrarPantalla() {
        
    if (rol == 'ADMIN') {
        GetEquipos(token)
            .then(data => {
                for (let team in data) {
                    var nameTeam = data[team].nameTeam;
                    var teamLeaderDTO = data[team].teamLeaderDTO.name + ' ' + data[team].teamLeaderDTO.surname;
                    var logo = "../img/apple-icon.png";
                    var uuid = data[team].uuid;
                    crearFila(logo, nameTeam, teamLeaderDTO, uuid);
                }
            })
            .catch(error => {
                window.location.href = "../pages/sign_in.html"; // Usuario no logueado
                console.error(error); 
            });
    } else {
        window.location.href = "../pages/MyTeam.html"; //Aca deberia ir a la pagina de visualizacion de equipos
    }   
     
}
