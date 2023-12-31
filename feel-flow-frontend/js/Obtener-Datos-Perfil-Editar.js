import { GetUser } from "../js/functions/GetPerfil.js";
import { UpdateUser } from "../js/functions/UpdatePerfil.js";

const upName = ``;
const upSurname = ``;
const upUser = ``;

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

document.addEventListener('DOMContentLoaded', function () {
  const botonGuardar = document.getElementById('miBoton');
  const campos = {
    Nombre: document.getElementById('epnombre'),
    Apellido: document.getElementById('epapellido'),
    Email: document.getElementById('epemail')
  };
  // Variable para rastrear si se realizaron cambios en los campos
  let cambiosRealizados = false;
  // Función para verificar si un campo ha cambiado y no está vacío
  function campoHaCambiadoYNoEstaVacio(campo) {
    return campo.value !== campo.defaultValue && campo.value.trim() !== '';
  }
  // Función para verificar si un campo está vacío
  function campoEstaVacio(campo) {
    return campo.value.trim() === '';
  }
  // Función para verificar y habilitar/deshabilitar el botón "Guardar"
  function actualizarEstadoBoton() {
    const algunCampoHaCambiado = Object.values(campos).some(campoHaCambiadoYNoEstaVacio);
    const algunCampoEstaVacio = Object.values(campos).some(campoEstaVacio);
    botonGuardar.disabled = !algunCampoHaCambiado || algunCampoEstaVacio;
  }
  // Agregar escucha de eventos "input" a los campos
  Object.values(campos).forEach((campo) => {
    campo.addEventListener('input', function () {
      cambiosRealizados = true;
      actualizarEstadoBoton();
    });
  });
  // Llama a esta función para configurar el estado inicial del botón
  actualizarEstadoBoton();
});

document.getElementById("edit-form").addEventListener("submit", function (event) {
  event.preventDefault();
  let newName = document.getElementById('epnombre').value;
  let newSurname = document.getElementById('epapellido').value;
  let newUsername = document.getElementById('epemail').value;
  UpdateUser(idLocation, token, newName, newSurname, newUsername)
    .then(data => {
      console.log(data)
    })
    .catch(error => {
      console.error(error);
    });
  window.location.href = "../pages/profile.html";
});

window.addEventListener("load", function () {
  GetUser(idLocation, token)
    .then(data => {
      MostrarUsuario(data)
    })
    .catch(error => {
      console.error(error);
    });

})

function MostrarUsuario(usuario) {
  let Nombre = `${usuario.name}` + ` ` + `${usuario.surname}`;
  let Rol = `Administrador`;

  document.getElementById("name").innerHTML = Nombre;
  document.getElementById("rol").innerHTML = Rol;
  document.getElementById('epnombre').value = `${usuario.name}`;
  document.getElementById('epapellido').value = `${usuario.surname}`;
  document.getElementById('epemail').value = `${usuario.username}`;
  upName = `${usuario.name}`;
  upSurname = `${usuario.surname}`;
  upUser = `${usuario.username}`;
}












/*
const listUsers = async (id) => {
  try {
    const response = await fetch(`http://localhost:8080/api/v1/user/${idLocation}`);
    if (!response.ok) {
      throw new Error(`Error en la solicitud: ${response.status}`);
    }
    
    const user = await response.json();

    let Nombre = '';
    let Email = '';

    Nombre += user.name;
    Email += user.username;

    const arreglo = Nombre.split(" ");
    const name = arreglo[0];
    const apellido = arreglo[1];

    const inputNombre = document.getElementById('epnombre');
    inputNombre.value = name;

    const inputApellido = document.getElementById('epapellido');
    inputApellido.value = apellido;

    const inputEmail = document.getElementById('epemail');
    inputEmail.value = Email;

    // Mostrar los datos en los elementos HTML
    document.getElementById("name").innerHTML = user.name;
    document.getElementById("rol").innerHTML = user.role;
  } catch (error) {
    console.error('Error en la solicitud:', error.message);
  }
};

document.getElementById("edit-form").addEventListener("submit", function (event) {
  event.preventDefault();
  window.location.href = "../pages/profile.html";
});

window.addEventListener("load", function () {
  listUsers(idLocation);
});

document.addEventListener('DOMContentLoaded', function () {
  const botonGuardar = document.getElementById('miBoton');
  const campos = {
    Nombre: document.getElementById('epnombre'),
    Apellido: document.getElementById('epapellido'),
    Email: document.getElementById('epemail'),
    //Empresa: document.getElementById('epempresa')
  };

  // Variable para rastrear si se realizaron cambios en los campos
  let cambiosRealizados = false;

  // Función para verificar si un campo ha cambiado y no está vacío
  function campoHaCambiadoYNoEstaVacio(campo) {
    return campo.value !== campo.defaultValue && campo.value.trim() !== '';
  }

  // Función para verificar si un campo está vacío
  function campoEstaVacio(campo) {
    return campo.value.trim() === '';
  }

  // Función para verificar y habilitar/deshabilitar el botón "Guardar"
  function actualizarEstadoBoton() {
    const algunCampoHaCambiado = Object.values(campos).some(campoHaCambiadoYNoEstaVacio);
    const algunCampoEstaVacio = Object.values(campos).some(campoEstaVacio);
    botonGuardar.disabled = !algunCampoHaCambiado || algunCampoEstaVacio;
  }

  // Agregar escucha de eventos "input" a los campos
  Object.values(campos).forEach((campo) => {
    campo.addEventListener('input', function () {
      cambiosRealizados = true;
      actualizarEstadoBoton();
    });
  });

  // Llama a esta función para configurar el estado inicial del botón
  actualizarEstadoBoton();
});
*/