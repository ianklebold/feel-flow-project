// Functions
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, json } from "react-router-dom";
// Components
import Login from "./components/Login";
import HamburgerMenu from "./components/HamburgerMenu";
import Navbar from "./components/Navbar";
import VerticalMenu from "./components/MenuVertical";
// Pages
import UserManagement from "./pages/UserManagement";
import Profile from "./pages/Profile";
import Dashboard from "./pages/Dashboard";
import Users from "./pages/Users";
// Styles
import './App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    JSON.parse(localStorage.getItem("isAuthenticated")) || false
  );

  const handleLogin = () => {
    setIsAuthenticated(true);
    localStorage.setItem("isAuthenticated", JSON.stringify(true));
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem("isAuthenticated");
  };

  // useEffect(() => {
  //   localStorage.setItem("isAuthenticated", JSON.stringify(isAuthenticated));
  // }, [isAuthenticated])

  return (
    <Router>
      <div className="flex min-h-screen">
        {isAuthenticated ? (
          <>
            {/* Menú vertical */}
            <VerticalMenu />

            {/* Contenido principal */}
            <div className="flex-grow">
              {/* <button
                onClick={handleLogout}
                className="absolute top-5 right-4 bg-red-500 text-white px-4 py-2 rounded"
              >
                Cerrar Sesión
              </button> */}
              <main className="p-6">
                <Navbar onLogout={() => setIsAuthenticated(false)} />
                <Routes>
                  <Route path="/dashboard" element={<Dashboard />} />
                  <Route path="/users" element={<Users />} />
                  <Route path="/profile" element={<Profile />} />
                  <Route path="/" element={<Dashboard />} /> {/* Ruta inicial */}
                </Routes>
              </main>
            </div>
          </>
        ) : (
          <Login onLogin={handleLogin} />
        )}
      </div>
    </Router>
  );
  return (
    <Router>
      <div>
        {isAuthenticated ? (
          <>
            {/* <HamburgerMenu onLogout={() => setIsAuthenticated(false)} /> */}
            <VerticalMenu />
            <button
              onClick={handleLogout}
              className="absolute top-5 right-4 bg-red-500 text-white px-4 py-2 rounded"
            >
              Cerrar Sesión
            </button>
            <Routes>
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/users" element={<Users />} />
              {/* <Route path="/users" element={<UserManagement />} /> */}
              <Route path="/profile" element={<Profile />} />
            </Routes>
          </>
        ) : (
          <Login onLogin={handleLogin} />
          // <Login onLogin={() => setIsAuthenticated(true)} />
        )}
      </div>
    </Router>
  );
}

export default App;
