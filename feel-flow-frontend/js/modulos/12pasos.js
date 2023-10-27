const endpoint = 'http://localhost:8080/api/v1/module/TWELVE_STEPS';

// Crear un objeto para la solicitud POST
const postData = {};

fetch(endpoint, {
  method: 'POST',
  body: JSON.stringify(postData),
  headers: {
    'Content-Type': 'application/json'
  }
})
  .then(response => {
    if (response.status === 201) {
      // Por si se crea con exito
      console.log('Módulo creado exitosamente.');
    } else if (response.status === 404) {
      // Aca algo sale mal
      console.log('No se encontró el equipo o el módulo.');
    } else if (response.status === 400) {
      // Aca si ya se creo otro codigo anteriormente
      console.log('Ya existe un módulo de 12 pasos activo para el equipo.');
    } else {
      // Aca si sale algo raro
      console.log('Otro error');
    }
  })
  .catch(error => {
    console.error('Error en la solicitud:', error);
  });
