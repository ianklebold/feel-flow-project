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
        if (response.status === 200) {
          console.log(response.statusText);
          return response.text();
          //const headers = response.body.token;
          //console.log(response)

          /*
          const token = headers.get('User-Agent');
          const locationHeader = headers.get('Location');
          
          // Muestra los valores de los encabezados en la consola
          console.log('Token:', token);
          console.log('Location Header:', locationHeader);/*
          return response.headers.json();*/

          // Continúa con el procesamiento de la respuesta si es necesario
        } else {
          // Maneja errores, por ejemplo, muestra un mensaje de error
          throw new Error('Error al iniciar sesión');
        }
      })
      .then(data => {
        console.log(data);
        const body_login = JSON.parse(data);
        console.log(body_login.token);

        const token = body_login.token; // Reemplaza con tu token JWT real

        // Dividir el token en sus partes (encabezado, carga útil y firma)
        const partesToken = token.split('.');

        // Decodificar la carga útil (parte en el índice 1) utilizando atob()
        const payloadDecodificado = atob(partesToken[1]);

        // El payload decodificado es una cadena JSON, por lo que puedes analizarla en un objeto JavaScript
        const payloadObjeto = JSON.parse(payloadDecodificado);


        console.log('Payload decodificado:', payloadObjeto);
        console.log(payloadObjeto.id)
        //localStorage.setItem('idLocation', body_login.token);
        //window.location.href = "../pages/Home.html";
      })
      .catch(error => {
        // Manejo de errores, por ejemplo, mostrar un mensaje de error
        console.error(error);
        messageContainer.textContent = "Usuario o contraseña incorrectos.";
      });
    /*.then(response => { ESTE ES EL CODIGO ANTIGUO
      if (response.status === 200) {
        window.location.href = "../pages/Home.html";
        document.getElementById("idinicio").innerHTML = response.username
        document.getElementById("tkninicio").innerHTML = response.password
        return response.json(); // Esto asume que la respuesta es un JSON directo
      } else {
        throw new Error('Error al iniciar sesión');
      }
    })
    .then(data => {
      // Si la respuesta es un objeto que contiene un campo 'json', entonces accede a 'data.json'
      // por ejemplo, data.json sería algo como data.json
      console.log(data.json);

      // Ahora puedes trabajar con 'data.json' según sea necesario

      // Finalmente, puedes redirigir al usuario a otra página
      
      window.location.href = "../pages/Home.html";
      document.getElementById("idinicio").innerHTML = response.username
      document.getElementById("tkninicio").innerHTML = response.password
    })
    .catch(error => {
      console.error(error);
      messageContainer.textContent = "Usuario o contraseña incorrectos.";
    });*/
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