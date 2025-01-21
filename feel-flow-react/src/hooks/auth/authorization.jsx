
const permissions = {
    modules: {
        Dashboard: ["ADMIN", "TEAM_LEADER"],
        Resultados: ["ADMIN", "TEAM_LEADER"],
        Configuracion: ["ADMIN"],
        Usuarios: ["ADMIN"],
        Modulos: ["ADMIN", "TEAM_LEADER"],
        // Equipo: ["TEAM_LEADER"],
        Equipos: ["ADMIN","TEAM_LEADER"],
        Perfil: ["ADMIN", "TEAM_LEADER"]
    }
};

export const canAccess = (module, userRole) => {
    const allowedRoles = permissions.modules[module];
    // console.log(allowedRoles?.includes(userRole));
    return allowedRoles?.includes(userRole);
};