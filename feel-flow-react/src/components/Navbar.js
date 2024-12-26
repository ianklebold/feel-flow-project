import React from 'react';
import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Helmet } from 'react-helmet';

const Navbar = ({ onLogout }) => {
  // useEffect(() => {
  //   console.log("Título actual:", document.title);
  // }, []);

  const [isOpen, setIsOpen] = useState(false);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const { pathname } = useLocation();

  const getPageTitle = () => {
    switch (pathname) {
      case '/':
        return 'Feel Flow';
      case 'Dashboard':
        return 'Page Title';
      default:
        return 'Unknown';
    }
  };

  const NavLink = ({ to, children, className }) => {
    return (
      <Link
        to={to}
        className={`transition-colors duration-300 ${className}`}
      >
        {children}
      </Link>
    );
  };

  return (
    <div>

      <Helmet>
        <title>{getPageTitle()}</title>
      </Helmet>

      <nav class="bg-bgPrimary">
        <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
          <div class="relative flex h-16 items-center justify-between">
            <div class="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
              <nav className="flex space-x-4">
                <NavLink to="/" className="hover:text-gray-400">
                  Feel Flow
                </NavLink>
                <span className="hover:text-gray-400">
                  <Helmet>
                    <title></title>
                  </Helmet>
                </span>
              </nav>
            </div>
            <div class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
              <button type="button" class="relative rounded-full p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
                <span class="absolute -inset-1.5"></span>
                <span class="sr-only">View notifications</span>
                <svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0" />
                </svg>
              </button>

              {/* <!-- Profile dropdown --> */}
              <div className="relative ml-3">
                <div>
                  <button
                    type="button"
                    className="relative flex rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
                    onClick={toggleDropdown}
                    aria-expanded={isOpen}
                    aria-haspopup="true"
                  >
                    <span className="absolute -inset-1.5"></span>
                    <span className="sr-only">Open user menu</span>
                    <img
                      className="h-8 w-8 rounded-full"
                      src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                      alt=""
                    />
                  </button>
                </div>

                {isOpen && (
                  <div
                    className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black/5 focus:outline-none"
                    role="menu"
                    aria-orientation="vertical"
                    aria-labelledby="user-menu-button"
                    tabIndex="-1"
                  >
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      role="menuitem"
                      tabIndex="-1"
                      id="user-menu-item-0"
                    >
                      Your Profile
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      role="menuitem"
                      tabIndex="-1"
                      id="user-menu-item-1"
                    >
                      Settings
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      role="menuitem"
                      tabIndex="-1"
                      id="user-menu-item-2"
                      onClick={onLogout}
                    >
                      Sign out
                    </a>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </nav>
    </div>

  );
  return (
    <nav className="bgPrimary shadow-lg">
      <div className="container mx-auto flex justify-between items-center py-2 px-4">
        <nav aria-label="breadcrumb">
          <ol className="flex space-x-2">
            <li>
              <a className="text-gray-500 hover:text-gray-700" href="../pages/home.html">Feel Flow</a>
            </li>
            <li className="text-gray-700">Home</li>
          </ol>
        </nav>
        <div className="flex items-center space-x-4">
          <div className="relative">
            <button className="text-gray-500 focus:outline-none">
              <i className="fa fa-bell"></i>
            </button>
            <div className="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded">
              <ul className="py-2">
                <li>
                  <a className="flex items-center p-2 hover:bg-gray-100" href="#">
                    <img src="../img/team-2.jpg" className="h-8 w-8 rounded-full mr-2" alt="Avatar" />
                    <div>
                      <h6 className="text-sm font-semibold">New message from Laur</h6>
                      <p className="text-xs text-gray-500">13 minutes ago</p>
                    </div>
                  </a>
                </li>
                <li>
                  <a className="flex items-center p-2 hover:bg-gray-100" href="#">
                    <img src="../img/small-logos/logo-spotify.svg" className="h-8 w-8 mr-2" alt="Avatar" />
                    <div>
                      <h6 className="text-sm font-semibold">New album by Travis Scott</h6>
                      <p className="text-xs text-gray-500">1 day</p>
                    </div>
                  </a>
                </li>
                <li>
                  <a className="flex items-center p-2 hover:bg-gray-100" href="#">
                    <div className="h-8 w-8 bg-gray-300 rounded-full flex items-center justify-center mr-2">
                      <i className="fa fa-credit-card text-white"></i>
                    </div>
                    <div>
                      <h6 className="text-sm font-semibold">Payment successfully completed</h6>
                      <p className="text-xs text-gray-500">2 days</p>
                    </div>
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <button className="text-gray-500 focus:outline-none">
            <i className="fa fa-cog"></i>
          </button>
          <div className="relative">
            <button className="text-gray-500 focus:outline-none">
              <i className="fa fa-user"></i>
            </button>
            <div className="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded">
              <ul className="py-2">
                <li>
                  <a className="block px-4 py-2 hover:bg-gray-100" href="">Mi Perfil</a>
                </li>
                <li>
                  <a className="block px-4 py-2 hover:bg-gray-100" href="" id="logoutLink">Cerrar Sesión</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
