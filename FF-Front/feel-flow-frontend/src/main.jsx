import React from 'react'
import ReactDOM from 'react-dom/client'

import './index.css'

import App from './main/App.jsx'
import { Login } from './main/Login.jsx'
import { Register } from './main/Register.jsx'
import { Home } from './main/Home.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Home />
  </React.StrictMode>,
)
