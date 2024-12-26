import { Helmet } from 'react-helmet';

function Dashboard() {
  return (
    <div>
      <Helmet>
        <title>Dashboard</title>
      </Helmet>
      {/* Contenido de la página */}

      <div className="p-6">
        <h1 className="text-2xl font-bold">Dashboard</h1>
        <p>¡Bienvenido al panel principal!</p>
      </div>
    </div>

  );
}

export default Dashboard;
