import React from "react";
import { useEffect, useState } from "react";
import { CiHome } from "react-icons/ci";


// import '../css/Menu.css'

export function Menu({ id, name, icon, url }) {
    console.log(icon)
    const formattedIcon = icon.charAt(0).toUpperCase() + icon.slice(1);
    const IconComponent = React.createElement(formattedIcon);
    console.log(IconComponent)
    return (
        <li className="nav-item">
            <a className="nav-link active" href="">
                <div className="icon">
                    <CiHome />
                </div>
                <span className="text">{name}</span>
            </a>
        </li>
    )
}