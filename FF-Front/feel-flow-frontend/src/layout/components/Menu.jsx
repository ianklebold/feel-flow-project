import React from "react";
import { useEffect, useState } from "react";
import { CiHome } from "react-icons/ci";


// import '../css/Menu.css'

export function Menu({ id, name, icon, url }) {
    const IconComponent = icon ? React.createElement(icon) : null;

    return (
        <div className="modulo" key={id}>

            <a href={url}>
                
                <i><CiHome /></i>
                <span>{name}</span>
            </a>
        </div>

    )
}