import { GetUser } from "../functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');
console.log(token)

const titulo_pagina = document.querySelector('title');
const pagina = titulo_pagina.textContent;

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

function crearMenu(modulo, logo_icon, ruta) {
    var personal = { // Creo una lista para luego insertarlos en otro lado
        Perfil: "Perfil",
        Configuración: "Configuración"
    }

    //Creo el módulo a insertar
    var lista = document.createElement('li');
    lista.classList.add('nav-item')
    var etiqueta_a = document.createElement('a');
    etiqueta_a.classList.add('nav-link')
    if (modulo === pagina) {
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

    // Inserto el modulo
    var divisor = document.getElementById('separador');
    if (modulo in personal) {
        divisor.insertAdjacentElement("afterend", lista);
    } else {
        divisor.insertAdjacentElement("beforebegin", lista);
    }
}

function obtenerModulosDisponibles(perfil) {
    return fetch('../assets/data/modulos.json')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el archivo JSON de módulos');
            }
            return response.json();
        })
        .then(data => {
            const listaDeModulos = data;
            const modulosDisponibles = [];

            for (const modulo in listaDeModulos) {
                if (listaDeModulos[modulo].perfiles.includes(perfil)) {
                    modulosDisponibles.push(listaDeModulos[modulo]);
                }
            }
            return modulosDisponibles;
        });
}

function MostrarPantalla(usuario) {
    let Nombre = `${usuario.name}` + `   ` + `${usuario.surname}`;

    obtenerModulosDisponibles(rol)
        .then(modulosUsuario => {
            for (var menu in modulosUsuario) {
                crearMenu(modulosUsuario[menu].nombre, modulosUsuario[menu].logo, modulosUsuario[menu].link);
            }

            var nombre_usuario = document.createElement('h6');
            nombre_usuario.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');
            nombre_usuario.textContent = Nombre;
            var divisor = document.getElementById("separador");
            divisor.insertAdjacentElement("beforeend", nombre_usuario);
        })
        .catch(error => {
            console.error('Error al cargar los módulos disponibles:', error);
        });
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