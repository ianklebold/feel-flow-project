document.addEventListener('DOMContentLoaded', function () {
  const formulario = document.getElementById('registro-formulario');
  const nombreInput = document.getElementById('Nombre');
  const apellidoInput = document.getElementById('Apellido');
  const emailInput = document.getElementById('email');
  const passwordInput = document.getElementById('floatingInputGroup1');
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

  formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    const name = nombreInput.value;
    const surname = apellidoInput.value;
    const username = emailInput.value;
    const password = passwordInput.value;
    const enterpriseDTO = empresaInput.value;

    const datos = {
      name,
      surname,
      username,
      password,
      enterpriseDTO
    };

    fetch('http://localhost:8080/api/v1/admin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    })
      .then(response => {
        if (response.status == 201) {
          console.log(response.headers.get('Location'));
          console.log(response);
          window.location.href = '../pages/sign_in.html';
        } else {
          console.error('Error al registrar usuario');
        }
      })
      .catch(error => {
        console.error('Error de red:', error);
      });
  });

  // Llama a esta función para configurar el estado inicial del botón
  actualizarEstadoBoton();
});