import {React, useState, useMemo} from 'react';
import AppBar from '../components/AppBar.js';
import fakeData from "./clients.json";
import "../css/ShowClients.css";
import {useTable} from 'react-table'


const ShowClients = () => {
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
  ], 
  []
  )
  
  const {getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = useTable({columns, data})
  
  return (
    <div>
    <AppBar />
    <h1 className='flex-centered site-header'>Twoje dane</h1>
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






export default ShowClients;