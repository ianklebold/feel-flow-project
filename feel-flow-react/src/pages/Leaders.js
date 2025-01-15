import { Helmet } from 'react-helmet';

function Leaders() {
    return (
        <div>
            <Helmet>
                <title>Leaders</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <h1 className="text-2xl font-bold">Leaders</h1>
                <p>¡Bienvenido al panel principal!</p>
            </div>
        </div>

    );
}

export default Leaders;
