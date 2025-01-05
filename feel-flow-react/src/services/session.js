// Guardar datos en localStorage
export const saveAuthData = (token, username) => {
    sessionStorage.setItem("token", token);
    sessionStorage.setItem("username", username);
};

// Obtener datos de localStorage
export const getAuthData = () => {
    const token = sessionStorage.getItem("token");
    const username = sessionStorage.getItem("username");
    return { token, username };
};

// Limpiar datos de localStorage (para logout)
export const clearAuthData = () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("username");
};

// En la consola del navegador
console.log("MOSTRANDO EL TOKEN e ID EN LOCALSTORAGE");
console.log(localStorage.getItem('token'));  // Debe mostrar el token almacenado
console.log(localStorage.getItem('userId'));

// En la consola del navegador
console.log("MOSTRANDO EL TOKEN e ID EN sessionStorage");
console.log(sessionStorage.getItem('token'));  // Debe mostrar el token almacenado
console.log(sessionStorage.getItem('username'));
