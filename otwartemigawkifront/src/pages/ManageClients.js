import {React, useState, useMemo, useEffect} from 'react';
import axios from 'axios';
import AppBar from '../components/AppBar.js';
import fakeData from "./clients.json";
import "../css/ManageClients.css";
import {useTable} from 'react-table'
import { fetchClients } from '../api/ManageClientsApi.js';


const ManageClients = () => {

  const [clients, setClients] = useState([]);

  useEffect(() => {
        const fetchData = async () => {
            try {
                const clientsData = await fetchClients();
                setClients(clientsData);
            } catch (error) {
                console.error('Error fetching clients:', error);
            }
        };

        fetchData();
  }, []);


  //const data = useMemo( () => fakeData, []);
  const columns = useMemo(()=>[
    {
      Header: "ID",
      accessor: "id",
    },
    {
      Header: "Imię",
      accessor: "name",
    },
    {
      Header: "Nazwisko",
      accessor: "surname",
    },
    {
      Header: "Email",
      accessor: "email",
    },
    {
      Header: "Nr telefonu",
      accessor: "phone",
    },
    {
      Header: "Akcje",
      Cell: ({ row }) => (
        <button className="deleteButton" onClick={() => handleDelete(row.original.id)}>
                    Usuń
                </button>
      )
    },
  ], 
  []
  )

  const handleDelete = async (userId) => {
    try {
      await axios.delete(`http://localhost:8080/api/clients/${userId}`);
      const clientsData = await fetchClients();
      setClients(clientsData);
      } catch (error) {
          console.error('Error deleting client:', error);
      }
  }
  
  const {getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = useTable({columns, data: clients,})
  
  return (
    <div>
    <AppBar />
    <h1 className='flex-centered site-header'>Klienci</h1>
    <div className='flex-centered'>
      <table {...getTableProps()}>
        <thead>
          {headerGroups.map((headerGroup) =>(
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column) =>(
                <th {...column.getHeaderGroupProps}>
                    {column.render("Header")}
                </th>
              )
            )}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row) => {
            prepareRow(row)
            return(
              <tr {...row.getRowProps()}>
                {row.cells.map((cell) => (
                  <td {...cell.getCellProps}>
                    {cell.render("Cell")}
                  </td>
                ))}
              </tr>
            )
          })}

        </tbody>
      </table>
      
    </div>
    </div>
  );
}


export default ManageClients;