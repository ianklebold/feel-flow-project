  // Agrega un evento de clic al botón "Invitar miembros"
document.getElementById('inviteMembersButton').addEventListener('click', function () {
  // Crear el contenido HTML del modal con el botón de copiar
  const modalContent = `
    <strong>Link de Invitación:</strong>
    <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">LINK</a>
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
    // Obtener el texto que se encuentra en el campo de entrada
    const textoACopiar = document.getElementById('copiedText').value;

    // Copiar el texto al portapapeles
    navigator.clipboard.writeText(textoACopiar)
      .then(function () {
      })
      .catch(function (error) {
        // Error al copiar el texto
        console.error('Error al copiar el texto: ', error);
      });
  });
});

//Aca comienza la funcionalidad de la pagina

import { GetUser } from "../js/functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');


window.addEventListener("load", function () {
    GetUser(idLocation, token)
        .then(data => {
            MostrarPantalla(data)
        })
        .catch(error => {
            console.error(error);
        });
})



function MostrarPantalla(usuario) {
  // Dividir el token en sus partes (encabezado, carga útil y firma)
  const partesToken = token.split('.');

  // Decodificar la carga útil (parte en el índice 1) utilizando atob()
  const payloadDecodificado = atob(partesToken[1]);

  // El payload decodificado es una cadena JSON, por lo que puedes analizarla en un objeto JavaScript
  const payloadObjeto = JSON.parse(payloadDecodificado);

  if (payloadObjeto.isTeamLeader || payloadObjeto.isAdmin) {
      // Si el usuario es Team Leader o Admin, muestra el botón "Invitar miembros"
      document.getElementById('inviteMembersButton').style.display = 'block';
  } else {
      // Si no es Team Leader ni Admin, redirige a la página "Mi equipo"
      console.log("Acceso denegado");
      window.location.href = "ruta-a-mi-equipo.html";
  }
}





