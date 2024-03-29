import React from "react";
import { useEffect, useState } from "react";
import { CiHome, CiUser, CiSettings } from "react-icons/ci";
import { FaHome, FaUserAlt, FaCogs, FaIdCard, FaModx, FaUsers, FaCrown, FaPalette, FaTrophy } from "react-icons/fa";



// import '../css/Menu.css'

export function Menu({ id, name, icon, url }) {
    console.log(icon)
    const formattedIcon = icon.charAt(0).toUpperCase() + icon.slice(1);
    const IconComponent = React.createElement(formattedIcon);

    return (
        <li className="nav-item">
            <a className="nav-link active" href={url}>
                <div className="icon">
                    {getIcon(icon)}
                </div>
                <span className="text">{name}</span>
            </a>
        </li>
    )
}

function getIcon(logo) {
    switch (logo) {
        case 'CiHome':
            return <FaHome />;
        case 'FaPalette':
            return <FaPalette />;
        case 'FaCrown':
            return <FaCrown />;
        case 'FaTrophy':
            return <FaTrophy />;
        case 'FaModx':
            return <FaModx />;
        case 'FaUsers':
            return <FaUsers />;
        case 'FaIdCard':
            return <FaIdCard />;
        case 'CiSettings':
            return <FaCogs />;
        default:
            return null;
    }
}