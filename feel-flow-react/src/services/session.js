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
