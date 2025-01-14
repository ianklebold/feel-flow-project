
const permissions = {
    modules: {
        dashboard: ["ADMIN", "TEAM_LEADER"],
        settings: ["ADMIN"],
        users: ["ADMIN"],
        modules: ["ADMIN", "TEAM_LEADER"],
        team: ["TEAM_LEADER"],
        teams: ["ADMIN"],
        profile: ["ADMIN", "TEAM_LEADER"]
    }
};

export const canAccess = (module, userRole) => {
    const allowedRoles = permissions.modules[module];
    // console.log(allowedRoles?.includes(userRole));
    return allowedRoles?.includes(userRole);
};