import { Helmet } from 'react-helmet';

function Home() {
    return (
        <div>
            <Helmet>
                <title>Home</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <h1 className="text-2xl font-bold">Home</h1>
                <p>¡Bienvenido al panel principal!</p>
            </div>
        </div>

    );
}

export default Home;
