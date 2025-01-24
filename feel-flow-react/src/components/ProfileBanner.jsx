import React, { useState, useEffect } from 'react';
import Banner from '../assets/img/banner.jpg';

const ProfileBanner = () => {
  const [inView, setInView] = useState(false);

  // Detecta cuando el banner entra en la vista (para la animación)
  useEffect(() => {
    const handleScroll = () => {
      const bannerPosition = document.getElementById('banner').getBoundingClientRect().top;
      const screenPosition = window.innerHeight / 1.5;

      if (bannerPosition < screenPosition) {
        setInView(true);
      } else {
        setInView(false);
      }
    };

    window.addEventListener('scroll', handleScroll);
    handleScroll(); // Para comprobar al cargar la página.

    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <div className="relative w-full h-72 overflow-hidden rounded-xl shadow-lg">
      {/* Fondo con Parallax */}
      <div
        id="banner"
        className={`absolute inset-0 overflow-hidden transition-all duration-1000 ease-in-out transform ${
          inView ? 'scale-100' : 'scale-110'
        }`}
      >
        <img
          src={Banner}
          alt="Banner"
          className="w-full h-full object-cover"
        />
      </div>

      {/* Superposición Oscura */}
      <div className="absolute inset-0 bg-black opacity-40"></div>

    </div>
  );
};

export default ProfileBanner;
