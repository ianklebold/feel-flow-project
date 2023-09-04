const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');
console.log(token)
function GetUser(id, token) {
  let url = `http://localhost:8080/api/v1/user/${id}`;
  // Realizar una solicitud POST al backend
  fetch(url, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
  .then(response => {
    if (response.status === 200) {
      return response.json(); 
    } else {
      throw new Error('Error al recuperar los datos');
    }
  })
  .then(data => {
    console.log(data);
  })
}
/*
const listUsers = async (email) => {
    const response = await fetch('https://jsonplaceholder.typicode.com/users');
    const users = await response.json();
  
    let tableBody = ``;
    let Nombre = ``;
    let Rol = ``;
    let Email = ``;
    let Empresa = ``;
    let Equipo = ``;
    users.forEach((user) => {
        if (user.email === email) {
            Nombre += `${user.name}`
            Rol += `${user.username}`
            Email += `${user.email}`
            Empresa += `${user.company.name}`
            Equipo += `${user.website}`
        }
        
    });
    document.getElementById("name").innerHTML = Nombre;
    document.getElementById("rol").innerHTML = Rol;
    document.getElementById("Nombre").innerHTML = Nombre;
    document.getElementById("correo").innerHTML = Email;
    document.getElementById("empresa").innerHTML = Empresa;
    document.getElementById("equipo").innerHTML = Equipo;
    document.getElementById("Rol").innerHTML = Rol;
    


};
*/
window.addEventListener("load", function () {
    
    GetUser(idLocation,token);
})

/*
document.getElementById("login-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    const email = emailInput.value;
    const password = passwordInput.value;

    listUsers(email, password);
});*/

/*
const listUsers = async (username) => {
  try {
    const response = await fetch('http://localhost:8080/api/v1/user/');
    if (!response.ok) {
      throw new Error('No se pudo obtener la lista de usuarios');
    }
    const users = await response.json();

    let Nombre = '';
    let Apellido = ''; 
    let Email = 'username'; 
    let Contraseña = ''; 
    let Empresa = ''; 

    users.forEach((user) => {
      if (user.username === username) { 
        Nombre = user.name;
        Apellido = user.surname; 
        Email = user.email; 
        Contraseña = user.password; 
        Empresa = user.enterpriseDTO.name; 
      }
    });

    document.getElementById("name").innerHTML = Nombre;
    document.getElementById("apellido").innerHTML = Apellido; // Cambiado a 'apellido'
    document.getElementById("Nombre").innerHTML = Nombre;
    document.getElementById("correo").innerHTML = Email;
    document.getElementById("contraseña").innerHTML = Contraseña; // Cambiado a 'contraseña'
    document.getElementById("empresa").innerHTML = Empresa;
  } catch (error) {
    console.error('Error:', error);
  }
};

window.addEventListener("load", function () {
  listUsers("aca iria el id location"); // Cambiado a 'ianstgo@gmail.com'
});

*/