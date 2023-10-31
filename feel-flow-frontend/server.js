const express = require('express');
const app = express();
const port = 8000; // Puedes cambiar el número de puerto si es necesario

// Configura la ruta para servir tus archivos HTML
app.use(express.static(__dirname));

// Inicia el servidor
app.listen(port, () => {
  console.log(`Servidor iniciado en http://localhost:${port}`);
});
