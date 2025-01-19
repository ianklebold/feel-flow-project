import React, { useState } from "react";
import TextInput from "../../components/TextInput";
import Button from "../../components/Button";
import Checkbox from "../../components/checkbox";
import Popup from "../../components/Popup";
import { Sign_up } from "../../services/Auth/Signup";
import { useNavigate } from "react-router-dom";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { FaCheckCircle } from "react-icons/fa";

const RegistroFormulario = () => {
    const [formData, setFormData] = useState({
        name: "",
        lastName: "",
        email: "",
        password: "",
        company: "",
        terms: false,
    });
    const [errors, setErrors] = useState({});
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const navigate = useNavigate();


    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData({
            ...formData,
            [name]: type === "checkbox" ? checked : value,
        });

        // Elimina el error al cambiar el campo
        if (errors[name]) {
            setErrors({ ...errors, [name]: null });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.terms) {
            alert("Debes aceptar los términos y condiciones.");
            return;
        }

        const result = await Sign_up(
            formData.name,
            formData.lastName,
            formData.email,
            formData.password,
            formData.company
        );

        if (result.errors) {
            const formattedErrors = result.errors.reduce((acc, curr) => {
                const key = Object.keys(curr)[0];
                acc[key] = curr[key];
                return acc;
            }, {});
            setErrors(formattedErrors);
        } else {
            setIsPopupOpen(true);
        }
    };

    const closePopup = () => {
        setIsPopupOpen(false);
        navigate("/login"); // Redirigir al login
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <TextInput
                        label="Name"
                        labelClass="login"
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        placeholder="Enter your name"
                        color="blue"
                        error={errors.name}
                    />
                </div>
                <div className="mb-4">
                    <TextInput
                        label="Last name"
                        labelClass="login"
                        type="text"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                        placeholder="Enter your last name"
                        color="blue"
                        error={errors.surname}
                    />
                </div>
                <div className="mb-4">
                    <TextInput
                        label="Username"
                        labelClass="login"
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        placeholder="Enter your email"
                        color="blue"
                        error={errors.username}
                    />
                </div>
                <div className="mb-4">
                    <TextInput
                        label="Password"
                        labelClass="login"
                        type={showPassword ? "text" : "password"}
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        placeholder="Enter your password"
                        color="blue"
                        error={errors.password}
                        icon={showPassword ? FaEyeSlash : FaEye} // Cambia el ícono según el estado
                        onIconClick={togglePasswordVisibility} // Función para mostrar/ocultar
                    />
                </div>
                {/* <div className="mb-4">
                <TextInput
                    label="Confirm Password"
                    labelClass="login"
                    type="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Enter your password"
                    color="blue"
                />
            </div> */}
                <div className="mb-4">
                    <TextInput
                        label="Company"
                        labelClass="login"
                        type="text"
                        name="company"
                        value={formData.company}
                        onChange={handleChange}
                        placeholder="Enter your company"
                        color="blue"
                        error={errors["enterpriseDTO.name"]}
                    />
                </div>

                <hr className="border-gray-300" />

                <div className="text-left mt-2">
                    <Checkbox
                        id="terms"
                        name="terms"
                        label="Acepto los términos y condiciones"
                        checked={formData.terms}
                        onChange={handleChange}
                        className="mb-4"
                    />
                </div>
                <Button label="Register" type="submit" color="blue" variant="solid" className="w-full" />

                {/* 
            <div className="text-center">
                <button
                    type="submit"
                    className="btn bg-indigo-600 text-white w-full py-2 px-4 rounded-md hover:bg-indigo-700"
                >
                    Registrar
                </button>
            </div>
            */}
                <p className="text-sm text-center mt-3">
                    ¿Ya tienes una cuenta?{" "}
                    <a
                        href="/login"
                        className="text-indigo-600 font-semibold hover:underline"
                    >
                        Ingresa
                    </a>
                </p>
            </form>
            <Popup
                isOpen={isPopupOpen}
                icon={<FaCheckCircle className="text-green-400 text-5xl mr-4" />}
                title="¡Usuario administrador creado!"
                message="Tu cuenta ha sido creada correctamente. Ahora puedes iniciar sesión."
                buttons={[
                    {
                        label: "Ir al Login",
                        color: "blue",
                        size: "md",
                        onClick: closePopup,
                    },
                ]}
            />
        </>
    );
};

export default RegistroFormulario;
