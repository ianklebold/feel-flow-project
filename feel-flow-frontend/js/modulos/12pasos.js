const idLocation = localStorage.getItem('idLocation');
const token = localStorage.getItem('Token');

// Función para crear un módulo
function crearModulo() {
  // Verificar si el usuario es Team Leader o Admin
  GetUser(idLocation, token)
    .then(data => {
      const payloadObjeto = JSON.parse(data);
      if (payloadObjeto.isTeamLeader || payloadObjeto.isAdmin) {
        // El usuario es Team Leader o Admin, puede crear el módulo
        const nombreModulo = 'TWELVE_STEPS'; // Reemplaza con el nombre del módulo
        const moduloEndpoint = `http://localhost:8080/api/v1/module/${nombreModulo}`;

        // Realiza la solicitud POST para crear el módulo
        fetch(moduloEndpoint, {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })
          .then(response => {
            if (response.status === 201) {
              // Módulo creado con éxito
              console.log('Módulo creado con éxito.');
            } else if (response.status === 400) {
              // Ya existe un módulo activo para el equipo
              console.error('Ya existe un módulo activo para el equipo.');
            } else {
              // Otro tipo de error
              console.error('Error al crear el módulo:', response.status);
            }
          })
          .catch(error => {
            console.error('Error al realizar la solicitud:', error);
          });
      } else {
        // Acceso denegado si no es Team Leader ni Admin
        console.log("Acceso denegado para crear el módulo.");
      }
    })
    .catch(error => {
      console.error(error);
    });
}

// Llama a la función para crear el módulo cuando sea necesario
document.getElementById('crearModuloButton').addEventListener('click', function () {
  crearModulo();
});
