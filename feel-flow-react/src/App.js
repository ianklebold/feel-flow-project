// Functions
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

// Components
import Layout from "./Layouts/FeelFlow";
import Login from "./pages/Auth/Login";
import HamburgerMenu from "./components/HamburgerMenu";
import Sidebar from "./components/Sidebar";
// import Navbar from "./components/Navbar";
import VerticalMenu from "./components/MenuVertical";
import Navbar from "./components/Navbar";

// Layouts
import Auth from "./Layouts/auth";
import FeelFlow from "./Layouts/FeelFlow";

// Pages
import Dashboard from "./pages/Dashboard";
import Home from "./pages/Home";
import Leaders from "./pages/Leaders";
import Modules from "./pages/Modules";
import Profile from "./pages/Profile";
import EditProfile from "./pages/EditProfile";
import Settings from "./pages/Settings";
import Teams from "./pages/Teams";
import TeamDetails from "./pages/TeamDetails"; // Asegúrate de que esta página esté correctamente importada
import UserManagement from "./pages/UserManagement";
import Users from "./pages/Users";
import Sign_up from "./pages/Auth/Sign_up";

// Styles
import './App.css';

// API
import { clearAuthData, getAuthData, validateToken } from "./services/session";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    JSON.parse(localStorage.getItem("isAuthenticated")) || false
  );
  const { token } = getAuthData()

  const handleLogin = () => {
    setIsAuthenticated(true);
    localStorage.setItem("isAuthenticated", JSON.stringify(true));
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem("isAuthenticated");
    clearAuthData()
  };
  
  useEffect(() => {
    const checkToken = async () => {
      try {
        await validateToken();
      } catch (error) {
        console.error("Error al validar el token:", error);
      }
    };

    if (token) {
      checkToken();
    }
  }, [token]);

  return (
    <Router>
      {isAuthenticated && token != null ? (
        <>
          <FeelFlow onLogout={handleLogout}>
            <Routes>
              <Route path="/" element={<Home />} /> {/* Ruta inicial */}
              <Route path="/home" element={<Home />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/leaders" element={<Leaders />} />
              <Route path="/modules" element={<Modules />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/edit-profile" element={<EditProfile />} />
              <Route path="/settings" element={<Settings />} />
              <Route path="/teams" element={<Teams />} />
              <Route path="/teams/:teamId" element={<TeamDetails />} /> {/* Nueva ruta para TeamDetails */}
              <Route path="/usermanagement" element={<UserManagement />} />
              <Route path="/users" element={<Users />} />
              <Route path="*" element={<Navigate to="/home" />} />
            </Routes>
          </FeelFlow>
        </>
      ) : (
        <Auth>
          <Routes>
            <Route path="/login" element={<Login onLogin={handleLogin} />} />
            <Route path="/register" element={<Sign_up />} />
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </Auth>
      )}
    </Router>
  );
}

export default App;
