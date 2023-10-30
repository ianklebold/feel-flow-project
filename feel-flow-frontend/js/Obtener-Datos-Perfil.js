import { GetUser } from "../js/functions/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);
const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

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
  console.log(usuario)
  console.log(token)
  var Rol;
  switch (rol) {
    case "ADMIN":
      Rol = "Administrador";
      break;
    case "TEAM_LEADER":
      Rol = "Team Leader";
      break;
    case "USER_REGULAR":
      Rol = "Miembro del Equipo";
      break;
    default:
      Rol = `${rol}`;
  }

  let Email = `${usuario.username}`;
  let Empresa = `${usuario.enterpriseInfoHomeDTO.name}`;
  let Equipo = `Administrador`;

  document.getElementById("name").innerHTML = Nombre;
  document.getElementById("rol").innerHTML = Rol;
  document.getElementById("Nombre").innerHTML = Nombre;
  document.getElementById("correo").innerHTML = Email;
  document.getElementById("empresa").innerHTML = Empresa;
  document.getElementById("equipo").innerHTML = Equipo;
  document.getElementById("Rol").innerHTML = Rol;
}