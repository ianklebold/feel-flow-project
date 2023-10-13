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
          throw new Error('Error al iniciar sesión');
        }
      })
      .then(data => {
        const body_login = JSON.parse(data);

        const token = body_login.token;
        const partesToken = token.split('.');
        const payloadDecodificado = atob(partesToken[1]);
        const payloadObjeto = JSON.parse(payloadDecodificado);

        localStorage.setItem('token', token)
        localStorage.setItem('idLocation', payloadObjeto.id);
        window.location.href = "../pages/Home.html";
      })
      .catch(error => {
        console.error(error);
        messageContainer.textContent = "Usuario o contraseña incorrectos.";
      });
  });
});