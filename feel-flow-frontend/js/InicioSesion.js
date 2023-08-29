const listUsers = async (email, password) => {
  const response = await fetch('https://jsonplaceholder.typicode.com/users');
  const users = await response.json();

  let isAuthenticated = false;

  users.forEach((user) => {
    if (user.email === email && user.username === password) {
      isAuthenticated = true;
    }
  });

  const messageContainer = document.getElementById("message-container");

  if (!isAuthenticated) {
    messageContainer.textContent = "Usuario o contraseña incorrectos.";
  } else {
    window.location.href = "../pages/Home.html";
  }
};

document.getElementById("login-form").addEventListener("submit", function (event) {
  event.preventDefault();

  const emailInput = document.getElementById("email");
  const passwordInput = document.getElementById("password");

  const email = emailInput.value;
  const password = passwordInput.value;

  listUsers(email, password);
});