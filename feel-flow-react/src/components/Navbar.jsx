// Dependencies
import React from 'react';
import { NavLink, useLocation, Link } from 'react-router-dom';
import { useState } from 'react';
import { FaBell } from 'react-icons/fa';
import { FaExclamationCircle } from 'react-icons/fa';

// Elements
import FeelFlow from '../assets/img/FeelFlow.png';
import Breadcrumbs from './Breadcrumbs';
import IconButton from './IconButton';


function Navbar({ onLogout }) {
    const [isOpen, setIsOpen] = useState(false);
    const [notificationsIsOpen, setNotificationsIsOpen] = useState(false);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };
    const notificationsDropdown = () => {
        setNotificationsIsOpen(!notificationsIsOpen);
    };

    const openModal = (e) => {
        e.preventDefault();
        setModalIsOpen(true);  // Abre el modal
    };

    const closeModal = () => {
        setModalIsOpen(false); // Cierra el modal
    };

    const handleLogout = () => {
        onLogout();
        closeModal();
        // Llama a la función de cierre de sesión pasada como props
    };
    return (
        <nav>
            <div class="mx-auto px-2 sm:px-6 lg:px-8">
                <div class="relative flex h-16 items-center justify-between">
                    <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
                        {/* <!-- Mobile menu button--> */}
                        <button type="button" class="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white" aria-controls="mobile-menu" aria-expanded="false">
                            <span class="absolute -inset-0.5"></span>
                            <span class="sr-only">Open main menu</span>
                            {/* <!--Icon when menu is closed. Menu open: "hidden", Menu closed: "block"--> */}
                            <svg class="block size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                            </svg>
                            {/* <!--Icon when menu is open. Menu open: "block", Menu closed: "hidden" --> */}
                            <svg class="hidden size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
                            </svg>
                        </button>
                    </div>
                    <div class="flex flex-1 items-center justify-center align-middle sm:justify-start">
                        <div class="flex shrink-0 items-center">
                            <img class="h-8 w-auto" src={FeelFlow} alt="Feel Flow" />
                        </div>
                        <div class="hidden sm:ml-6 sm:block">
                            <div class="flex space-x-4 font-bold text-xs">
                                {/* <!-- Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" --> */}
                                <nav className="flex space-x-1">
                                    <Breadcrumbs></Breadcrumbs>
                                </nav>
                            </div>
                        </div>
                    </div>
                    <div class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">

                        <IconButton icon={FaBell} onClick={notificationsDropdown} color="black" size="md" tooltip="Notifications" />

                        {/* <!-- Profile dropdown --> */}
                        <div class="relative ml-3">
                            <div>
                                <button type="button" class="relative flex rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 hover:bg-bgBluePrimary" id="user-menu-button" onClick={toggleDropdown} aria-expanded={isOpen} aria-haspopup="true">
                                    <span class="absolute -inset-1.5"></span>
                                    <span class="sr-only">Open user menu</span>
                                    <img class="size-8 rounded-full" src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="" />
                                </button>
                            </div>
                            {/* Modal de confirmación de cierre de sesión */}

                            {modalIsOpen && (
                                <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
                                    <div className="bg-white max-w-sm w-full rounded-lg shadow-lg transform transition-all duration-300 scale-95 hover:scale-100 p-6">
                                        <div className="flex items-center mb-4">
                                        <FaExclamationCircle className="text-yellow-400 text-5xl mr-4" />
                                            <h3 className="text-xl font-roboto-bold text-textBlack">¿Estás seguro que quieres cerrar sesión?</h3>
                                        </div>
                                        <div className="mt-6 flex justify-center space-x-4">
                                            <button
                                                className="px-4 py-2 text-gray-700 font-semibold rounded-lg border border-gray-300 bg-bgBluePrimary hover:bg-bgBlueSecondary transition-colors duration-200"
                                                onClick={closeModal}
                                            >
                                                Cancelar
                                            </button>
                                            <button
                                                className="px-4 py-2 text-white font-semibold rounded-lg bg-red-600 hover:bg-red-700 transition-colors duration-200"
                                                onClick={handleLogout}
                                            >
                                                Cerrar sesión
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            )}
                            {/* {modalIsOpen && (
                                <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-500 bg-opacity-50">
                                    <div className="bg-white p-6 rounded-lg shadow-lg">
                                        <h3 className="text-lg font-semibold">¿Estas seguro que quieres cerrar sesión?</h3>
                                        <div className="mt-4 flex justify-end space-x-4">
                                            <button className="px-4 py-2 bg-primary text-gray-700 rounded" onClick={closeModal}>Cancel</button>
                                            <button className="px-4 py-2 bg-error text-white rounded" onClick={handleLogout}>Log Out</button>
                                        </div>
                                    </div>
                                </div>
                            )} */}
                            {notificationsIsOpen && (
                                <div class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-light py-1 shadow-lg ring-1 ring-black/5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="user-menu-button" tabindex="-1">
                                    {/* <!-- Active: "bg-gray-100 outline-none", Not Active: "" --> */}
                                    <a href="#" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-0">Notification 1</a>
                                    <a href="#" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-1">Notification 2</a>
                                    <a href="#" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-2">Notification 3</a>
                                </div>
                            )}
                            {isOpen && (
                                <div class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-light py-1 shadow-lg ring-1 ring-black/5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="user-menu-button" tabindex="-1">
                                    {/* <!-- Active: "bg-gray-100 outline-none", Not Active: "" --> */}
                                    <a href="#" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-0">Your Profile</a>
                                    <a href="#" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-1">Settings</a>
                                    <a href="/" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-2" onClick={openModal}>Sign out</a>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>


            {/* <!-- Mobile menu, show/hide based on menu state. --> */}
            <div class="sm:hidden" id="mobile-menu">
                <div class="space-y-1 px-2 pb-3 pt-2">
                    {/* <!-- Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" --> */}
                    <a href="#" class="block rounded-md bg-gray-900 px-3 py-2 text-base font-medium text-white" aria-current="page">Dashboard</a>
                    <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Team</a>
                    <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Projects</a>
                    <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Calendar</a>
                </div>
            </div>
        </nav>

    );

}
export default Navbar;
