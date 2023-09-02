document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.getElementById('registro-formulario');
  
    formulario.addEventListener('submit', function (event) {
      event.preventDefault();
  
      // Obtener los valores de los campos del formulario
      const name = document.getElementById('Nombre').value;
      const surname = document.getElementById('Apellido').value;
      const email = document.getElementById('email').value;
      const password = document.getElementById('floatingInputGroup1').value;
      const enterpriseDTO = document.getElementById('Empresa').value;
  
      // Crear un objeto con los datos a enviar
      const datos = {
        name,
        surname,
        email,
        password,
        enterpriseDTO
      };
  
      // Realizar una solicitud POST al backend
      fetch('http://localhost:8080/api/v1/admin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
      })
        .then(response => {
          if (response.status == 201) {
            // El registro se realizó con éxito, puedes redirigir al usuario a otra página
            console.log(response.headers.get('Location'));
            console.log(response);
            //window.location.href = 'pagina_de_exito.html';
          } else {
            // Manejar errores, por ejemplo, mostrar un mensaje de error
            console.error('Error al registrar usuario');
          }
        })
        .catch(error => {
          // Manejar errores de red u otros errores
          console.error('Error de red:', error);
        });
    });
  });
  