import {React, useState, useMemo} from 'react';
import AppBar from '../components/AppBar.js';
import fakeData from "./clients.json";
import "../css/ManageClients.css";
import {useTable} from 'react-table'


const ManageClients = () => {
  const data = useMemo( () => fakeData, []);
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
        <button className='deleteButton' onClick={() => handleDelete(row.original.id)}>Usuń</button>
      )
    },
  ], 
  []
  )

  const handleDelete = (id) => {
    // Implement your delete functionality here
    console.log("Delete button clicked for ID:", id);
  }
  
  const {getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = useTable({columns, data})
  
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