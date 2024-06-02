import React, { useState, useEffect } from 'react';
import { InputBox } from '../components/InputBox.js';
import AppBar from '../components/AppBar.js';
import "../css/ManageSessions.css";
import usePost from '../hooks/usePost.js';
import Popup from '../components/Popup.js';
import axios from 'axios';
import SessionTable from '../components/tables/SessionTable';
import UpcomingSessionsTable from '../components/tables/UpcomingSessionsTable';
import { fetchAllManageData } from '../api/manage-session-api.js';

const ManageSessions = () => {
  const [sessionDetails, setSessionDetails] = useState([]);
  const [upcomingSessions, setUpcomingSessions] = useState([]);
  const [loading, setLoading] = useState(true);
  const { popupMessage, responseSuccess, showPopup, handlePost, closePopup } = usePost();
  const [newSessionTypeName, setNewSessionTypeName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState(null);
  const [photo, setPhoto] = useState(null);

  useEffect(() => {
    fetchAllManageData(setSessionDetails, setUpcomingSessions, setLoading);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('sessionTypeName', newSessionTypeName);
    formData.append('description', description);
    formData.append('price', price);
    formData.append('photo', photo);
    await handlePost('session/add', formData, true);
    fetchAllManageData(setSessionDetails, setUpcomingSessions, setLoading);
  };

  const handleFileChange = (e) => {
    setPhoto(e.target.files[0]);
  };

  const handleToggle = async (id) => {
    await handlePost('session/change-disable-session-state/' + id, {});
    fetchAllManageData(setSessionDetails, setUpcomingSessions, setLoading);
  };

  const handleDeleteSession = async (id) => {
    try {
      await axios.delete('/api/session/cancel-session/' + id);
      fetchAllManageData(setSessionDetails, setUpcomingSessions, setLoading);
    } catch (error) {
      console.error("There was an error deleting the session type!", error);
    }
  };

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
            <SessionTable data={sessionDetails} handleToggle={handleToggle} />
          )}
        </div>
      </div>
      <div className='manage-session-section'>
        <h1 className='flex-centered site-header'>Nadchodzące sesje</h1>
        <div className='flex-centered'>
          {loading ? (
            <p>Loading...</p>
          ) : (
            <UpcomingSessionsTable data={upcomingSessions} handleDeleteSession={handleDeleteSession} />
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
