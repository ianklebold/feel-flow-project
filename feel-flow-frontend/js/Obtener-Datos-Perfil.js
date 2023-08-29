const listUsers = async (email) => {
    const response = await fetch('https://jsonplaceholder.typicode.com/users');
    const users = await response.json();
  
    let tableBody = ``;
    let Nombre = ``;
    let Rol = ``;
    let Email = ``;
    let Empresa = ``;
    let Equipo = ``;
    /*let Descripcion = ``;*/
    users.forEach((user) => {
        if (user.email === email) {
            Nombre += `${user.name}`
            Rol += `${user.username}`
            Email += `${user.email}`
            Empresa += `${user.company.name}`
            Equipo += `${user.website}`
            
            /*Descripcion += `Hola`*/
            /*
            tableBody += `
            <li class="list-group-item border-0 ps-0 pt-0 text-sm"> <strong class="text-dark">Nombre Completo: </strong>${user.name}</li>
            <li class="list-group-item border-0 ps-0 text-sm"> <strong class="text-dark">Email: </strong>${user.email}</li>
            <li class="list-group-item border-0 ps-0 text-sm"> <strong class="text-dark">Empresa: </strong>${user.company.name}</li>
            <li class="list-group-item border-0 ps-0 text-sm"> <strong class="text-dark">Equipo: </strong>${user.website}</li>
            <li class="list-group-item border-0 ps-0 pb-0"> <strong class="text-dark">Rol: </strong>${user.username}</li>
            `*/
        }
        
    });
    document.getElementById("name").innerHTML = Nombre;
    document.getElementById("rol").innerHTML = Rol;
    /*document.getElementById("descripcion").innerHTML = Descripcion;*/
    /*document.getElementById("data").innerHTML = tableBody;*/
    document.getElementById("Nombre").innerHTML = Nombre;
    document.getElementById("correo").innerHTML = Email;
    document.getElementById("empresa").innerHTML = Empresa;
    document.getElementById("equipo").innerHTML = Equipo;
    document.getElementById("Rol").innerHTML = Rol;
    


};

window.addEventListener("load", function (){
    listUsers("Sincere@april.biz");
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