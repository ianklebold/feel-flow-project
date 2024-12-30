import { Helmet } from 'react-helmet';

function Settings() {
    return (
        <div>
            <Helmet>
                <title>Settings</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <h1 className="text-2xl font-bold">Settings</h1>
                <p>¡Bienvenido al panel principal!</p>
            </div>
        </div>

    );
}

export default Settings;
