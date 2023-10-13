import { GetUser } from "../functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const titulo_pagina = document.querySelector('title');
const pagina = titulo_pagina.textContent;

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

const listaDeModulos = {
    Home: {
        nombre: 'Home',
        perfiles: ['ADMIN', 'TEAM_LEADER', 'USER_REGULAR'],
        logo: 'fa-home',
        link: '../pages/Home.html'
    },
    Dashboard: {
        nombre: 'Dashboard',
        perfiles: ['ADMIN', 'TEAM_LEADER', 'USER_REGULAR'],
        logo: 'fa-pie-chart',
        link: '../pages/proximamente.html'
    },
    Lideres: {
        nombre: 'Lideres',
        perfiles: ['ADMIN'],
        logo: 'fa-user-circle',
        link: '../pages/proximamente.html'
    },
    Equipos: {
        nombre: 'Equipos',
        perfiles: ['ADMIN', 'TEAM_LEADER', 'USER_REGULAR'],
        logo: 'fa-users',
        link: '../pages/Teams.html'
    },
    Modulos: {
        nombre: 'Modulos',
        perfiles: ['ADMIN', 'TEAM_LEADER', 'USER_REGULAR'],
        logo: 'fa-puzzle-piece',
        link: '../pages/proximamente.html'
    },
    Usuarios: {
        nombre: 'Usuarios',
        perfiles: ['ADMIN', 'TEAM_LEADER'],
        logo: 'fa-user-circle',
        link: '../pages/proximamente.html'
    },
};

function crearMenu(modulo, logo_icon, ruta) {
    var lista = document.createElement('li');
    lista.classList.add('nav-item')
    var etiqueta_a = document.createElement('a');
    etiqueta_a.classList.add('nav-link')
    if (modulo == pagina) {
        etiqueta_a.classList.add('active');
    }
    etiqueta_a.href = ruta;

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
    let Nombre = `${usuario.name}` + `   ` + `${usuario.surname}`;

    if (payloadObjeto.isAdmin) {
        var modulosUsuario = obtenerModulosDisponibles(rol);
        for (var menu in modulosUsuario) {
            crearMenu(modulosUsuario[menu].nombre, modulosUsuario[menu].logo, modulosUsuario[menu].link);
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

