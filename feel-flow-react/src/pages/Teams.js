import { Helmet } from 'react-helmet';

function Teams() {
    return (
        <div>
            <Helmet>
                <title>Teams</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <h1 className="text-2xl font-bold">Teams</h1>
                <p>¡Bienvenido al panel principal!</p>
            </div>
        </div>

    );
}

export default Teams;
