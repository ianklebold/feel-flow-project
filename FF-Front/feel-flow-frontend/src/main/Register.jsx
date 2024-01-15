import { useState } from 'react'
import axios from 'axios'
import { Boton, InputText, Check } from '../layout/components/DefaultElements'
import '../assets/css/Register.css'
import DefaultLogin from '../layout/components/DefaulLogin';

export function Register() {
    const [data, setData] = useState({
        Nombre: '',
        Apellido: '',
        Email: '',
        Contraseña: '',
        Empresa: '',
        Terminos: false,
        name: '',
        surname: '',
        username: '',
        password: '',
        enteroriseDTOName: ''
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
        try {
            console.log(this.state)
            const response = await axios.post('http://localhost:8080/login', {
                name: this.state.Nombre,
                surname: this.state.Apellido,
                username: this.state.Email,
                password: this.state.password,
                enteroriseDTO: this.state.Empresa
            });
            console.log('Respuesta del servidor:', response.data);
            // Aquí puedes manejar la respuesta del servidor, por ejemplo, actualizando el estado de tu componente
        } catch (error) {
            if (error.response && error.response.status === 400) {
                switch (error.response.data) { // Actualizar el estado con el mensaje de error
                    case (error.response.data.name):
                        this.setState({ name: error.response.data.name.message });
                    case (error.response.data.surname):
                        this.setState({ surname: error.response.data.surname.message });
                    case (error.response.data.username):
                        this.setState({ username: error.response.data.username.message });
                    case (error.response.data.password):
                        this.setState({ password: error.response.data.password.message });
                    case (error.response.data.enteroriseDTO.name):
                        this.setState({ enteroriseDTO: error.response.data.enteroriseDTO.name.message });
                }
            } else {
                console.error('Error al realizar la petición:', error);
            }
        }
    }

    return (
        <DefaultLogin clase="Register" nombre="Registrarse">
            <form className='Formulario' onSubmit={handleSubmit}>
                <hr />
                <>
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Nombre"
                        tipo="text"
                        name="Nombre"
                        value={data.Nombre}
                        onChange={handleInputChange}
                    />
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Apellido"
                        tipo="text"
                        name="Apellido"
                        value={data.Apellido}
                        onChange={handleInputChange}
                    />
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Email"
                        tipo="email"
                        name="Email"
                        value={data.Email}
                        onChange={handleInputChange}
                    />
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Contraseña"
                        tipo="password"
                        name="Contraseña"
                        value={data.Contraseña}
                        onChange={handleInputChange}
                    />
                    <InputText
                        claseInput="InputLogin"
                        claseLabel="LabelLogin"
                        label="Empresa"
                        tipo="text"
                        name="Empresa"
                        value={data.Empresa}
                        onChange={handleInputChange}
                    />
                    <Check checkClase="checkBox">
                        <span>Acepto los terminos y condiciones</span>
                    </Check>
                    <hr />
                    {data.errorMessage && <div className="error-message">{data.errorMessage}</div>}
                    <Boton clase="login" tipo="submit" nombre="REGISTRAR" />
                </>
            </form>
        </DefaultLogin>
    )

}