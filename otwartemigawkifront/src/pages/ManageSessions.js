import React, { useState, useMemo, useEffect } from 'react';
import { InputBox } from '../components/InputBox.js';
import AppBar from '../components/AppBar.js';
import "../css/ManageSessions.css";
import { useTable } from 'react-table';
import { fetchData } from '../api/GetApi.js';

const ManageSessions = () => {
  const [sessionDetails, setSessionDetails] = useState([]);
  const [upcomingSessions, setUpcomingSessions] = useState([]); // State for the second table
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSessionDetails = async () => {
      try {
        const sessionData = await fetchData('/api/session/all');
        const upcomingData = await fetchData('/api/session/all-upcoming'); // Fetch data for the second table
        setSessionDetails(sessionData);
        setUpcomingSessions(upcomingData);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchSessionDetails();
  }, []);

  const sessionColumns = useMemo(() => [
    {
      Header: "ID",
      accessor: "sessionTypeId",
    },
    {
      Header: "Nazwa sesji",
      accessor: "sessionTypeName",
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
  ], []);

  const upcomingColumns = useMemo(() => [
    {
      Header: "ID",
      accessor: "id",
    },
    {
      Header: "Nazwa sesji",
      accessor: "sessionTypeName",
    },
    {
      Header: "Data",
      accessor: "date",
    },
    {
      Header: "Imię",
      accessor: "clientName",
    },
    {
      Header: "Nazwisko",
      accessor: "clientSurname",
    },
    {
      Header: "Nr telefonu",
      accessor: "clientPhone",
    },
    {
      Header: "Akcje",
      Cell: ({ row }) => (
        <button className='deleteButton' onClick={() => handleDelete(row.original.id)}>Odwołaj sesje</button>
      )
    },
  ], []);

  const handleDelete = (id) => {
    console.log("Delete button clicked for ID:", id);
  };

  const {
    getTableProps: getSessionTableProps,
    getTableBodyProps: getSessionTableBodyProps,
    headerGroups: sessionHeaderGroups,
    rows: sessionRows,
    prepareRow: prepareSessionRow
  } = useTable({
    columns: sessionColumns,
    data: sessionDetails || [],
  });

  const {
    getTableProps: getUpcomingTableProps,
    getTableBodyProps: getUpcomingTableBodyProps,
    headerGroups: upcomingHeaderGroups,
    rows: upcomingRows,
    prepareRow: prepareUpcomingRow
  } = useTable({
    columns: upcomingColumns,
    data: upcomingSessions || [],
  });

  return (
    <div>
      <AppBar />
      <div className='manage-session-section'>
        <h1 className='flex-centered site-header'>Sesje</h1>
        <div className='add-session flex-centered'>
          <form className='flex-centered' method='POST'>
            <InputBox label='Nazwa sesji' name='session-name' />
            <InputBox label='Cena' name='price' />
            <button className='site-button'>Dodaj</button>
          </form>
        </div>
        <div className='flex-centered'>
          {loading ? (
            <p>Loading...</p>
          ) : (
            <table {...getSessionTableProps()}>
              <thead>
                {sessionHeaderGroups.map((headerGroup) => (
                  <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map((column) => (
                      <th {...column.getHeaderProps()}>
                        {column.render("Header")}
                      </th>
                    ))}
                  </tr>
                ))}
              </thead>
              <tbody {...getSessionTableBodyProps()}>
                {sessionRows.map((row) => {
                  prepareSessionRow(row);
                  return (
                    <tr {...row.getRowProps()}>
                      {row.cells.map((cell) => (
                        <td {...cell.getCellProps()}>
                          {cell.render("Cell")}
                        </td>
                      ))}
                    </tr>
                  );
                })}
              </tbody>
            </table>
          )}
        </div>
      </div>
      <div className='manage-session-section'>
        <h1 className='flex-centered site-header'>Nadchodzące sesje</h1>
        <div className='flex-centered'>
          {loading ? (
            <p>Loading...</p>
          ) : (
            <table {...getUpcomingTableProps()}>
              <thead>
                {upcomingHeaderGroups.map((headerGroup) => (
                  <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map((column) => (
                      <th {...column.getHeaderProps()}>
                        {column.render("Header")}
                      </th>
                    ))}
                  </tr>
                ))}
              </thead>
              <tbody {...getUpcomingTableBodyProps()}>
                {upcomingRows.map((row) => {
                  prepareUpcomingRow(row);
                  return (
                    <tr {...row.getRowProps()}>
                      {row.cells.map((cell) => (
                        <td {...cell.getCellProps()}>
                          {cell.render("Cell")}
                        </td>
                      ))}
                    </tr>
                  );
                })}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
};

export default ManageSessions;
