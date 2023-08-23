const listUsers = async () => {
    const response = await fetch('https://jsonplaceholder.typicode.com/users');
    const users = await response.json();
  
    let tableBody = ``;
    users.forEach((user, index) => {
        tableBody += `
        <li class="list-group-item border-0 ps-0 pt-0 text-sm"> <strong class="text-dark">Nombre Completo: </strong>${user.name}</li>
        <li class="list-group-item border-0 ps-0 text-sm"> <strong class="text-dark">Email: </strong>${user.email}</li>
        <li class="list-group-item border-0 ps-0 text-sm"> <strong class="text-dark">Empresa: </strong>${user.company.name}</li>
        <li class="list-group-item border-0 ps-0 text-sm"> <strong class="text-dark">Equipo: </strong>${user.website}</li>
        <li class="list-group-item border-0 ps-0 pb-0"> <strong class="text-dark">Rol: </strong>${user.username}</li>
        `
    });

    document.getElementById("data").innerHTML = tableBody;

};

window.addEventListener("load", function (){
    listUsers();
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