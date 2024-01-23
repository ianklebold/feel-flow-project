import { InvitarAlEquipo } from "./functions/post/invite_to_team.js";
import { GetEquipo } from "./functions/GetEquipos.js";
import { UnirseAlEquipo } from "./functions/post/join_to_team.js";

const token = localStorage.getItem('Token');
const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);
const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

var Id_Equipo;


async function obtenerIdEquipo() {
  try {
    if (rol !== "ADMIN") {
      const data = await GetEquipo(token);
      localStorage.setItem('IdEquipo', data[0].uuid);
    }
  } catch (error) {
    //window.location.href = "../pages/sign_in.html"; // Usuario no logueado
    console.error(error);
  }
}

obtenerIdEquipo();
Id_Equipo = localStorage.getItem('IdEquipo');

var link_registro;

document.getElementById('inviteMembersButton').addEventListener('click', function () {
  InvitarAlEquipo(token, Id_Equipo)
    .then(response => {
      localStorage.setItem('Invite_uuid', response.uuid);
    })
    .catch(error => {
      console.error(error); // Maneja errores aquí
    });
})

document.getElementById('inviteMembersButton').addEventListener('click', function () {
  // Crear el contenido HTML del modal con el botón de copiar
  var uuid_reg = localStorage.getItem('Invite_uuid');
  link_registro = `http://127.0.0.1:5500/feel-flow-frontend/pages/sign_up_regular_user.html?`;
  //link_registro = `http://localhost:8080/api/v1/regular_user/${uuid_reg}`;
  const modalContent = `
    <strong>Link de Invitación:</strong>
    <a href="${link_registro}">Registrarse con invitación</a>
    <button class="btn btn-primary mt-2" id="copyButton">Copiar link</button>
  `;

  // Mostrar el modal de SweetAlert
  Swal.fire({
    title: modalContent,
    icon: 'info',
    focusConfirm: false,
    confirmButtonText: 'Listo',
    confirmButtonAriaLabel: 'Thumbs up, great!',
  });

  // Agregar un evento de clic al botón "Copiar al portapapeles" en el modal
  document.getElementById('copyButton').addEventListener('click', function () {
    // Obtener el valor del enlace de invitación
    const linkToCopy = link_registro;

    // Copiar el enlace al portapapeles
    navigator.clipboard.writeText(linkToCopy)
      .then(function () {
        // Éxito al copiar el texto
        console.log('Texto copiado al portapapeles: ', linkToCopy);
      })
      .catch(function (error) {
        // Error al copiar el texto
        console.error('Error al copiar el texto: ', error);
      });
  });
});