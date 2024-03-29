document.addEventListener("DOMContentLoaded", function () {
    const logoutLink = document.querySelector("#logoutLink");

    if (logoutLink) {
        logoutLink.addEventListener("click", function (event) {
            event.preventDefault();

            Swal.fire({
                title: "Cerrar sesión",
                text: "¿Estás seguro de que deseas cerrar sesión?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#dc3545",
                cancelButtonColor: "#1e7bf0", //#1e7bf0
                confirmButtonText: '<span style="color: white;">Sí</span>',
                cancelButtonText: '<span style="color: white;">No</span>',
                customClass: {
                    popup: "bg-white text-white",
                    confirmButton: "btn btn-danger",
                    cancelButton: "btn btn-secondary",
                },
            }).then((result) => {
                if (result.isConfirmed) {
                    CerrarSesion();
                    window.location.href = "../pages/sign_in.html";
                }
            });
        });
    }
});

function CerrarSesion() {
    var tkn = localStorage.getItem('token');
    let url = `http://localhost:8080/api/v1/auth/logout`;
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${tkn}`
        }
    })
    localStorage.removeItem('token');
}