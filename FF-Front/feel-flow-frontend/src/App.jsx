import { useState } from 'react'
import { Route, Routes, Link, BrowserRouter } from "react-router-dom";

import './assets/css/App.css'

import { Login } from './pages/Login'
import { Home } from './pages/Home'

const App= () => {
  return(
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
      </Routes>
    </BrowserRouter>
      
  )
}

export default App