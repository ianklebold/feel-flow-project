import { GetUser } from "../functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const aside = document.getElementById('separador');
const titulo_pagina = document.querySelector('title');
const pagina = titulo_pagina.textContent;

const listaDeModulos = {
    Home: {
        nombre: 'Home',
        perfiles: ['Administrador', 'Team Leader', 'Miembro del Equipo'],
        logo: 'fa-home',
        link: '../Home.html'
    },
    Dashboard: {
        nombre: 'Dashboard',
        perfiles: ['Administrador', 'Team Leader', 'Miembro del Equipo'],
        logo: 'fa-pie-chart',
        link: '../Home.html'
    },
    Lideres: {
        nombre: 'Lideres',
        perfiles: ['Administrador'],
        logo: 'fa-user-circle',
        link: '../Home.html'
    },
    Equipos: {
        nombre: 'Equipos',
        perfiles: ['Administrador', 'Team Leader', 'Miembro del Equipo'],
        logo: 'fa-users',
        link: '../Teams.html'
    },
    Modulos: {
        nombre: 'Modulos',
        perfiles: ['Administrador', 'Team Leader', 'Miembro del Equipo'],
        logo: 'fa-puzzle-piece',
        link: '../Home.html'
    },
    Usuarios: {
        nombre: 'Usuarios',
        perfiles: ['Administrador', 'Team Leader'],
        logo: 'fa-user-circle',
        link: '../Home.html'
    },
};

function crearMenu(modulo, logo_icon, nom_user) {
    var lista = document.createElement('li');
    lista.classList.add('nav-item')
    var etiqueta_a = document.createElement('a');
    etiqueta_a.classList.add('nav-link')
    if (modulo == pagina) {
        etiqueta_a.classList.add('active');
    }
    etiqueta_a.href = '../pages/profile.html';

    var div_contenedor = document.createElement('div');
    div_contenedor.classList.add('icon', 'icon-shape', 'icon-sm', 'shadow', 'border-radius-md', 'text-center', 'me-2', 'd-flex', 'align-items-center', 'justify-content-center');

    var icono = document.createElement('i');
    icono.classList.add('fa', logo_icon);

    var nombre_modulo = document.createElement('span');
    nombre_modulo.classList.add('nav-link-text', 'ms-1');
    nombre_modulo.textContent = modulo;

    div_contenedor.appendChild(icono);
    etiqueta_a.appendChild(div_contenedor);
    etiqueta_a.appendChild(nombre_modulo);
    lista.appendChild(etiqueta_a);

    var divisor = document.getElementById('separador');
    divisor.insertAdjacentElement("beforebegin", lista);

    // var nombre_usuario = document.createElement('h6');
    // nombre_usuario.classList.add('ps-4', 'ms-2', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');
    // nombre_usuario.textContent = nom_user;

    // divisor.insertAdjacentElement("beforebegin", nombre_usuario);

    //<h6 class="ps-4 ms-2 text-uppercase text-xs font-weight-bolder opacity-6">Nombre del usuario</h6>

}

function obtenerModulosDisponibles(perfil) {
    const modulosDisponibles = [];
  
    for (const modulo in listaDeModulos) {
      if (listaDeModulos[modulo].perfiles.includes(perfil)) {
        modulosDisponibles.push(listaDeModulos[modulo]);
      }
    }
  
    return modulosDisponibles;
}

function MostrarPantalla(usuario) {
    const partesToken = token.split('.');
    const payloadDecodificado = atob(partesToken[1]);
    const payloadObjeto = JSON.parse(payloadDecodificado);
    let Nombre = `${usuario.name}` + `   ` + `${usuario.surname}`;

    if (payloadObjeto.isAdmin) {
        var perfilUsuario = 'Administrador'
        var modulosUsuario = obtenerModulosDisponibles(perfilUsuario);
        for (var menu in modulosUsuario) {
            crearMenu(modulosUsuario[menu].nombre, modulosUsuario[menu].logo, Nombre);
        }
        
        //crearMenu(menu, logo)
        var nombre_usuario = document.createElement('h6');
        nombre_usuario.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');
        nombre_usuario.textContent = Nombre;
        var divisor = document.getElementById("separador")
        divisor.insertAdjacentElement("beforebegin", nombre_usuario);
    } else {
        window.location.href = "../pages/Home.html";
    }   
}

window.addEventListener("load", function () {
    GetUser(idLocation, token)
    .then(data => {
      MostrarPantalla(data)
    })
    .catch(error => {
      console.error(error);
    });
})

