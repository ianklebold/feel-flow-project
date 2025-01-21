import { Helmet } from "react-helmet";
import { useState } from "react";

// Components
import Searchbar from "../components/Searchbar";
import Table from "../components/Table";



function Users() {
  const [searchUser, setSearchUser] = useState("");
  const [searchUserName, setSearchUsername] = useState("");
  const [users, setUsers] = useState([{ name: "Pepe Argento", username: "pepe@gmail.com" }]);

  const filteredUsers = users.filter((user) => {
    const name = user.name || "";
    const username = user.username || "";
    console.log(name, username);
    return (
      name.toLowerCase().includes(searchUser.toLowerCase()) &&
      username.toLowerCase().includes(searchUserName.toLowerCase())
    );
  });
  console.log(users);
  // setUsers([{ name: "Pepe Argento", user: "pepe@gmail.com" }])
  return (
    <>
      <Helmet>
        <title>Users</title>
      </Helmet>

      <div className="p-6">
        <div className="flex flex-col md:flex-row gap-6 mt-6 items-center">
          <div className="flex-1">
            <Searchbar
              placeholder="Buscar usuario..."
              value={searchUser}
              onChange={(e) => setSearchUser(e.target.value)}
            />
          </div>
          <div className="flex-1">
            <Searchbar
              placeholder="Buscar username..."
              value={searchUserName}
              onChange={(e) => setSearchUsername(e.target.value)}
            />
          </div>
          
        </div>

      </div>
      <Table
        columns={[
          {header: "Usuario", key: "name"},
          {header: "Username", key: "username"},
        ]}
        data={filteredUsers}
        // showIcon={true}
        // onIconClick={handleIconClick}
        striped={true}
        noDataText="No se encontraron equipos."
      />
    </>
  );
}

export default Users;
