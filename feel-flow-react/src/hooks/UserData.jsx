import { getUser } from "../services/Users/GetUser";
import { getUserData } from "../services/session";
export const authUserData = async () => {
    try {
        await getUser();
    } catch (error) {
        console.error("Error al validar el token:", error);
    }
};