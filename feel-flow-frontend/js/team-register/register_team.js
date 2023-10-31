//import { GetUser } from "../../js/functions/GetPerfil.js";

//const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');
console.log("token: ", { token })
// window.addEventListener("load", function () {
//     GetUser(idLocation, token)
//         .then(data => {
//             //console.log(data);
//         })
//         .catch(error => {
//             console.error(error);
//         });
// })

function enviarCredenciales(username, password) {
    // <input type="text" class="form-control" id="copiedText"
    //     value="Email: hola@gmail.com " readonly>
    // <span class="input-group-append">
    //     <button type="button" id="copyButton"
    //         class="btn bg-gradient-dark my-2 mt-2">
    //         <i class="fa fa-clipboard" aria-hidden="true"
    //             style="color: #f8f9fa;"></i>
    //     </button>
    // </span>
    // console.log(username)

    // var divContenedor = document.createElement('div')
    // divContenedor.setAttribute('class', 'form-group', 'input-group', 'justify-content-around');

    // var input = document.createElement('input');
    // input.type = 'text';
    // input.classList.add('form-control');
    // input.id = 'copiedText';
    // input.value = 'Usuario: ' + username;
    // input.readOnly = true;

    // var span = document.createElement('span');
    // span.setAttribute('class', 'imput-group-append');

    // var button = document.createElement('button');
    // button.setAttribute('type', 'button');
    // button.setAttribute('class', 'btn', 'bg-gradient-dark', 'my-2', 'mt-2');
    // button.setAttribute('id', 'copyButton');

    // var icon = document.createElement('i');
    // icon.setAttribute('class', 'fa', 'fa-clipboard');
    // icon.setAttribute('aria-hidden', 'true');
    // icon.setAttribute('style', 'color: #f8f9fa');

    // button.appendChild(icon);
    // span.appendChild(button);
    // divContenedor.appendChild(input);
    // divContenedor.appendChild(span);

    const divContainer = document.createElement('div');
    divContainer.classList.add('form-group', 'input-group', 'justify-content-around');
    divContainer.id = '00-datos';
    divContainer.style = 'with: 80%'

    // Crear el elemento <input> con atributos
    const inputElement = document.createElement('textarea');
    inputElement.classList.add('form-control');
    inputElement.id = 'copiedText';
    inputElement.value = 'Email: ' + username + '\nPassword: ' + password;
    inputElement.readOnly = true;
    inputElement.innerHTML = inputElement.value.replace(/\n/g, '<br>');

    // Crear el span con clase "input-group-append"
    const spanElement = document.createElement('span');
    spanElement.classList.add('input-group-append');

    // Crear el botón
    const buttonElement = document.createElement('button');
    buttonElement.type = 'button';
    buttonElement.id = 'copyButton';
    buttonElement.classList.add('btn', 'bg-gradient-dark', 'my-2', 'mt-2');

    // Crear el icono del botón
    const iconElement = document.createElement('i');
    iconElement.classList.add('fa', 'fa-clipboard');
    iconElement.setAttribute('aria-hidden', 'true');
    iconElement.style.color = '#f8f9fa';

    // Agregar el icono al botón
    buttonElement.appendChild(iconElement);

    // Agregar el botón al span
    spanElement.appendChild(buttonElement);

    // Agregar el input y el span al div principal
    divContainer.appendChild(inputElement);
    divContainer.appendChild(spanElement);

    var contenedor = document.getElementById('00-datos');
    contenedor.insertAdjacentElement("beforebegin", inputElement);
}

document.addEventListener('DOMContentLoaded', function () {

    const formulario = document.getElementById('registro-formulario');
    const nombreTeamInput = document.getElementById('NombreTeam');
    const descripcionInput = document.getElementById('Descripcion');
    const nombreInput = document.getElementById('Nombre');
    const apellidoInput = document.getElementById('Apellido');
    const passwordInput = document.getElementById('floatingInputGroup1');
    const emailInput = document.getElementById('email');
    const registrarseButton = document.querySelector('.registrar');
    const buttonClosePopUp = document.getElementById('closePopUp');

    // Función para verificar si los campos están vacíos
    function camposEstanVacios() {
        return (
            nombreInput.value === '' ||
            apellidoInput.value === '' ||
            emailInput.value === '' ||
            passwordInput.value === '' ||
            nombreTeamInput.value === '' ||
            descripcionInput.value === ''
        );
    }

    // Función para habilitar o deshabilitar el botón según el estado de los campos
    function actualizarEstadoBoton() {
        registrarseButton.disabled = camposEstanVacios();
    }

    // Escuchar eventos de entrada en los campos para actualizar el estado del botón
    nombreInput.addEventListener('input', actualizarEstadoBoton);
    apellidoInput.addEventListener('input', actualizarEstadoBoton);
    emailInput.addEventListener('input', actualizarEstadoBoton);
    passwordInput.addEventListener('input', actualizarEstadoBoton);
    nombreTeamInput.addEventListener('input', actualizarEstadoBoton);
    descripcionInput.addEventListener('input', actualizarEstadoBoton);

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const name = nombreInput.value;
        const surname = apellidoInput.value;
        const username = emailInput.value;
        const password = passwordInput.value;
        const nameTeam = nombreTeamInput.value;
        const descriptionTeam = descripcionInput.value;

        const teamLeaderDTO = {
            name,
            surname,
            username,
            password
        }

        const datos = {
            nameTeam,
            descriptionTeam,
            teamLeaderDTO
        };

        //console.log(datos);
        fetch(`http://localhost:8080/api/v1/team`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        })
            // .then(response => response.json()) // Convierte la respuesta a JSON
            // .then(data => {
            //     console.log(data); // Imprime los datos del cuerpo de la respuesta
            // })
            .then(response => {
                if (response.status == 201) {
                    return response.json();
                } else {
                    console.error('Error al crear y registrar Equipo con su Team Lead');
                }
            })
            .then(data => {
                enviarCredenciales(data.username, data.password);
            })
            .catch(error => {
                console.error('Error de red:', error);
            });
    });

    // Llama a esta función para configurar el estado inicial del botón
    actualizarEstadoBoton();

    buttonClosePopUp.addEventListener('click', function () {
        window.location.href = '../../pages/home.html';
    });
});