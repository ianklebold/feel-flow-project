import axios from 'react-axios';

export function login(user, pass) {
    let usernameInput = document.getElementById("email");
    let passwordInput = document.getElementById("password");
    let btn = document.getElementById("btn");

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
