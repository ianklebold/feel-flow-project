import { useState } from "react";
import { login } from "../services/authService";
import FeelFlow from "../assets/img/FeelFlow.png";

function Login( {onLogin} ) {
    // Estados para usuario y contraseña
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    // Manejo del evento de submit
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validar campos
        if (!username || !password) {
            setError("Por favor, completa todos los campos.");
            return;
        }

        try {
            const response = await login(username, password);
            if (response.success) {
              onLogin(); // Notificar éxito
            }
          } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen">
            <div className="bg-bgPrimary p-8 rounded-3xl shadow-lg flex w-3/4 max-w-4xl">
                {/* Columna izquierda con la imagen */}
                <div className="w-1/2 flex flex-col items-center justify-center rounded-l-lg">
                    {/* <h1 className="text-3xl font-bold text-secondary">Feel Flow</h1> */}
                    <img
                        src={FeelFlow}
                        alt="Feel Flow Logo"
                        className="w-auto h-auto mb-4"
                    />
                </div>

                {/* Columna derecha con el formulario */}
                <div className="w-1/2 p-6 flex flex-col justify-center">
                    <h1 className="text-3xl font-bold text-secondary text-center">Feel Flow</h1>

                    {/* <h2 className="text-2xl font-bold text-secondary mb-6 text-center">
                        Bienvenido de nuevo
                    </h2> */}

                    <form onSubmit={handleSubmit}>
                        <div className="mb-4">
                            <label className="block text-textSecondary font-medium mb-1">
                                Usuario:
                            </label>
                            <input
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                placeholder="Ingresa tu usuario"
                                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>

                        <div className="mb-6">
                            <label className="block text-textSecondary font-medium mb-1">
                                Contraseña:
                            </label>
                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="Ingresa tu contraseña"
                                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>

                        {error && (
                            <p className="text-error text-sm mb-4">{error}</p>
                        )}

                        <button
                            type="submit"
                            className="w-full bg-blue-600 text-white py-2 rounded-lg font-medium hover:bg-blue-700 transition duration-300"
                        >
                            Iniciar sesión
                        </button>

                        <div className="text-center mt-4">
                            <a
                                href="#"
                                className="text-textPrimary text-sm hover:underline"
                            >
                                ¿Olvidaste tu Contraseña?
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default Login;
