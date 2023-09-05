import { GetUser } from "../js/GetPerfil.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

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