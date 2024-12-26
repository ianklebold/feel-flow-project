import { Helmet } from "react-helmet";

function Users() {
    return (
      <div>
        <Helmet>
          <title>Users</title>
        </Helmet>

        <div className="p-6">
          <h1 className="text-2xl font-bold">Gestión de Usuarios</h1>
          <p>Aquí podrás gestionar a los usuarios de la plataforma.</p>
        </div>
        
      </div>
    );
  }
  
  export default Users;
  