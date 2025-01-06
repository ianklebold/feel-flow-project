import React from "react";
import { Helmet } from "react-helmet";
import Modulo from "../components/moduloAgil";


// Imagenes
import headerImage1 from "../img/twelve_steps_header.png";
import bodyImage1 from "../img/twelve_steps_body.png";
import footerImage1 from "../img/twelve_steps_footer.png";
import contentImage1 from "../img/twelve_steps-removebg.png";

function Modules() {
    const handleEnviarEncuesta = () => {
        console.log("Enviar Encuesta");
    };

    const handleContestarEncuesta = () => {
        console.log("Contestar Encuesta");
    };

    return (
        <div>
            <Helmet>
                <title>Modules</title>
            </Helmet>
            
            <h1 className="text-3xl font-bold text-center my-8">Módulos Ágiles</h1>
             {/* Módulo 12 pasos */}
             <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="12 Pasos de la Felicidad"
                imagenHeader={headerImage1}
                imagenBody={bodyImage1}
                imagenFooter={footerImage1}
                imagenContent={contentImage1} 
                descripcionModulo="El objetivo de esta práctica es reflexionar sobre diferentes aspectos relacionados con la felicidad, cómo los incorporamos en nuestro día a día y poder generar acciones que impulsen nuestra felicidad."
                botones={[
                    { texto: "Enviar Encuesta", onClick: handleEnviarEncuesta },
                    { texto: "Contestar Encuesta", onClick: handleContestarEncuesta },
                ]}
            />

            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="Kudos"
                imagenHeader={headerImage1}
                imagenBody={bodyImage1}
                imagenFooter={footerImage1}
                imagenContent={contentImage1} 
                descripcionModulo="El objetivo de esta práctica es reflexionar sobre diferentes aspectos relacionados con la felicidad, cómo los incorporamos en nuestro día a día y poder generar acciones que impulsen nuestra felicidad."
                citaModulo="La felicidad es algo que creamos, no es algo que hay que lograr."
                botones={[
                    { texto: "Enviar Encuesta", onClick: handleEnviarEncuesta },
                    { texto: "Contestar Encuesta", onClick: handleContestarEncuesta },
                ]}
            />

        </div>
    );
}

export default Modules;