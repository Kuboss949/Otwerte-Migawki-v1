import { React, useState, useEffect } from 'react';
import AppBar from '../components/AppBar.js';
import "../css/ManageClients.css";
import { fetchClientsData} from '../api/manage-clients-api.js';
import ClientsTable from '../components/tables/ClientsTable.js';


const ManageClients = () => {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    fetchClientsData(setClients);
  }, []);

  return (
    <div>
      <AppBar />
      <h1 className='flex-centered site-header'>Klienci</h1>
      <div className='flex-centered'>
        <ClientsTable data={clients} setClients={setClients} />
      </div>
    </div>
  );
}

export default ManageClients;