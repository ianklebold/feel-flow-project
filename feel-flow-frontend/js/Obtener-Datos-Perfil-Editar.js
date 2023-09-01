const listUsers = async (email) => {
    const response = await fetch('https://jsonplaceholder.typicode.com/users');
    const users = await response.json();

    let name = ``;
    let mail = ``;
    let empresa = ``;
    let Nombre = ``;
    let Rol = ``;
    let Email = ``;
    let Empresa = ``;
    let Equipo = ``;
    let arreglo = ``;
    let Apellido = ``;
    let Nombre2 = ``;
    /*let Descripcion = ``;*/
    users.forEach((user) => {
        if (user.email === email) {
            Nombre2 += `${user.name}`
            Rol += `${user.username}`
            Email += `${user.email}`
            Empresa += `${user.company.name}`
            Equipo += `${user.website}`

            /*Descripcion += `Hola`*/
            arreglo = Nombre2.split(" ");
            Nombre = arreglo[0];
            Apellido = arreglo[1];

            name += `
                <input type="email" class="form-control" id="Nombre" placeholder="Nombre" required value="${Nombre}">
                <label for="floatingInputGroup1">Nombre</label>
            `
            apellido = `
                <input type="email" class="form-control" id="Nombre" placeholder="Apellido" required value="${Apellido}">
                <label for="floatingInputGroup1">Apellido</label>
            `
            mail += `
                <input type="email" class="form-control" id="Nombre" placeholder="Nombre" required value="${Email}">
                <label for="floatingInputGroup1">Email</label>
            `
            empresa += `
                <input type="email" class="form-control" id="Nombre" placeholder="Nombre" required value="${Empresa}">
                <label for="floatingInputGroup1">Empresa</label>
            `
        }

    });
    document.getElementById("name").innerHTML = Nombre2;
    document.getElementById("rol").innerHTML = Rol;
    document.getElementById("Nombre").innerHTML = name;
    document.getElementById("Apellido").innerHTML = apellido;
    document.getElementById("Email").innerHTML = mail;
    document.getElementById("Empresa").innerHTML = empresa;

    document.getElementById("edit-form").addEventListener("submit", function (event) {
        /*Falta controlar si se modificaron datos y enviarlos a algun lado*/
        event.preventDefault();
        window.location.href = "../pages/profile.html";
    });

};

window.addEventListener("load", function () {
    listUsers("Sincere@april.biz");
    /*Aca deberiamos ir a buscar el mail del user en alguna parte que no tengo ni puta idea donde es*/
})

