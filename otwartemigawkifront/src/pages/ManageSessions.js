import React, { useState, useEffect, useMemo } from 'react';
import { InputBox } from '../components/InputBox.js';
import AppBar from '../components/AppBar.js';
import "../css/ManageSessions.css";
import { useTable } from 'react-table';
import { fetchData } from '../api/GetApi.js';
import usePost from '../hooks/usePost.js';
import Popup from '../components/Popup.js';

const ManageSessions = () => {
  const [sessionDetails, setSessionDetails] = useState([]);
  const [upcomingSessions, setUpcomingSessions] = useState([]);
  const [loading, setLoading] = useState(true);
  const { popupMessage, responseSuccess, showPopup, handlePost, closePopup } = usePost();
  const [newSessionTypeName, setNewSessionTypeName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState(null);
  const [photo, setPhoto] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('sessionTypeName', newSessionTypeName);
    formData.append('description', description);
    formData.append('price', price);
    formData.append('photo', photo);

    await handlePost('session/add', formData, true);
  };

  const handleFileChange = (e) => {
    setPhoto(e.target.files[0]);
  };

  useEffect(() => {
    const fetchSessionDetails = async () => {
      try {
        const sessionData = await fetchData('/api/session/all');
        const upcomingData = await fetchData('/api/session/all-upcoming');
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
          <form className='flex-centered' method='POST' onSubmit={handleSubmit} encType="multipart/form-data">
            <InputBox 
              label='Nazwa sesji' 
              name='session-name'
              onChange={(e) => setNewSessionTypeName(e.target.value)}
            />
            <InputBox 
              label='Opis' 
              name='session-desc'
              onChange={(e) => setDescription(e.target.value)}
            />
            <InputBox 
              label='Cena' 
              name='price'
              onChange={(e) => setPrice(e.target.value)}
            />
            <input type="file" name="photo" onChange={handleFileChange}></input>
            <button type='submit' className='site-button'>Dodaj</button>
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
      <Popup
        showPopup={showPopup}
        popupMessage={popupMessage}
        responseSuccess={responseSuccess}
        onClose={closePopup}
      />
    </div>
  );
};

export default ManageSessions;