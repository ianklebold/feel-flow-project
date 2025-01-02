import { useState } from "react";
import { login } from "../services/Auth/Login";
// import { login } from "../services/authService";
import TextInput from "./TextInput";
import Button from "./Button";

function Login( {onLogin} ) {
    // Estados para usuario y contraseña
    const [user, setUsername] = useState("");
    const [pw, setPassword] = useState("");
    const [error, setError] = useState("");

    // Manejo del evento de submit
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validar campos
        if (!user || !pw) {
            setError("Por favor, completa todos los campos.");
            return;
        }

        try {
            const result = await login(user, pw);
            onLogin();
            // Almacenar el token o realizar otra acción
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
                    color="read"
                />
            </div>

            {error && (
                <p className="text-error text-sm mb-4">{error}</p>
            )}
            <Button label="Login" type="submit" color="blue" variant="solid" className="w-full" />

            <div className="text-center mt-4">
                <a
                    href="#"
                    className="text-textPrimary text-sm hover:underline"
                >
                    ¿Olvidaste tu Contraseña?
                </a>
            </div>
        </form>
    );

}

export default Login;
