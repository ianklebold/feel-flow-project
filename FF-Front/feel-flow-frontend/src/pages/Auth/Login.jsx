import axios from 'axios'

export function IniciarSesion(user, pass) {
    const usernameInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const btn = document.getElementById("btn");

    var url = "http://localhost:8080/login"
    const data = {
        username: usernameInput,
        password: passwordInput
    };
    axios.post(url, data)
        .then(response => {
            if (response.status === 200) {
                console.log(response.data);
            }
        })
        .catch(error => {
            console.error(error);
        });
}