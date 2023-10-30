import { GetUser } from "../js/functions/GetPerfil.js";
import { UpdateUser } from "../js/functions/UpdatePerfil.js";

const botonGuardar = document.getElementById('miBoton');

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);
const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

function MostrarUsuario(usuario) {
  let Nombre = `${usuario.name}` + ` ` + `${usuario.surname}`;
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

  // <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Editar Perfil</li>
  var path_myteam = document.createElement('li');
  path_myteam.classList.add('breadcrumb-item', 'text-sm', 'text-dark', 'active');
  path_myteam.setAttribute('aria-current', 'page');
  path_myteam.textContent = Nombre;

  var path = document.getElementById('02-01-Path');
  path.insertAdjacentElement("beforeend", path_myteam);

  // <div class="h-100">
  //   <h5 class="mb-1" id="name"></h5>
  //   <p class="mb-0 font-weight-bold text-sm" id="rol"></p>
  // </div>
  var ContenedorDetalles = document.createElement('div');
  ContenedorDetalles.classList.add('h-100');
  var detalleNombre = document.createElement('h5');
  detalleNombre.classList.add('mb-1');
  detalleNombre.textContent = Nombre;
  var detalleRol = document.createElement('p');
  detalleRol.classList.add('mb-0', 'font-weight-blod', 'text-sm');
  detalleRol.textContent = Rol;

  ContenedorDetalles.appendChild(detalleNombre);
  ContenedorDetalles.appendChild(detalleRol);
  var detallesUsuario = document.getElementById('04-01-Detalles');
  detallesUsuario.insertAdjacentElement("beforeend", ContenedorDetalles);

  // <div class="mb-3 form-floating shadow-lg" id="Nombre">
  //   <input type="text" class="form-control" id="epnombre" placeholder="Nombre" required>
  //   <label for="floatingInputGroup1">Nombre</label>
  // </div>
  var FormularioNombre = document.createElement('div');
  FormularioNombre.classList.add('mb-3', 'form-floating', 'shadow-lg');
  var InputNombre = document.createElement('input');
  InputNombre.type = 'text';
  InputNombre.classList.add('form-control');
  InputNombre.placeholder = 'Nombre';
  InputNombre.required = true;
  InputNombre.value = usuario.name;
  InputNombre.id = 'upNombre';
  var LabelNombre = document.createElement('label');
  LabelNombre.setAttribute('for', 'floatingInputGroup1');
  LabelNombre.textContent = 'Nombre';

  FormularioNombre.appendChild(InputNombre);
  FormularioNombre.appendChild(LabelNombre);

  // <div class="mb-3 form-floating shadow-lg" id="Apellido">
  //   <input type="text" class="form-control" id="epapellido" placeholder="Apellido" required>
  //   <label for="floatingInputGroup1">Apellido</label>
  // </div>
  var FormularioApellido = document.createElement('div');
  FormularioApellido.classList.add('mb-3', 'form-floating', 'shadow-lg');
  var inputApellido = document.createElement('input');
  inputApellido.type = 'text';
  inputApellido.classList.add('form-control');
  inputApellido.placeholder = 'Apellido';
  inputApellido.required = true;
  inputApellido.value = usuario.surname;
  inputApellido.id = 'upApellido';
  var LabelApellido = document.createElement('label');
  LabelApellido.setAttribute('for', 'floatingInputGroup1');
  LabelApellido.textContent = 'Apellido';

  FormularioApellido.appendChild(inputApellido);
  FormularioApellido.appendChild(LabelApellido);
  // <div class="mb-3 form-floating shadow-lg" id="Email">
  //   <input type="email" class="form-control" id="epemail" placeholder="Email" required>
  //   <label for="floatingInputGroup1">Email</label>
  // </div>
  var FormularioEmail = document.createElement('div');
  FormularioEmail.classList.add('mb-3', 'form-floating', 'shadow-lg');
  var inputEmail = document.createElement('input');
  inputEmail.type = 'email';
  inputEmail.classList.add('form-control');
  inputEmail.placeholder = 'Email';
  inputEmail.required = true;
  inputEmail.value = usuario.username;
  inputEmail.id = 'upUsuario';
  var LabelEmail = document.createElement('label');
  LabelEmail.setAttribute('for', 'floatingInputGroup1');
  LabelEmail.textContent = 'Email';

  FormularioEmail.appendChild(inputEmail);
  FormularioEmail.appendChild(LabelEmail);

  document.getElementById('edit-form').insertAdjacentElement("afterbegin", FormularioEmail);
  document.getElementById('edit-form').insertAdjacentElement("afterbegin", FormularioApellido);
  document.getElementById('edit-form').insertAdjacentElement("afterbegin", FormularioNombre);

  localStorage.setItem('upName', usuario.name);
  localStorage.setItem('upSurname', usuario.surname);
  localStorage.setItem('upUser', usuario.username);
}

function comprobarCambios() {
  if (document.getElementById('upNombre') !== null && document.getElementById('upApellido') !== null && document.getElementById('upUsuario') !== null) {
    const campos = {
      Nombre: document.getElementById('upNombre').value,
      Apellido: document.getElementById('upApellido').value,
      Email: document.getElementById('upUsuario').value
    };
    
    if (campos.Nombre !== localStorage.getItem('upName') || campos.Apellido !== localStorage.getItem('upSurname') || campos.Email !== localStorage.getItem('upUser')) {
      if (campos.Nombre !== '' && campos.Apellido !== '' && campos.Email !== '') {
        botonGuardar.disabled = false;
      } else {
        botonGuardar.disabled = true;
      }     
    } else {
      botonGuardar.disabled = true;
    }  
  }  
}

botonGuardar.addEventListener('click', function() {
  UpdateUser(idLocation, token, document.getElementById('upNombre').value, document.getElementById('upApellido').value, document.getElementById('upUsuario').value)
  .then(response => {
    if (response) {
      localStorage.removeItem('upName');
      localStorage.removeItem('upSurname');
      localStorage.removeItem('upUser');
      window.location.href = '../pages/profile.html';
    }
  })
})

window.addEventListener("load", function () {
  GetUser(idLocation, token)
    .then(data => {
      MostrarUsuario(data, payloadObjeto.isAdmin)
    })
    .catch(error => {
      console.error(error);
    });
  comprobarCambios();
})

setInterval(comprobarCambios, 1000);