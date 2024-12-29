// Functions
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, json } from "react-router-dom";
// Components
import Layout from "./Layout/Layout";
import Login from "./components/Login";
import HamburgerMenu from "./components/HamburgerMenu";
import Sidebar from "./components/Sidebar";
// import Navbar from "./components/Navbar";
import VerticalMenu from "./components/MenuVertical";
import Navbar from "./components/NewNavbar";
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
      {isAuthenticated ? (
        <>
          <Layout>
            <Routes>
              <Route path="/" element={<Dashboard />} /> {/* Ruta inicial */}
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/users" element={<Users />} />
              <Route path="/profile" element={<Profile />} />
            </Routes>
          </Layout>
        </>
      ) : (
        <Login onLogin={handleLogin} />
      )}
    </Router>
  );
}

export default App;
