import FeelFlow from '../assets/img/FeelFlow.png'

function Auth({ children, onLogin }) {

    return (
        <div className="flex items-center justify-center min-h-screen">
            <div className="bg-bgPrimary p-8 rounded-3xl shadow-lg flex w-3/4 max-w-4xl">
                {/* Columna izquierda con la imagen */}
                <div className="w-1/2 flex flex-col items-center justify-center rounded-l-lg">
                    <img
                        src={FeelFlow}
                        alt="Feel Flow Logo"
                        className="w-auto h-auto mb-4"
                    />
                </div>

                {/* Columna derecha con el formulario */}
                <div className="w-1/2 p-6 flex flex-col justify-center">
                    <h1 className="text-3xl font-bold text-secondary text-center">Feel Flow</h1>
                    {children}
                </div>
            </div>
        </div>
    );
}

export default Auth