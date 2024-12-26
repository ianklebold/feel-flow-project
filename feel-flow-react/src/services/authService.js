export const login = async (username, password) => {
    // Simular una respuesta del servidor
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (username === "admin" && password === "1234") {
          resolve({ success: true, token: "fake-token" });
        } else {
          reject({ success: false, message: "Credenciales incorrectas" });
        }
      }, 1000);
    });
  };
  