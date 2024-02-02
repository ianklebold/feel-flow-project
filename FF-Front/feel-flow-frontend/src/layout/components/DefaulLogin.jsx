import { children } from "react"
// import '../../assets/css/Login.css'

export default function DefaultLogin ({ children, clase, nombre }) {
    return (
        <div className={clase}>
            <div className='Header'>
                <h2 className='Titulo'>Feel Flow</h2> 
                <h2 className='Titulo'>{nombre}</h2>
            </div>

            <div className='Contenedor'>
                <img className='Logo' src="../src/assets/image/icons/FeelFlow.png" alt="Logo FeelFlow" />
                {children}
            </div>
        </div>
    )
}