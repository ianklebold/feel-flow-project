import React, { useState } from "react";
import TextInput from "../../components/TextInput";
import Button from "../../components/Button";
import Checkbox from "../../components/checkbox";
import { Sign_up } from "../../services/Auth/Signup";

const RegistroFormulario = () => {
    const [formData, setFormData] = useState({
        name: "",
        lastName: "",
        email: "",
        password: "",
        company: "",
        terms: false,
    });


    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData({
            ...formData,
            [name]: type === "checkbox" ? checked : value,
        });

    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Datos del formulario:", formData);

        if (formData.terms) {
            // console.log("Datos del formulario:", formData);
            const result = Sign_up(formData.name, formData.lastName, formData.email, formData.password, formData.company);
            console.log(result);
            // Aquí puedes enviar los datos al backend o realizar otras acciones.
        } else {
            alert("Debes aceptar los términos y condiciones.");
        }
    };

    return (
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
                />
            </div>
            <div className="mb-4">
                <TextInput
                    label="Password"
                    labelClass="login"
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Enter your password"
                    color="blue"
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
                    href="sign_in.html"
                    className="text-indigo-600 font-semibold hover:underline"
                >
                    Ingresa
                </a>
            </p>
        </form>
    );
};

export default RegistroFormulario;
