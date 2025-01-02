// Functions
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, json, Navigate } from "react-router-dom";
// Components
import Layout from "./Layouts/FeelFlow";
import Login from "./components/Login";
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
import Settings from "./pages/Settings";
import Teams from "./pages/Teams";
import UserManagement from "./pages/UserManagement";
import Users from "./pages/Users";
// Styles
import './App.css';

// API
import { clearAuthData, getAuthData } from "./services/session";

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
    console.log(getAuthData());
    clearAuthData()
    console.log(getAuthData());
  };

  // useEffect(() => {
  //   localStorage.setItem("isAuthenticated", JSON.stringify(isAuthenticated));
  // }, [isAuthenticated])

  return (
    <Router>
      {isAuthenticated ? (
        <>
          <FeelFlow onLogout={handleLogout}>
            <Routes>
              <Route path="/" element={<Home />} /> {/* Ruta inicial */}
              <Route path="/home" element={<Home />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/leaders" element={<Leaders />} />
              <Route path="/modules" element={<Modules />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/settings" element={<Settings />} />
              <Route path="/teams" element={<Teams />} />
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
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </Auth>

      )}
    </Router>
  );
}

export default App;
