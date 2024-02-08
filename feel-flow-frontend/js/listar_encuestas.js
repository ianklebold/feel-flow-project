import { GetIdEquipo } from "./functions/GetEquipos.js";
import { Logueado } from "./functions/User.js";
import { ObtenerModulos } from "./functions/get/get_modules.js";


const token = localStorage.getItem('Token');


// Variables globales para almacenar los valores seleccionados
let name_module;
let state;
let order;

const moduleMapping = {
    'Todos': null,
    '12 pasos de la Felicidad': 'TWELVE_STEPS'
};

// Mapeo de opciones de select-estado a valores específicos
const stateMapping = {
    'Todas': null,
    'Activas': 'ACTIVE',
    'Terminadas': 'FINISHED'
};

// Mapeo de opciones de select-order a valores específicos
const orderMapping = {
    'Por fecha ascendente': true,
    'Por fecha descendente': false
};

// Función para obtener los valores seleccionados de los select
function obtenerValoresSeleccionados() {
    const selectModulo = document.getElementById('select-modulo');
    const selectEstado = document.getElementById('select-estado');
    const selectOrder = document.getElementById('select-order');

    // Obtener los valores seleccionados utilizando los mapeos
    name_module = moduleMapping[selectModulo.value];
    state = stateMapping[selectEstado.value];
    order = orderMapping[selectOrder.value];
}

// Función para llenar la tabla con los módulos obtenidos
async function llenarTabla() {

    // Llamar a la función para obtener los valores seleccionados
    obtenerValoresSeleccionados();

    // Llamar a la función para obtener los módulos
    const moduleData = await ObtenerModulos(token, name_module, state);


    const tableBody = document.getElementById('listado-modulos');
    // Limpiar la tabla eliminando todas las filas existentes
    tableBody.innerHTML = '';
    // Verificar si se obtuvo la información de algún módulo
    if (moduleData !== null) {
        // Obtener el tbody de la tabla en el HTML
        const tableBody = document.getElementById('listado-modulos');

        // Crear una nueva fila (tr) para el módulo
        const newRow = document.createElement('tr');

        // Crear celdas (td) con la información del módulo
        const nameCell = document.createElement('td');
        nameCell.textContent = moduleData.name;  // Ajusta según tu estructura de datos

        const moduleStateCell = document.createElement('td');
        moduleStateCell.textContent = moduleData.moduleState;  // Ajusta según tu estructura de datos

        // Calcular la cantidad de "idSurvey" con "surveyState" igual a "FINISHED"
        const surveys = moduleData.surveyList;
        const finishedSurveys = surveys.filter(survey => survey.surveyState === 'FINISHED');
        const ratioFinishedSurveys = (finishedSurveys.length / surveys.length) * 100;

        const finishedSurveysCell = document.createElement('td');
        finishedSurveysCell.textContent = `${finishedSurveys.length} / ${surveys.length} (${ratioFinishedSurveys.toFixed(2)}%)`;

        const enableToCloseCell = document.createElement('td');
        enableToCloseCell.textContent = moduleData.enableToClose;

        // Agregar las celdas a la fila
        newRow.appendChild(nameCell);
        newRow.appendChild(moduleStateCell);
        newRow.appendChild(finishedSurveysCell);
        newRow.appendChild(enableToCloseCell);

        // Agregar la fila al tbody de la tabla
        tableBody.appendChild(newRow);
    }
}

// Agregar un evento de clic al botón
const busquedaModuloBtn = document.getElementById('busqueda_modulo');
busquedaModuloBtn.addEventListener('click', llenarTabla);