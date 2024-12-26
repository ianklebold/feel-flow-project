import { Helmet } from "react-helmet";

function UserManagement() {
    return (
      <div style={{ padding: "20px" }}>
        <Helmet>
          <title>Users</title>
        </Helmet>

        <div className="p-6">
          <h2>Gestión de Usuarios</h2>
          <p>Aquí podrás administrar los usuarios.</p>
        </div>
      </div>
    );
  }
  
  export default UserManagement;
  