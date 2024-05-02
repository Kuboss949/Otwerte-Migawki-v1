import {React, useState, useMemo} from 'react';
import {InputBox} from '../components/InputBox.js';
import AppBar from '../components/AppBar.js';
import fakeData from "./sessions.json";
import "../css/ManageSessions.css";
import {useTable} from 'react-table'


const ManageSessions = () => {
  const data = useMemo( () => fakeData, []);
  const columns = useMemo(()=>[
    {
      Header: "Nazwa sesji",
      accessor: "name",
    },
    {
      Header: "Cena",
      accessor: "price",
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
    <h1 className='flex-centered site-header'>Sesje</h1>
    <div className='add-session flex-centered'>
      <form className='flex-centered' method='POST'>
        <InputBox label='Nazwa sesji' name='session-name'/>
        <InputBox label='Cena' name='price'/>
        <button className='site-button'>Dodaj</button>
      </form>
    </div>
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


export default ManageSessions;