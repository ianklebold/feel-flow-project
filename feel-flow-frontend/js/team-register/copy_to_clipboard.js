document.getElementById('copyButton').addEventListener('click', function () {
    // Obtener el texto que se encuentra en el campo de entrada
    const textoACopiar = document.getElementById('copiedText').value;

    // Copiar el texto al portapapeles
    navigator.clipboard.writeText(textoACopiar)
        .then(function () {
            // Éxito: el texto se ha copiado al portapapeles
            alert('Texto copiado al portapapeles.');
        })
        .catch(function (error) {
            // Error al copiar el texto
            console.error('Error al copiar el texto: ', error);
        });
});