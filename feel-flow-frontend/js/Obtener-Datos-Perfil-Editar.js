const id = localStorage.getItem('idLocation')
const listUsers = async (id) => {
  const response = await fetch('http://localhost:8080/api/v1/user/' + id);
  const users = await response.json();

  let name = ``;
  let mail = ``;
  //let empresa = ``;
  let Nombre = ``;
  let Rol = ``;
  let Email = ``;
  //let Empresa = ``;
  let Equipo = ``;
  let arreglo = ``;
  let Apellido = ``;
  let Nombre2 = ``;


  Nombre2 += `${user.name}`
  //Rol += `${user.username}`
  Email += `${user.username}`
  Equipo += `${user.website}`

  arreglo = Nombre2.split(" ");
  Nombre = arreglo[0];
  Apellido = arreglo[1];

  name += `${Nombre}`
  const inputNombre = document.getElementById('epnombre');
  inputNombre.value = name;

  apellido = `${Apellido}`
  const inputApellido = document.getElementById('epapellido');
  inputApellido.value = apellido;

  mail += `${Email}`
  const inputEmail = document.getElementById('epemail');
  inputEmail.value = mail;

};

document.getElementById("name").innerHTML = Nombre2;
document.getElementById("rol").innerHTML = Rol;

document.getElementById("edit-form").addEventListener("submit", function (event) {
  /*Falta controlar si se modificaron datos y enviarlos a algún lado*/
  event.preventDefault();
  window.location.href = "../pages/profile.html";
});
  

window.addEventListener("load", function () {
  listUsers("id");
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
