const withMT = require("@material-tailwind/react/utils/withMT");

module.exports = withMT({
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: '#1D4ED8', // Azul principal
        secondary: '#1E40AF', // Azul oscuro
        light: '#E0F2FE', // Azul claro
        error: '#DC2626', // Rojo para errores
        success: 'rgb(34 197 94 / var(--tw-text-opacity, 1));', // Verde para los exitosos
        textPrimary: '#1E3A8A', // Azul para texto principal
        textSecondary: '#64748B', // Gris para subtítulos
        textWhite: '#FFFFFF',
        textBlack: '#000000',
        gray: 'rgb(55 65 81 / var(--tw-text-opacity, 1))', // text-gray-700
        bgPrimary: 'rgba(69, 130, 158, 0.323)',
        Primary: 'rgba(69, 130, 158)',
        bgBluePrimary: '#3b82f6', //bg-blue-500
        bgBlueSecondary: '#2563eb', //bg-blue-600
      },
      fontFamily: {
        sans: ['Inter', 'ui-sans-serif', 'system-ui'], // Fuentes predeterminadas
        display: ['Roboto', 'inherit', 'Poppins', 'ui-sans-serif'], // Fuentes para títulos
      }
    },
  },
  plugins: [],
});
