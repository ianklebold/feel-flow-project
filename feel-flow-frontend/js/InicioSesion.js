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
    console.log(datos)
    // Realizar una solicitud POST al backend
    fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    })
      .then(response => {
        if (response.status == 200) {
          // El registro se realizó con éxito, puedes redirigir al usuario a otra página
          window.location.href = "../pages/Home.html";
          console.log(response.token);
          console.log(response.Authorization);
          /*
          console.log(response.headers.get('Location'));
          console.log(response);
          //window.location.href = 'pagina_de_exito.html';
          */
        } else {
          // Manejar errores, por ejemplo, mostrar un mensaje de error
          /*console.error('Error al registrar usuario');*/
          messageContainer.textContent = "Usuario o contraseña incorrectos.";
        }
      })
      .catch(error => {
        // Manejar errores de red u otros errores
        console.error('Error de red:', error);
      });
  });
});






/*
const listUsers = async (email, password) => {
  const response = await fetch('https://jsonplaceholder.typicode.com/users');
  const users = await response.json();

  let isAuthenticated = false;

  users.forEach((user) => {
    if (user.email === email && user.username === password) {
      isAuthenticated = true;
    }
  });

  const messageContainer = document.getElementById("message-container");

  if (!isAuthenticated) {
    messageContainer.textContent = "Usuario o contraseña incorrectos.";
  } else {
    window.location.href = "../pages/Home.html";
  }
};

document.getElementById("login-form").addEventListener("submit", function (event) {
  event.preventDefault();

  const emailInput = document.getElementById("email");
  const passwordInput = document.getElementById("password");

  const email = emailInput.value;
  const password = passwordInput.value;

  listUsers(email, password);
});*/