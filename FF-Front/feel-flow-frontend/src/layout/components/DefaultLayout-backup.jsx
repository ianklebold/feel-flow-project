// import '../css/Nav.css'
import { CiUser } from "react-icons/ci";
import { CiSettings } from "react-icons/ci";
import { CiBellOn } from "react-icons/ci";
import { Menu } from './Menu';

import modulos from '../../assets/data/Modules.json'


export default function DefaultLayout({ children }) {
    return (
        <>
            <header>
                <nav>
                    <div className="ContainerPath">
                        <ol className="path">
                            <li className="path-item">
                                <a href="/home">Feel Flow</a>
                            </li>
                            <li className="path-item">
                                Home
                            </li>
                        </ol>
                    </div>
                    <div className='ContainerDropdown'>
                        <ul>
                            <li className="dropdown">
                                <a href="#" className="dropbtn"><CiBellOn /></a>
                                <div className="dropdown-content">
                                    <a href="#">Notification 1</a>
                                    <a href="#">Notification 2</a>
                                    <a href="#">Notification 3</a>
                                </div>
                            </li>
                            <li className="dropdown">
                                <a href="#" className="dropbtn"><CiSettings /></a>
                                <div className="dropdown-content">
                                    <a href="#">Setting 1</a>
                                    <a href="#">Setting 2</a>
                                    <a href="#">Setting 3</a>
                                </div>
                            </li>
                            <li className="dropdown">
                                <a href="#" className="dropbtn"><CiUser /></a>
                                <div className="dropdown-content">
                                    <a href="#">My Profile</a>
                                    <a href="#">Log Off</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>

            <main>
                <nav className='sidenav'>
                    <div className='sidenav-header'>
                        <img src="../src/assets/image/icons/FeelFlow.png" alt="Feel Flow" />
                        <span>Feel Flow</span>
                    </div>
                    <hr />
                    <div className='sidenav-body'>
                        <>
                            <ul className="navbar-nav">
                                {   
                                    modulos.map(mod => (
                                        
                                        <Menu key={mod.id} name={mod.nombre} icon={mod.logo} url={mod.link}/>
                                        
                                    ))
                                }
                            </ul>
                            
                        </>
                    </div>
                </nav>
                <div className='contenido'>
                    {children}
                </div>
            </main>
        </>

    )
}