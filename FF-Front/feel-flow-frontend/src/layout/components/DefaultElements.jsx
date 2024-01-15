import '../css/Input.css'
import '../css/Button.css'
import '../css/CheckBox.css'

export function InputText({ claseInput, claseLabel, label, tipo, name, value, onChange }) {
    return (
        <div className='conteiner'>
            <input
                className={claseInput}
                type={tipo}
                id={name}
                placeholder=''
                name={name}
                value={value}
                onChange={onChange}
            />
            <label className={claseLabel} htmlFor={tipo}>{label}</label>
        </div>
    )
}

export function Boton({ clase, tipo, nombre }) {
    return (
        <button className={clase} type={tipo}>{nombre}</button>
    )
}

export function Check({ children, checkClase, inputClase, labelClase  }) {
    return (
        <div className={checkClase}>
            <input className="checkTerminos" type="checkbox" id="flexCheckDefault" required="" />
                <label className="labelCheckTerminos" htmlFor="flexCheckDefault" id="label-check"> 
                    <a href="">
                        {children}
                    </a>
                </label>
        </div>
    )
}