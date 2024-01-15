import { useState } from 'react'
import axios from 'axios'
import '../assets/css/Login.css'
import { Boton, InputText, } from '../layout/components/DefaultElements'
import DefaultLogin from '../layout/components/DefaulLogin';

export function Login() {
    const [data, setData] = useState({
        matches: window.matchMedia("(min-width: 768px)").matches,
        email: '',
        password: '',
        errorMessage: ''
    });

    const handleInputChange = (e) => {
        const value = e.target.value;
        setData({
            ...data,
            [e.target.name]: value
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(data)
        console.log(data.email)
        console.log(data.password)
        const datos = {
            username: data.email,
            password: data.password
        };

        try {
            const response = await axios.post('http://localhost:8080/login', datos);
            console.log(response.data);
        } catch (error) {
            console.error(error);
            setData({
                ...data,
                errorMessage: 'Error en la autenticación. Verifica tus credenciales.'
            });
        }
    }

    return (
        <DefaultLogin clase="Login" nombre="Iniciar sesion">
            <form className='Formulario' onSubmit={handleSubmit}>
                <hr />
                <>
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Usuario"
                        tipo="email"
                        name="email"
                        value={data.email}
                        onChange={handleInputChange}
                    />
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Contraseña"
                        tipo="password"
                        name="password"
                        value={data.password}
                        onChange={handleInputChange}
                    />
                    {data.errorMessage && <div className="error-message">{data.errorMessage}</div>}
                    <Boton clase="login" tipo="submit" nombre="INICIAR SESION" />
                </>
                <hr />
            </form>
        </DefaultLogin>

    )

}