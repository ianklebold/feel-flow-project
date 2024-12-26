import { Helmet } from "react-helmet";

function Profile() {
    return (
      
      <div>
        <Helmet>
          <title>Profile</title>
        </Helmet>
        
        <div className="p-6">
          <h1 className="text-2xl font-bold">Mi Perfil</h1>
          <p>Aquí puedes ver y editar la información de tu perfil.</p>
        </div>
      </div>
    );
  }
  
  export default Profile;
  