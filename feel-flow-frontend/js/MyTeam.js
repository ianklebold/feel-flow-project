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
                MostrarDatos(data[0], admin);
            })
            .catch(error => {
                window.location.href = "../pages/sign_in.html"; // Usuario no logueado
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
    path_myteam.textContent = info.nameTeam;

    var path = document.getElementById('02-01-Path');
    path.insertAdjacentElement("beforeend", path_myteam);

    // Para la parte de detalles
    var detalleNombre = document.createElement('h5');
    detalleNombre.textContent = info.nameTeam; // Aca se inserta el nombre del equipo

    var NombreEquipoXDetalles = document.getElementById('03-01-Detalles-Equipo');
    NombreEquipoXDetalles.insertAdjacentElement("afterbegin", detalleNombre);

    // <a class="nav-link mb-0 px-0 py-1 " href="../pages/Edit_Team.html" role="tab" aria-selected="false">
    //     <i class="fa fa-pencil-square text-secondary" aria-hidden="true"></i>
    //     <span class="ms-1">Editar Equipo</span>
    // </a>
    // <a class="nav-link mb-0 px-0 py-1 " href="../pages/proximamente.html" role="tab" aria-selected="false">
    //     <i class="fa fa-trash text-secondary" aria-hidden="true"></i>
    //     <span class="ms-1">Eliminar Equipo</span>
    // </a>

    // Crear el primer elemento "Editar Equipo"
    if (admin) {
        const editarEquipoLink = document.createElement('a');
        editarEquipoLink.classList.add('nav-link', 'mb-0', 'px-0', 'py-1');
        editarEquipoLink.href = '../pages/Edit_Team.html';
        editarEquipoLink.setAttribute('role', 'tab');
        editarEquipoLink.setAttribute('aria-selected', 'false');

        const editarEquipoIcon = document.createElement('i');
        editarEquipoIcon.classList.add('fa', 'fa-pencil-square', 'text-secondary');
        editarEquipoIcon.setAttribute('aria-hidden', 'true');

        const editarEquipoText = document.createElement('span');
        editarEquipoText.classList.add('ms-1');
        editarEquipoText.textContent = 'Editar Equipo';

        editarEquipoLink.appendChild(editarEquipoIcon);
        editarEquipoLink.appendChild(editarEquipoText);

        // Crear el segundo elemento "Eliminar Equipo"
        const eliminarEquipoLink = document.createElement('a');
        eliminarEquipoLink.classList.add('nav-link', 'mb-0', 'px-0', 'py-1');
        eliminarEquipoLink.href = '../pages/proximamente.html';
        eliminarEquipoLink.setAttribute('role', 'tab');
        eliminarEquipoLink.setAttribute('aria-selected', 'false');

        const eliminarEquipoIcon = document.createElement('i');
        eliminarEquipoIcon.classList.add('fa', 'fa-trash', 'text-secondary');
        eliminarEquipoIcon.setAttribute('aria-hidden', 'true');

        const eliminarEquipoText = document.createElement('span');
        eliminarEquipoText.classList.add('ms-1');
        eliminarEquipoText.textContent = 'Eliminar Equipo';

        eliminarEquipoLink.appendChild(eliminarEquipoIcon);
        eliminarEquipoLink.appendChild(eliminarEquipoText);

        // Invitar a un miembro
        const InvitarAlEquipo = document.createElement('button');
        InvitarAlEquipo.type = 'button';
        InvitarAlEquipo.classList.add('btn', 'btn-primary');
        InvitarAlEquipo.setAttribute('daba-bs-toggle', 'modal');
        InvitarAlEquipo.setAttribute('data-bs-target', '#exampleModal');
        InvitarAlEquipo.setAttribute('data-bs-whatever', '@fat');
        InvitarAlEquipo.id = 'InvitarButton';

        const InvitarAlEquipoIcon = document.createElement('i');
        InvitarAlEquipoIcon.classList.add('fa', 'fa-pencil-square', 'text-secondary');
        InvitarAlEquipoIcon.setAttribute('aria-hidden', 'true');

        const InvitarAlEquipoText = document.createElement('span');
        InvitarAlEquipoText.classList.add('ms-1');
        InvitarAlEquipoText.textContent = 'Generar Invitación';

        InvitarAlEquipo.appendChild(InvitarAlEquipoIcon);
        InvitarAlEquipo.appendChild(InvitarAlEquipoText);

        // Insertar los elementos en tu documento HTML
        const container = document.getElementById('03-02-Botones'); 
        container.insertAdjacentElement('afterbegin', InvitarAlEquipo);
        container.insertAdjacentElement('afterbegin', eliminarEquipoLink);
        container.insertAdjacentElement('afterbegin', editarEquipoLink);

    }


    // Para la parte de información del equipo
    var elementoDescripcion = document.createElement('textarea');
    elementoDescripcion.classList.add('py-1', 'form-control', 'mt-3');
    elementoDescripcion.textContent = info.descriptionTeam; // Aca se inserta la descripción del equipo
    elementoDescripcion.rows = 3;
    elementoDescripcion.disabled = true;

    let miembros = [];

    info.regularUsers.forEach(item => {
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