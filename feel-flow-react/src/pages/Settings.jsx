import React from "react";
import { Helmet } from 'react-helmet';

function Settings() {
    return (
        <div>
            <Helmet>
                <title>Settings</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <h1>Settings</h1>
            </div>
        </div>

    );
}
export default Settings;
