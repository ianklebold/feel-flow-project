import React from "react";

const Modulo = ({
    mostrarSinEncuestas = false,
    sinEncuestasMensaje = "En este momento no dispone de encuestas disponibles",
    tituloModulo = "",
    imagenHeader = "",
    imagenBody = "",
    imagenFooter = "",
    descripcionModulo = "",
    citaModulo = "",
    botones = [],
    imagenContent = "", // Nueva imagen para colocar encima de la imagen del cuerpo
}) => {
    return (
        <div className="max-h-screen overflow-hidden"> {/* Limitar el tamaño del contenedor principal */}
            {/* Mensaje Sin Encuestas */}
            {mostrarSinEncuestas && (
                <div className="text-center text-gray-700 p-4">{sinEncuestasMensaje}</div>
            )}

            {/* Modulo de Encuestas */}
            {!mostrarSinEncuestas && (
                <div className="bg-white shadow-md rounded-lg overflow-hidden max-h-full">
                    {/* Contenedor para permitir hover sobre el header */}
                    <div className="group">
                        {/* Encabezado */}
                        {tituloModulo && (
                            <div className="relative bg-blue-500 text-white p-4">
                                {imagenHeader && (
                                    <img
                                        src={imagenHeader}
                                        alt="Encabezado"
                                        className="absolute inset-0 w-full h-full object-cover"
                                    />
                                )}
                                <h3 className="text-lg font-semibold relative z-10">{tituloModulo}</h3>
                            </div>
                        )}

                        {/* Contenido que se despliega */}
                        <div className="group-hover:block hidden overflow-hidden transition-all duration-500 ease-in-out">
                            <div className="relative flex flex-col items-center text-white w-full">
                                {imagenBody && (
                                    <div className="relative w-full max-h-[calc(80vh-70px)] mb-0"> 
                                        <img
                                            src={imagenBody}
                                            alt="Cuerpo"
                                            className="w-full h-full object-cover filter blur-sm m-0 p-0"
                                        />
                                        {imagenContent && (
                                            <img
                                                src={imagenContent}
                                                alt="Content"
                                                className="absolute inset-0 w-full h-full object-contain z-10 m-0 p-0"
                                            />

                                        )}
                                    </div>
                                        
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Modulo;
