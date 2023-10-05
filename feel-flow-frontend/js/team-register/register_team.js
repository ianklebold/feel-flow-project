//import { GetUser } from "../../js/functions/GetPerfil.js";

//const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');
console.log("token: ", {token})
// window.addEventListener("load", function () {
//     GetUser(idLocation, token)
//         .then(data => {
//             //console.log(data);
//         })
//         .catch(error => {
//             console.error(error);
//         });
// })

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
                    //console.log(response.body.get('username'));
                    //console.log(response.body);
                    console.log(response.json().body);
                } else {
                    console.error('Error al crear y registrar Equipo con su Team Lead');
                }
            })
            .then(data => {
                console.log(data);
            }) 
            .catch(error => {
                console.error('Error de red:', error);
            });
    });

    // Llama a esta función para configurar el estado inicial del botón
    actualizarEstadoBoton();

    buttonClosePopUp.addEventListener('click', function () {
        //window.location.href = '../../pages/home.html';
    });
});