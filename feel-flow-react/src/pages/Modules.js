import { Helmet } from 'react-helmet';

function Modules() {
    return (
        <div>
            <Helmet>
                <title>Modules</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <h1 className="text-2xl font-bold">Modules</h1>
                <p>¡Bienvenido al panel principal!</p>
            </div>
        </div>

    );
}

export default Modules;
