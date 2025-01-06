import React from "react";
import { Helmet } from "react-helmet";
import Modulo from "../components/moduloAgil";


// Imagenes
import headerImage12pasos from "../img/twelve_steps_header.png";
import bodyImage12pasos from "../img/twelve_steps_body.png";
import footerImage12pasos from "../img/twelve_steps_footer.png";
import contentImage12pasos from "../img/twelve_steps-removebg.png";

import headerImageNikoNiko from "../img/niko_niko_header.png";
import bodyImageNikoNiko from "../img/niko_niko_body.png";
import footerImageNikoNiko from "../img/niko_niko_footer.png";
import contentImageNikoNiko from "../img/niko_niko_content.png";

import headerImageKudos from "../img/kudos_header.png";
import bodyImageKudos from "../img/kudos_body.png";
import footerImageKudos from "../img/kudos_footer.png";
import contentImageKudos from "../img/kudos_content.png";


function Modules() {
    const handleHabilitarEncuesta = () => {
        console.log("Enviar Encuesta");
    };
    const handleComenzarNikoNiko = () => {
        console.log("Enviar Encuesta");
    };

    const handleTerminarNikoNiko = () => {
        console.log("Contestar Encuesta");
    };
    const handleComenzarKudos = () => {
        console.log("Enviar Encuesta");
    };

    const handleTerminarKudos = () => {
        console.log("Contestar Encuesta");
    };

    return (
        <div>
            <Helmet>
                <title>Modules</title>
            </Helmet>

            <h1 className="text-3xl font-bold text-center my-2">Módulos Ágiles</h1>
            {/* Módulo 12 pasos */}
            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="12 PASOS DE LA FELICIDAD"
                imagenHeader={headerImage12pasos}
                imagenBody={bodyImage12pasos}
                imagenFooter={footerImage12pasos}
                imagenContent={contentImage12pasos}
                descripcionModulo="El objetivo de esta práctica es reflexionar sobre diferentes aspectos relacionados con la felicidad, cómo los incorporamos en nuestro día a día y poder generar acciones que impulsen nuestra felicidad."
                botones={[
                    { texto: "Habilitar Encuesta", onClick: handleHabilitarEncuesta, color: "light_pink" },
                ]}
            />

            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="NIKO NIKO"
                imagenHeader={headerImageNikoNiko}
                imagenBody={bodyImageNikoNiko}
                imagenFooter={footerImageNikoNiko}
                imagenContent={contentImageNikoNiko}
                descripcionModulo="El objetivo de esta práctica es reflexionar sobre diferentes aspectos relacionados con la felicidad, cómo los incorporamos en nuestro día a día y poder generar acciones que impulsen nuestra felicidad."
                citaModulo="La felicidad es algo que creamos, no es algo que hay que lograr."
                botones={[
                    { texto: "Comenzar", onClick: handleComenzarNikoNiko, color: "light_blue"},
                    { texto: "Terminar", onClick: handleTerminarNikoNiko, color: "light_blue" },
                ]}
            />

            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="KUDOS"
                imagenHeader={headerImageKudos}
                imagenBody={bodyImageKudos}
                imagenFooter={footerImageKudos}
                imagenContent={contentImageKudos}
                descripcionModulo="El objetivo de esta práctica es reflexionar sobre diferentes aspectos relacionados con la felicidad, cómo los incorporamos en nuestro día a día y poder generar acciones que impulsen nuestra felicidad."
                citaModulo="La felicidad es algo que creamos, no es algo que hay que lograr."
                botones={[
                    { texto: "Comenzar", onClick: handleComenzarKudos, color: "light_purple" },
                    { texto: "Terminar", onClick: handleTerminarKudos, color: "light_purple" },
                ]}
            />


        </div>
    );
}

export default Modules;