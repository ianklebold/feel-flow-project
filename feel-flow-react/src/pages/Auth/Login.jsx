import { useState } from "react";
import { login } from "../../services/Auth/Login";
import TextInput from "../../components/TextInput";
import Button from "../../components/Button";

import { FaEye, FaEyeSlash } from "react-icons/fa";

import { saveAuthData } from "../../services/session";

function Login({ onLogin }) {
    const [user, setUsername] = useState("");
    const [pw, setPassword] = useState("");
    const [error, setError] = useState("");
    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!user || !pw) {
            setError("Por favor, completa todos los campos.");
            return;
        }

        try {
            const result = await login(user, pw);
            saveAuthData(result.token, result.username)
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

            <div className="mb-6 relative">
                <TextInput
                    label="Password"
                    labelClass="login"
                    type={showPassword ? "text" : "password"}
                    value={pw}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your password"
                    color="red"
                    icon={showPassword ? FaEyeSlash : FaEye} // Cambia el ícono según el estado
                    onIconClick={togglePasswordVisibility} // Función para mostrar/ocultar
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
