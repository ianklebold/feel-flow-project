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
    '12 pasos de la Felicidad': 'TWELVE_STEPS',
    'Niko Niko': 'NIKO_NIKO',
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

// Mapeo inverso de los nombres para mostrar los valores seleccionados en el HTML
const reverseModuleMapping = {
    'TWELVE_STEPS': '12 pasos de la Felicidad',
    'NIKO_NIKO': 'Niko Niko'
};
// Mapeo inverso de los status para mostrar los valores seleccionados en el HTML
const reverseStateMapping = {
    'ACTIVE': 'Activo',
    'FINISHED': 'Terminado'
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
async function llenarTabla(moduleData) {
    // Verificar si se obtuvo la información de algún módulo
    if (moduleData !== null) {
        // Obtener el tbody de la tabla en el HTML
        const tableBody = document.getElementById('listado-modulos');

        // Crear una nueva fila (tr) para el módulo
        const newRow = document.createElement('tr');

        // Crear celdas (td) con la información del módulo
        const nameCell = document.createElement('td');
        if (moduleData.name in reverseModuleMapping) {
            nameCell.textContent = reverseModuleMapping[moduleData.name];
        } else {
            // Si no hay un mapeo inverso, simplemente usa el valor original
            nameCell.textContent = moduleData.name;
        }
        nameCell.classList.add('text-center');

        const moduleStateCell = document.createElement('td');
        if (moduleData.moduleState in reverseStateMapping) {
            moduleStateCell.textContent = reverseStateMapping[moduleData.moduleState];
        } else {
            // Si no hay un mapeo inverso, simplemente usa el valor original
            moduleStateCell.textContent = moduleData.moduleState;
        }
        moduleStateCell.classList.add('text-center');


        const finishedSurveysCell = document.createElement('td');
        const formattedData = `${moduleData.resumeModuleDto.totalOfSurveysReplied} / ${moduleData.resumeModuleDto.totalOfSurveys} (${moduleData.resumeModuleDto.percentOfSurveysReplied.toFixed(2)}%)`;
        finishedSurveysCell.textContent = formattedData;
        finishedSurveysCell.classList.add('text-center');

        const enableToCloseCell = document.createElement('td');
        if (moduleData.moduleState === 'FINISHED') {
            enableToCloseCell.textContent = 'Si';
        } else {
            enableToCloseCell.textContent = moduleData.enableToClose ? 'Si' : 'No';
        }
        enableToCloseCell.classList.add('text-center');
        

        const creationDateCell = document.createElement('td');
        // Obtener la fecha y formatearla
        const creationDate = new Date(moduleData.creationDate);
        creationDate.setHours(creationDate.getHours() + 3);
        const formattedDate = `${String(creationDate.getDate()).padStart(2, '0')}-${String(creationDate.getMonth() + 1).padStart(2, '0')}-${creationDate.getFullYear()}`;
        creationDateCell.textContent = formattedDate;
        creationDateCell.classList.add('text-center');

        const closeDateCell = document.createElement('td');
        // Obtener la fecha y formatearla
        const closeDate = new Date(moduleData.closeDate);
        closeDate.setHours(closeDate.getHours() + 3);
        const formattedDateClose = `${String(closeDate.getDate()).padStart(2, '0')}-${String(closeDate.getMonth() + 1).padStart(2, '0')}-${closeDate.getFullYear()}`;
        
        closeDateCell.classList.add('text-center');

        if (moduleData.moduleState !== 'FINISHED') {
            closeDateCell.textContent = '-';
        } else {
            closeDateCell.textContent = formattedDateClose;
        }

        // Agregar las celdas a la fila
        newRow.appendChild(nameCell);
        newRow.appendChild(moduleStateCell);
        newRow.appendChild(finishedSurveysCell);
        newRow.appendChild(enableToCloseCell);
        newRow.appendChild(creationDateCell);
        newRow.appendChild(closeDateCell);

        // Agregar la fila al tbody de la tabla
        tableBody.appendChild(newRow);
        console.info("Se inserto un elemento")
    }
}

// Agregar un evento de clic al botón
const busquedaModuloBtn = document.getElementById('busqueda_modulo');

function Filtrar() {
    ObtenerModulos(token, name_module, state, order)
        .then((result) => {
            //console.log(result)
            for (let i = 0; i < result.length; i++) {
                //console.log(result[i]);
                llenarTabla(result[i]);
            }
        }).catch((err) => {
            console.error(err);
        });
}

busquedaModuloBtn.addEventListener('click', function(){
    const tableBody = document.getElementById('listado-modulos');
    // Limpiar la tabla eliminando todas las filas existentes
    tableBody.innerHTML = '';

    // Llamar a la función para obtener los valores seleccionados
    obtenerValoresSeleccionados();
    Filtrar();
});

Filtrar();


