import { ObtenerEncuestas, ObtenerRespuestas, ObtenerPreguntas, crearModulo } from "./functions/post/twelve_steps.js";

const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

const partesToken = token.split('.');
const payloadDecodificado = atob(partesToken[1]);
const payloadObjeto = JSON.parse(payloadDecodificado);

const autoridad = payloadObjeto.authorities;
const autoridad_rol = JSON.parse(autoridad);
const rol = autoridad_rol[0].authority;

const answer_saved = []
const encuestas = []
const respuestas = {
    activities: [],
    surveyState: "ACTIVE"
}

function Preguntas() {
    ObtenerPreguntas(token)
        .then(data => {
            for (var i = data.length - 1; i >= 0; i--) {
                var preguntaDiv = document.createElement('div'); // Nuevo div para la pregunta y sus respuestas
                preguntaDiv.setAttribute('class', 'pregunta-container'); // Clase para estilizar si es necesario

                var pregunta = document.createElement('p');
                pregunta.classList.add('ps-4', 'ms-2', 'my-4', 'text-s', 'font-weight-bolder');
                pregunta.textContent = data[i];
                pregunta.setAttribute("id", 'pregunta-' + i);


                preguntaDiv.appendChild(pregunta);

                var divisor = document.getElementById("preguntasForm");
                divisor.insertAdjacentElement("afterbegin", preguntaDiv);

                
            }
            // var divisor = document.getElementById("preguntas");
            // divisor.insertAdjacentElement("beforeend", form);

            return data.length;
        })
        .catch(error => {
            console.error("Error al obtener las preguntas: " + error);
            return 0;
        });
}

function CargarDatos(question, answer) {
    let pregunta = {
        question: question,
        answer: answer
    }
    encuestas.push(pregunta)
}

function Respuestas() {
    ObtenerRespuestas(token)
        .then(data => {
            for (var i = 0; i < data.length; i++) { // Recorre el grupo de respuestas que corresponde a la pregunta | de 1 a 12
                for (var j = data[i].length - 1; j >= 0; j--) { // Recorre cada respuesta de cada pregunta | de 1 a 5
                    var respuesta = document.createElement('label');

                    respuesta.classList.add('ps-4', 'ms-2', 'my-4', 'text-s');

                    var checkbox = document.createElement('input');
                    checkbox.setAttribute('type', 'radio');
                    checkbox.setAttribute('name', 'respuestaRadio-' + i);
                    checkbox.setAttribute('value', data[i][j]);
                    checkbox.id = "Respuesta " + i + j
                    if (answer_saved[i] == j) {
                        console.log(data[i][j])
                        checkbox.setAttribute('checked', true)
                    }

                    respuesta.appendChild(checkbox);
                    respuesta.appendChild(document.createTextNode(data[i][j]));

                    var divisor = document.getElementById("pregunta-" + i.toString());
                    // divisor.insertAdjacentElement("beforeend", respuesta);
                    divisor.insertAdjacentElement("afterend", respuesta);

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

function toString(number) {
    let str
    switch (number) {
        case 5:
            str = "I am Ian";
            break;
        case 0:
            str = "UNO";
            break;
        case 1:
            str = "DOS";
            break;
        case 2:
            str = "TRES";
            break;
        case 3:
            str = "CUATRO";
            break;
        case 4:
            str = "CINCO";
            break;
    }
    return str
}

function EstadoEncuesta() { // Obtengo un array con las preguntas que ya están contestadas
    return ObtenerEncuestas(token)
        .then(data => {
            let activity = data[0].activityList;
            for (let i = 0; i < activity.length; i++) {
                answer_saved.push(toNumber(data[0].activityList[i].answer));
            }
        })
        .catch(error => {
            console.error("Error al obtener las encuestas: " + error);
            throw error; // Lanza el error nuevamente para que sea manejado por el siguiente `catch`
        });
}

function ObtenerDatos() {
    ObtenerPreguntas(token)
        .then(data => {
            for (var i = data.length - 1; i >= 0; i--) {
                for (var i = 0; i < 12; i++) { // Recorre el grupo de respuestas que corresponde a la pregunta | de 1 a 12
                    let nro_rta = 5;
                    let rta = toString(nro_rta);
                    for (var j = 0; j < 4; j++) { // Recorre cada respuesta de cada pregunta | de 1 a 5
                        let id = "Respuesta " + i + j;
                        let check = document.getElementById(id).checked;
                        console.log(check);
                        if (check) {
                            // nro_rta = j;
                            rta = toString(j);
                            break;
                        }
                    }
                    CargarDatos(data[i], rta);
                }

                
            }
        })
        .catch(error => {
            console.error("Error al obtener las preguntas: " + error);
        });
}

function MostrarPantalla() {
    EstadoEncuesta()
    
}

document.getElementById("enviarButton").addEventListener("click", function(event) {
    event.preventDefault();
    ObtenerDatos();
    console.log(encuestas);
    
})

Preguntas(token);
Respuestas(token);
MostrarPantalla();