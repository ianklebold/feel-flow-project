async function Registrar(name, surname, username, password, enterpriseDTO) {
  const datos = {
    name,
    surname,
    username,
    password,
    enterpriseDTO
  };

  await fetch('http://localhost:8080/api/v1/admin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  })
    .then(response => {
      if (response.status === 201) {
        return response.json()
      } else if (response.status === 400) {
        return response.json().then(errorData => {
          throw { errorData };
        });
      }
    })
    // .catch(error => {
    //   if (error.errorData) {

    //   } else {
    //     console.error('Error de red:', error);
    //   }
    // });
}

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
      passwordInput.value === '' ||
      empresaInput.value === ''
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
  empresaInput.addEventListener('input', actualizarEstadoBoton);

  var ctl_pas = false;
  var ctl_usr = false;

  formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    const name = nombreInput.value;
    const surname = apellidoInput.value;
    const username = emailInput.value;
    const password = passwordInput.value;
    const enterpriseDTO = empresaInput.value;

    Registrar(name, surname, username, password, enterpriseDTO)
      .then(responseData => {
        // La solicitud se completó con éxito, responseData contiene la respuesta
        console.log('Registro exitoso:', responseData);
        document.getElementById('flexCheckDefault')
      })
      .catch(error => {
        if (error.errorData) {
          // El servidor respondió con un error 400, error contiene la respuesta
          var pas = false;
          var usr = false;
          error.errorData.forEach(error => {
            console.log(error)
            for (const campo in error) {
              var idinput = "in-" + campo;
              var ctlinput = "clt-" + campo;
              const mensaje = error[campo];
              if (campo === "password") {
                pas = true;
                usr = false;
              } else {
                usr = true;
                pas = false;
              }
              // Insertar el mensaje de error en el HTML, por ejemplo, en un elemento div con el id 'errores':
              const divError = document.createElement('div');
              divError.classList.add('invalid-tooltip', 'text-center');
              divError.id = ctlinput;
              divError.textContent = `${mensaje}`;
              if (campo === "password") {
                if (pas) {
                  if (!ctl_pas) {
                    document.getElementById(`${campo}`).appendChild(divError);
                    document.getElementById(`${idinput}`).classList.add('incorrecto');
                    ctl_pas = true;
                  }
                } else {
                  if (ctl_pas) {
                    document.getElementById(`${ctlinput}`).remove();
                    document.getElementById(`${idinput}`).classList.remove('incorrecto');
                    ctl_pas = false;
                    console.log("remove pass")
                  }
                }
              } else {
                if (usr) {
                  if (!ctl_usr) {
                    document.getElementById(`${campo}`).appendChild(divError);
                    document.getElementById(`${idinput}`).classList.add('incorrecto');
                    ctl_usr = true;
                  } else {
                    if (ctl_usr) {
                      document.getElementById(`${ctlinput}`).remove();
                      document.getElementById(`${idinput}`).classList.remove('incorrecto');
                      ctl_usr = false;
                      console.log("remove user")
                    }
                  }
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
        } else {
          // Otro tipo de error, como error de red
          console.error('Error de red u otro error:', error);
        }
      });
  });

  // Llama a esta función para configurar el estado inicial del botón
  actualizarEstadoBoton();
});