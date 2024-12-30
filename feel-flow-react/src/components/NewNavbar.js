// Dependencies
import React from 'react';
import { NavLink, useLocation, Link } from 'react-router-dom';
import { useState } from 'react';
import { FaBell } from 'react-icons/fa';

// Elements
import FeelFlow from '../assets/img/FeelFlow.png';
import Breadcrumbs from '../widgets/Breadcrumbs';
import IconButton from '../widgets/Layout/IconButton';


function Navbar({ onLogout }) {
    const [isOpen, setIsOpen] = useState(false);
    const [notificationsIsOpen, setNotificationsIsOpen] = useState(false);
    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };
    const notificationsDropdown = () => {
        setNotificationsIsOpen(!notificationsIsOpen);
    };
    const layout = "FeelFlow";
    var [page] = "Home";
    const { pathname } = useLocation();
    var [page] = pathname.split("/").filter((el) => el !== "");
    console.log(layout, page);
    return (
        <nav class="bg-bgPrimary">
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
                        <button type="button" class="relative rounded-full bg-bgPrimary p-1 text-black hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 hover:bg-bgBluePrimary">
                            <span class="absolute -inset-1.5"></span>
                            {/* 
                            <span class="relative flex h-3 w-3">
                                <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-sky-400 opacity-75"></span>
                                <span class="relative inline-flex rounded-full h-3 w-3 bg-sky-500"></span>
                            </span> 
                            */}
                            <span class="sr-only">View notifications</span>
                            <svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0" />
                            </svg>
                        </button>

                        {/* <!-- Profile dropdown --> */}
                        <div class="relative ml-3">
                            <div>
                                <button type="button" class="relative flex rounded-full bg-bgPrimary text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 hover:bg-bgBluePrimary" id="user-menu-button" onClick={toggleDropdown} aria-expanded={isOpen} aria-haspopup="true">
                                    <span class="absolute -inset-1.5"></span>
                                    <span class="sr-only">Open user menu</span>
                                    <img class="size-8 rounded-full" src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="" />
                                </button>
                            </div>

                            {/* <!--
                            Dropdown menu, show/hide based on menu state.

                            Entering: "transition ease-out duration-100"
                            From: "transform opacity-0 scale-95"
                            To: "transform opacity-100 scale-100"
                            Leaving: "transition ease-in duration-75"
                            From: "transform opacity-100 scale-100"
                            To: "transform opacity-0 scale-95" --> */}
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
                                    <a href="#" class="block px-4 py-2 text-sm text-gray hover:bg-bgBlueSecondary" role="menuitem" tabindex="-1" id="user-menu-item-2" onClick={onLogout} >Sign out</a>
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
