import { ObtenerEncuestas, ObtenerRespuestas, ObtenerPreguntas, crearModulo } from "./functions/post/twelve_steps.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;


function Preguntas() {
    console.log("hola");
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

                console.log(data[i]);
            }

            return data.length;
        })
        .catch(error => {
            console.error("Error al obtener las preguntas");
            return 0;
        });
}


function Respuestas() {
    console.log("hola");
    ObtenerRespuestas(token)
        .then(data => {
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < data[i].length; j++) {
                    var respuesta = document.createElement('label');
                    respuesta.classList.add('ps-4', 'ms-2', 'my-4', 'text-uppercase', 'text-xs', 'font-weight-bolder', 'opacity-6');

                    var checkbox = document.createElement('input');
                    checkbox.setAttribute('type', 'radio');
                    checkbox.setAttribute('name', 'respuestaRadio-' + i);
                    checkbox.setAttribute('value', data[i][j]);

                    respuesta.appendChild(checkbox);
                    respuesta.appendChild(document.createTextNode(data[i][j]));

                    var divisor = document.getElementById("pregunta-" + i.toString());
                    divisor.insertAdjacentElement("beforeend", respuesta);

                    console.log(data[i][j]);
                }
            }
        })
        .catch(error => {
            console.error("Error al obtener las respuestas");
            return 0;
        });
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