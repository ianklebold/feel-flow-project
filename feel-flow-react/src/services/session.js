import { getUser } from "./Users/GetUser";

const saveUserData = (token) => {
    const payload = token.split('.')[1];
    const decodedPayload = JSON.parse(atob(payload));

    sessionStorage.setItem("userID", decodedPayload.id);
    sessionStorage.setItem("isAdmin", decodedPayload.isAdmin);
    sessionStorage.setItem("authority", JSON.parse(decodedPayload.authorities)[0].authority);
}

export const saveAuthData = (token, username) => {
    sessionStorage.setItem("token", token);
    sessionStorage.setItem("username", username);
    saveUserData(token);
};

export const getAuthData = () => {
    const token = sessionStorage.getItem("token");
    // const username = sessionStorage.getItem("username");
    return { token };
};

export const getUserData = () => {
    const username = sessionStorage.getItem("username");
    const userID = sessionStorage.getItem("userID");
    const isAdmin = sessionStorage.getItem("isAdmin");
    const authority = sessionStorage.getItem("authority");
    return { username, userID, isAdmin, authority }
}

export const clearAuthData = () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("username");
};

export const validateToken = async () => {
    const { userID } = await getUserData();
    try {
        const session = await getUser(userID);

        if (session === "El token JWT no es valido") {
            clearAuthData()
        }

    } catch (error) {
        console.error("Error en sessionTimeLife:", error);
    }
};