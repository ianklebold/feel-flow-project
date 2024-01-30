import { ObtenerEncuestas, ObtenerRespuestas, ObtenerPreguntas, crearModulo } from "./functions/post/twelve_steps.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

const encuesta = []

function Preguntas() {
    ObtenerPreguntas(token)
        .then(data => {
            var form = document.createElement('form');
            form.setAttribute('id', 'preguntasForm');

            for (var i = 0; i < data.length; i++) {
                var preguntaDiv = document.createElement('div'); // Nuevo div para la pregunta y sus respuestas
                preguntaDiv.setAttribute('class', 'pregunta-container'); // Clase para estilizar si es necesario

                var pregunta = document.createElement('p');
                pregunta.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');
                pregunta.textContent = data[i];
                pregunta.setAttribute("id", 'pregunta-' + i);


                preguntaDiv.appendChild(pregunta);
                form.appendChild(preguntaDiv);

                var divisor = document.getElementById("preguntas");
                divisor.insertAdjacentElement("beforeend", form);

                // console.log(data[i]);
            }

            return data.length;
        })
        .catch(error => {
            console.error("Error al obtener las preguntas: " + error);
            return 0;
        });
}


function Respuestas() {
    ObtenerRespuestas(token)
        .then(data => {
            for (var i = 0; i < data.length; i++) { // Recorre el grupo de respuestas que corresponde a la pregunta | de 1 a 12
                for (var j = 0; j < data[i].length; j++) { // Recorre cada respuesta de cada pregunta | de 1 a 5
                    var respuesta = document.createElement('label');

                    respuesta.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');

                    var checkbox = document.createElement('input');
                    checkbox.setAttribute('type', 'radio');
                    checkbox.setAttribute('name', 'respuestaRadio-' + i);
                    checkbox.setAttribute('value', data[i][j]);
                    if (encuesta[i] == j) {
                        console.log(data[i][j])
                        checkbox.setAttribute('checked', true)
                    }

                    respuesta.appendChild(checkbox);
                    respuesta.appendChild(document.createTextNode(data[i][j]));

                    var divisor = document.getElementById("pregunta-" + i.toString());
                    divisor.insertAdjacentElement("beforeend", respuesta);

                    // console.log(data[i][j]);
                }
            }
        })
        .catch(error => {
            console.error("Error al obtener las respuestas" + error);
            return 0;
        });
}

function toNumber(number) {
    let nro
    switch (number) {
        case 'I am Ian':
            nro = 0;
            break;
        case 'UNO':
            nro = 1;
            break;
        case 'DOS':
            nro = 2;
            break;
        case 'TRES':
            nro = 3;
            break;
        case 'CUATRO':
            nro = 4;
            break;
        case 'CINCO':
            nro = 5;
            break;
    }
    return nro
}

function EstadoEncuesta() {
    return ObtenerEncuestas(token)
        .then(data => {
            let activity = data[0].activityList;
            for (let i = 0; i < activity.length; i++) {
                encuesta.push(toNumber(data[0].activityList[i].answer));
            }
        })
        .catch(error => {
            console.error("Error al obtener las encuestas: " + error);
            throw error; // Lanza el error nuevamente para que sea manejado por el siguiente `catch`
        });
}

function MostrarRespuestas(respuestas) {

}

function MostrarPantalla() {
    EstadoEncuesta()
}

// function Preguntas() {
//     console.log("hola")
//     ObtenerPreguntas(token)
//         .then(data => {
//             //document.getElementById("preguntas").textContent = data
//             for (var i = 0; i < data.length; i++) {

//                 var pregunta = document.createElement('p');
//                 pregunta.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');
//                 pregunta.textContent = data[i];
//                 pregunta.setAttribute("id", i);

//                 var divisor = document.getElementById("preguntas");
//                 divisor.insertAdjacentElement("beforeend", pregunta);

//                 console.log(data[i])
//             }    
//             //console.log(data)
//             return data.length
//         })
//         .catch(error => {
//             console.error("Error al obtener las preguntas")
//             return 0
//         })
// }

// function Respuestas() {
//     console.log("hola")
//     ObtenerRespuestas(token)
//         .then(data => {

//             for (var i = 0; i < data.length; i++) {
//                 for (var j = 0; j < data[i].length; j++) {
//                     var respuesta = document.createElement('p');
//                     respuesta.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');
//                     respuesta.textContent = data[i][j];

//                     var divisor = document.getElementById(i.toString());
//                     divisor.insertAdjacentElement("beforeend", respuesta);

//                     console.log(data[i][j])
//                 }
//             }    
//         })
//         .catch(error => {
//             console.error("Error al obtener las respuestas")
//             return 0
//         })
// }

Preguntas(token);
Respuestas(token);
MostrarPantalla();