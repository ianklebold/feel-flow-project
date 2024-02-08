export async function ObtenerModulos(token, name_module, state, includeCreationDateOrder = true) {
    let url = 'http://localhost:8080/api/v1/modules';

    // Construir los parámetros de la URL basados en los filtros opcionales
    const params = new URLSearchParams();
    if (name_module) {
        params.append('name', name_module);
    }
    if (state) {
        params.append('state', state);
    }
    
    // Agregar el filtro creation_date_order solo si includeCreationDateOrder es true
    if (includeCreationDateOrder) {
        params.append('creation_date_order', 'true');
    }

    // Agregar los parámetros a la URL si hay alguno
    if (params.toString() !== '') {
        url += '?' + params.toString();
    }
        
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'accept': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    });
    if (response.ok) {
        const data = await response.json();
        console.log(data)
        if (data.length > 0) {
            console.info("The module id was obtained successfully")
            return data[0];
        } else {
            console.error('Error when obtaining the module id');
            return null;
        }
    } else {
        console.error('Error sending request:', response.status);
        return null;
    }
}