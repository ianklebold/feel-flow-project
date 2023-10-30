import { InvitarAlEquipo } from "./functions/post/invite_to_team.js";
import { GetEquipo } from "./functions/GetEquipos.js";
import { GetUser } from "../js/functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
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

console.log(Id_Equipo);
console.log(token)
document.getElementById('inviteMembersButton').addEventListener('click', function() {
  InvitarAlEquipo(token, Id_Equipo)
    .then (data => {
      console.log(data)
    }); 
})

/*




// Agrega un evento de clic al botón "Invitar miembros"
document.getElementById('inviteMembersButton').addEventListener('click', function () {
  // Generar el enlace con el uuid obtenido
  obtenerIdTeamYGenerarLink();

  // Función para obtener el IdTeam y generar el enlace
  function obtenerIdTeamYGenerarLink() {
    // Supongamos que tienes el IdTeam y puedes utilizarlo para obtener el uuid
    // Construye la URL del endpoint para obtener la invitación utilizando el IdTeam
    const idTeam = 123; // Reemplaza esto con la lógica para obtener el IdTeam
    const inviteEndpoint = `http://localhost:8080/api/v1/team/${idTeam}/invite`;

    // Realiza la solicitud GET para obtener el uuid
    fetch(inviteEndpoint, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => {
        if (response.status === 200) {
          return response.json(); // Parsea la respuesta JSON
        } else {
          throw new Error('Error al obtener la invitación del equipo');
        }
      })
      .then(data => {
        const uuid = data.uuid; // Obtiene el uuid de la respuesta

        // Crear el contenido HTML del modal con el botón de copiar
        const modalContent = `
          <strong>Link de Invitación:</strong>
          <a href="sign_up_invite.html?uuid=${uuid}">Registrarse con invitación</a>
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
      })
      .catch(error => {
        console.error('Error al realizar la solicitud:', error);
      });
  }
});


//Aca comienza la funcionalidad de la pagina

import { GetUser } from "../js/functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

window.addEventListener("load", function () {
    GetUser(idLocation, token)
        .then(data => {
            MostrarPantalla(data);
        })
        .catch(error => {
            console.error(error);
        });
});

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

        // Obtener el IdTeam y el uuid
        const idTeam = 123; // Reemplaza esto con la lógica para obtener el IdTeam
        generar_link_uuid(idTeam); // Función para obtener el IdTeam y procesar el uuid
    } else {
        // Si no es Team Leader ni Admin, redirige a la página "Mi equipo"
        console.log("Acceso denegado");
        window.location.href = "../pages/team.html";
    }
}

function generar_link_uuid(idTeam) {
  const inviteEndpoint = `http://localhost:8080/api/v1/team/${idTeam}/invite`;

  // Realiza la solicitud GET para obtener el uuid
  fetch(inviteEndpoint, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}`
      }
  })
      .then(response => {
          if (response.status === 200) {
              return response.json(); // Parsea la respuesta JSON
          } else {
              throw new Error('Error al obtener la invitación del equipo');
          }
      })
      .then(data => {
          const uuid = data.uuid; // Obtiene el uuid de la respuesta
          console.log(`UUID de invitación: ${uuid}`);
          
          // Generar un enlace que lleve a la página sign_up_invite.html con el uuid en el parámetro
          const signupInviteLink = document.createElement('a');
          signupInviteLink.href = `sign_up_invite.html?uuid=${uuid}`;
          signupInviteLink.textContent = 'Registrarse con invitación';
          
          // Agregar el enlace al documento
          document.body.appendChild(signupInviteLink);
      })
      .catch(error => {
          console.error('Error al realizar la solicitud:', error);
      });
}

*/