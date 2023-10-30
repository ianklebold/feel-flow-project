export async function InvitarAlEquipo(token, idTeam) {
    const endpoint = `http://localhost:8080/api/v1/team/${idTeam}/invite`;

    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then (Response => {
            if (Response.status === 200) {
                return Response.json();
            } else {
                if (Response.status === 403) {
                    //window.location.href = '../pages/sign_in.html';
                }
            }
        })
        .catch (Error => {
            console.error(Error);
        })
}