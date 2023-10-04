  // Agrega un evento de clic al botón "Invitar miembros"
document.getElementById('inviteMembersButton').addEventListener('click', function () {
  // Crear el contenido HTML del modal con el botón de copiar
  const modalContent = `
    <strong>Link de Invitación:</strong>
    <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">LINK</a>
    <button class="btn btn-primary mt-2" id="copyButton">Copiar link</button>
  `;

  // Mostrar el modal de SweetAlert
    Swal.fire({
      title: modalContent,
      icon: 'info',
      focusConfirm: false,
      confirmButtonText: 'Listo',
      confirmButtonAriaLabel: 'Thumbs up, great!',
      });

  // Agregar un evento de clic al botón "Copiar al portapapeles" en el modal
  document.getElementById('copyButton').addEventListener('click', function () {
    // Obtener el texto que se encuentra en el campo de entrada
    const textoACopiar = document.getElementById('copiedText').value;

    // Copiar el texto al portapapeles
    navigator.clipboard.writeText(textoACopiar)
      .then(function () {
      })
      .catch(function (error) {
        // Error al copiar el texto
        console.error('Error al copiar el texto: ', error);
      });
  });
});

