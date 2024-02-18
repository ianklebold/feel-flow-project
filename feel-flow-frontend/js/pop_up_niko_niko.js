function openPopup() {
    document.getElementById("popup-container").style.display = "flex";
  }
  
  function closePopup() {
    document.getElementById("popup-container").style.display = "none";
  }
  function selectEmoji(emoji, mood) {
    // Eliminar la clase "selected" de todos los botones
    document.querySelectorAll(".emoji-options button").forEach(function(btn) {
      btn.classList.remove("selected");
    });
  
    // Agregar la clase "selected" al botón actualmente seleccionado
    document.querySelector(".emoji-options button:focus").classList.add("selected");
  
    // Actualizar el estado de ánimo seleccionado
    document.getElementById("selected-mood").textContent = mood;
  }
  
  
  function shareMood() {
    var selectedEmoji = document.querySelector(".emoji-options button.selected").textContent;
    var moodName = document.getElementById("selected-mood").textContent;
    var comment = document.getElementById("comment").value;
  
    console.log("Estado de ánimo:", moodName, selectedEmoji);
    console.log("Comentario:", comment);
  
    closePopup();
  }
  
  function updateCurrentDate() {
    const currentDate = new Date();
    const day = currentDate.getDate();
    const monthNames = [
      "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];
    const month = monthNames[currentDate.getMonth()];
    const year = currentDate.getFullYear();
  
    const formattedDate = `${day} de ${month} de ${year}`;
    document.getElementById("current-date").textContent = formattedDate;
  }
  
  // Llamar a la función para actualizar la fecha al cargar la página
  document.addEventListener("DOMContentLoaded", function() {
    openPopup();
    updateCurrentDate();
  });