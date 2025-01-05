import { useState } from "react";
import { login } from "../../services/Auth/Login";
import TextInput from "../../components/TextInput";
import Button from "../../components/Button";

// Decodificar el token JWT sin verificar la firma
const decodeJWT = (token) => {
    try {
        const payload = token.split(".")[1]; // El segundo segmento del token es el payload
        const decodedPayload = JSON.parse(atob(payload)); // Decodificar desde Base64
        return decodedPayload;
    } catch (error) {
        console.error("Error al decodificar el token:", error);
        throw new Error("Token inválido.");
    }
};

function Login({ onLogin }) {
    const [user, setUsername] = useState("");
    const [pw, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!user || !pw) {
            setError("Por favor, completa todos los campos.");
            return;
        }

        try {
            const result = await login(user, pw);

            // Guardar el token y otros datos en localStorage
            localStorage.setItem("authToken", result.token);
            localStorage.setItem("user", result.username);

            // Decodificar el token para extraer información adicional
            const decodedToken = decodeJWT(result.token);
            if (decodedToken) {
                // Guardar ID de usuario
                if (decodedToken.id) {
                    localStorage.setItem("userId", decodedToken.id);
                    console.log("ID almacenado:", decodedToken.id);
                }

                // Guardar rol del usuario
                const roles = decodedToken.authorities
                    ? JSON.parse(decodedToken.authorities)
                    : [];
                const role = roles.length > 0 ? roles[0].authority : "USER"; // Default a USER si no hay rol definido
                localStorage.setItem("userRole", role);
                console.log("Rol almacenado:", role);

                // Guardar si es admin
                if (decodedToken.isAdmin !== undefined) {
                    localStorage.setItem("isAdmin", decodedToken.isAdmin);
                    console.log("Es Admin:", decodedToken.isAdmin);
                }
            }

            console.log("Token almacenado:", result.token);
            console.log("Username almacenado:", result.username);

            // Llamar a la función de login del padre
            onLogin();
        } catch (error) {
            console.error("Error de inicio de sesión:", error.message);
            setError("Username o Password Incorrectos");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="mb-4">
                <TextInput
                    label="Username"
                    labelClass="login"
                    value={user}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter your username"
                    color="blue"
                />
            </div>

            <div className="mb-6">
                <TextInput
                    label="Password"
                    labelClass="login"
                    type="password"
                    value={pw}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your password"
                    color="red"
                />
            </div>

            {error && <p className="text-error text-sm mb-4">{error}</p>}
            <Button
                label="Login"
                type="submit"
                color="blue"
                variant="solid"
                className="w-full"
            />

            <div className="text-center mt-4">
                <a
                    href="#"
                    className="text-textPrimary text-sm hover:underline"
                >
                    ¿Olvidaste tu Contraseña?
                </a>
            </div>
            <div className="text-center mt-4">
                <p className="text-textPrimary text-sm">
                    ¿Aun no tienes cuenta?{" "}
                    <a
                        href="/register"
                        className="text-indigo-600 font-semibold hover:underline"
                    >
                        Registrate
                    </a>
                </p>
            </div>
        </form>
    );
}

export default Login;
