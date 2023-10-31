import { UnirseAlEquipo } from "../functions/post/join_to_team.js";

const uuid = localStorage.getItem('Invite_uuid');

var ctl_pas = false;
var ctl_usr = false;

document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.getElementById('registro-formulario');
    const nombreInput = document.getElementById('Nombre');
    const apellidoInput = document.getElementById('Apellido');
    const emailInput = document.getElementById('in-username');
    const passwordInput = document.getElementById('in-password');
    const empresaInput = document.getElementById('Empresa');
    const registrarseButton = document.querySelector('.btn.bg-gradient-dark._ini_ses');

    // Función para verificar si los campos están vacíos
    function camposEstanVacios() {
        return (
            nombreInput.value === '' ||
            apellidoInput.value === '' ||
            emailInput.value === '' ||
            passwordInput.value === ''
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

    var pas = false;
    var usr = false;

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const name = nombreInput.value;
        const surname = apellidoInput.value;
        const username = emailInput.value;
        const password = passwordInput.value;

        if (!document.getElementById('flexCheckDefault').checked) {
            document.getElementById('flexCheckDefault').classList.add('alert-danger');
            document.getElementById('label-check').classList.add('text-danger');
        } else {

            document.getElementById('flexCheckDefault').classList.remove('alert-danger');
            document.getElementById('label-check').classList.remove('text-danger');

            const endpoint = `http://localhost:8080/api/v1/regular_user/${uuid}`;
            const datos = {
                name,
                surname,
                username,
                password
            };
            fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datos)
            })
                .then(response => {
                    if (response.status === 200) {
                        window.location.href = '../pages/sign_in.html';
                    } else if (response.status === 400) {
                        // Tratar la respuesta como texto
                        return response.text().then(errorText => {
                            // `errorText` contiene el cuerpo del error
                            var errorResponse = errorText;

                            // Puedes analizar el errorResponse si es un JSON válido, de lo contrario, simplemente trátalo como texto
                            try {
                                errorResponse = JSON.parse(errorText);
                            } catch (e) {
                                // No es un JSON válido
                                console.error('El cuerpo de error no es un JSON válido:', errorText);
                            }

                            // Resto del manejo de errores
                            var datosErroneos = errorResponse.length;

                            errorResponse.forEach(error => {
                                for (const campo in error) {
                                    var idinput = "in-" + campo;
                                    var ctlinput = "clt-" + campo;
                                    const mensaje = error[campo];

                                    if (datosErroneos < 2) {
                                        if (pas && ctl_pas) {
                                            document.getElementById('clt-password').remove();
                                            document.getElementById('in-password').classList.remove('incorrecto');
                                            ctl_pas = false;
                                            pas = false;
                                        }
                                        if (usr && ctl_usr) {
                                            document.getElementById('clt-username').remove();
                                            document.getElementById('in-username').classList.remove('incorrecto');
                                            ctl_usr = false;
                                            usr = false;
                                        }
                                    }

                                    if (campo === "password") {
                                        pas = true;
                                    } else {
                                        usr = true;
                                    }
                                    // Insertar el mensaje de error en el HTML, por ejemplo, en un elemento div con el id 'errores':
                                    const divError = document.createElement('div');
                                    divError.classList.add('invalid-tooltip', 'text-center');
                                    divError.id = ctlinput;
                                    divError.textContent = `${mensaje}`;

                                    if (campo === "password") {
                                        if (!ctl_pas) {
                                            document.getElementById(`${campo}`).appendChild(divError);
                                            document.getElementById(`${idinput}`).classList.add('incorrecto');
                                            ctl_pas = true;
                                        }
                                    }

                                    if (campo === "username") {
                                        if (!ctl_usr) {
                                            document.getElementById(`${campo}`).appendChild(divError);
                                            document.getElementById(`${idinput}`).classList.add('incorrecto');
                                            ctl_usr = true;
                                        }
                                    }

                                    // Controlar checkbox
                                    if (!document.getElementById('flexCheckDefault').checked) {
                                        document.getElementById('flexCheckDefault').classList.add('alert-danger');
                                        document.getElementById('label-check').classList.add('text-danger');
                                    } else {
                                        document.getElementById('flexCheckDefault').classList.remove('alert-danger');
                                        document.getElementById('label-check').classList.remove('text-danger');
                                    }

                                }
                            });
                        });
                    }
                })
                .catch(error => {
                    console.error(error);
                    // Devuelve una promesa rechazada en caso de error de red u otro tipo de error
                    return Promise.reject(error);
                });




            /*
            UnirseAlEquipo(Invite_uuid, name, surname, username, password)
                .then(response => {
                    console.log(response)
                })
                .catch(error => {
                    console.error(error)
                })
                /*
            var endpoint = `http://localhost:8080/api/v1/regular_user/${Invite_uuid}`;
            fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datos)
            })
                .then(response => {
                    if (response.status === 201) {
                        window.location.href = '../pages/sign_in.html'
                    } else if (response.status === 400) {

                        response.text().then(errorText => {
                            // `errorText` contiene el cuerpo del error
                            var errorResponse = JSON.parse(errorText);

                            var datosErroneos = errorResponse.length;

                            errorResponse.forEach(error => {
                                for (const campo in error) {
                                    var idinput = "in-" + campo;
                                    var ctlinput = "clt-" + campo;
                                    const mensaje = error[campo];

                                    if (datosErroneos < 2) {
                                        if (pas && ctl_pas) {
                                            document.getElementById('clt-password').remove();
                                            document.getElementById('in-password').classList.remove('incorrecto');
                                            ctl_pas = false;
                                            pas = false;
                                        }
                                        if (usr && ctl_usr) {
                                            document.getElementById('clt-username').remove();
                                            document.getElementById('in-username').classList.remove('incorrecto');
                                            ctl_usr = false;
                                            usr = false;
                                        }
                                    }

                                    if (campo === "password") {
                                        pas = true;
                                    } else {
                                        usr = true;
                                    }
                                    // Insertar el mensaje de error en el HTML, por ejemplo, en un elemento div con el id 'errores':
                                    const divError = document.createElement('div');
                                    divError.classList.add('invalid-tooltip', 'text-center');
                                    divError.id = ctlinput;
                                    divError.textContent = `${mensaje}`;

                                    if (campo === "password") {
                                        if (!ctl_pas) {
                                            document.getElementById(`${campo}`).appendChild(divError);
                                            document.getElementById(`${idinput}`).classList.add('incorrecto');
                                            ctl_pas = true;
                                        }
                                    }

                                    if (campo === "username") {
                                        if (!ctl_usr) {
                                            document.getElementById(`${campo}`).appendChild(divError);
                                            document.getElementById(`${idinput}`).classList.add('incorrecto');
                                            ctl_usr = true;
                                        }
                                    }

                                    // Controlar checkbox
                                    if (!document.getElementById('flexCheckDefault').checked) {
                                        document.getElementById('flexCheckDefault').classList.add('alert-danger');
                                        document.getElementById('label-check').classList.add('text-danger');
                                    } else {
                                        document.getElementById('flexCheckDefault').classList.remove('alert-danger');
                                        document.getElementById('label-check').classList.remove('text-danger');
                                    }

                                }
                            });
                        });

                    }
                })*/
        }
    });

    // Llama a esta función para configurar el estado inicial del botón
    actualizarEstadoBoton();
});