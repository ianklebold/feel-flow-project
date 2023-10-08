const mensaje = document.createElement('div');
mensaje.classList.add('text-centerm', 'text-sm', 'text-danger');
mensaje.textContent = "Usuario o contraseña incorrectos.";
mensaje.style.display = 'none';
const formulario = document.getElementById('login-form');
formulario.insertAdjacentElement("afterend", mensaje);

document.addEventListener('DOMContentLoaded', function () {
  const formulario = document.getElementById('login-form');

  formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const datos = {
      username,
      password
    };
    // Realizar una solicitud POST al backend
    fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    })
      .then(response => {
        if (response.status === 200) {
          return response.text();
        } else {
          // Maneja errores, por ejemplo, muestra un mensaje de error
          throw new Error('Error al iniciar sesión');
        }
      })
      .then(data => {
        const body_login = JSON.parse(data);
        const token = body_login.token; // Reemplaza con tu token JWT real
        const partesToken = token.split('.'); // Dividir el token en sus partes (encabezado, carga útil y firma)
        const payloadDecodificado = atob(partesToken[1]); // Decodificar la carga útil (parte en el índice 1) utilizando atob()
        const payloadObjeto = JSON.parse(payloadDecodificado); // El payload decodificado es una cadena JSON, por lo que puedes analizarla en un objeto JavaScript

        console.log('Payload decodificado:', payloadObjeto);
        console.log(payloadObjeto.id);
        localStorage.setItem('Token', token);
        localStorage.setItem('idLocation', payloadObjeto.id);

        window.location.href = "../pages/Home.html";
      })
      .catch(error => {
        mensaje.style.display = 'block';
        console.error(error);
      });
  });
});