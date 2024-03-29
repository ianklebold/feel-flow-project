// Libraries

// Components
import DefaultLayout from "../layout/components/DefaultLayout";

// Styles

export function Profile() {
    return (
        <DefaultLayout>
            <div className="perfil-container">
                {/* Banner */}
                <div className="banner">
                    <img src="../assets/image/profile/banner.jpg" alt="Banner" />
                </div>

                {/* Sección de foto de perfil, nombre y botón para editar */}
                <div className="perfil-info">
                    <div className="avatar">
                        <img src="./image/profile/profile.jpg" alt="Foto de perfil" />
                    </div>
                    <div className="user-info">
                        <h2>Nombre de Usuario</h2>
                        <button className="btn-editar">Editar Perfil</button>
                    </div>
                </div>

                {/* Sección de información del usuario */}
                <div className="user-data">
                    {/* Aquí puedes mostrar la información del usuario */}
                </div>
            </div>
        </DefaultLayout>
    )
}