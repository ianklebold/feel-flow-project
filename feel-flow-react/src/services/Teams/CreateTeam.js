export async function createTeam(name, description, teamLeader, token) {
    const body = {
        nameTeam: name,
        descriptionTeam: description,
        teamLeaderDTO: teamLeader
    };

    try {
        const response = await fetch('http://localhost:8080/api/v1/team', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })

        if (response.status !== 200) {
            const errorData = await response.json();
            return { errors: errorData };
        }

        return response.status;
    } catch (error) {
        return { errors: { general: "Error de red o servidor." } };
    }
}