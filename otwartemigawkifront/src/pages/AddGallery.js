// AddGallery.js

import React, { useState, useEffect } from 'react';
import AppBar from '../components/AppBar.js';
import { SelectBox } from '../components/InputBox.js';
import "../css/AddGallery.css";
import { fetchData } from '../api/GetApi.js';
import DropzoneComponent from '../components/DropzoneComponent.js';
import usePost from '../hooks/usePost.js';
import Popup from '../components/Popup.js';

const AddGallery = () => {
  const [clientsList, setClientsList] = useState([]);
  const [sessionsList, setSessionsList] = useState([]);
  const [mergedData, setMergedData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [client, setClient] = useState('');
  const [sessionId, setSessionId] = useState(null);
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [galleryName, setGalleryName] = useState('');
  const { popupMessage, responseSuccess, showPopup, handlePost, closePopup } = usePost();
  

  useEffect(() => {
    const fetchClientDetails = async () => {
      try {
        const data = await fetchData('/api/session/all-without-galleries');
        const mappedData = mapSessions(data);
        setMergedData(mappedData);
        setClientsList(mappedData.map(data => data.client));
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchClientDetails();
  }, []);

  function mapSessions(inputArray) {
    const result = inputArray.reduce((acc, item) => {
      const clientKey = `${item.clientName} ${item.clientSurname} (${item.clientPhone})`;
      if (!acc[clientKey]) {
        acc[clientKey] = {
          client: clientKey,
          sessions: []
        };
      }
      acc[clientKey].sessions.push({ id: item.id, sessionTypeName: item.sessionTypeName, date: item.date });
      return acc;
    }, {});

    return Object.values(result);
  }

  const handleClientChange = (e) => {
    const clientName = e.target.value;
    setClient(clientName);

    const clientData = mergedData.find(item => item.client === clientName);

    if (clientData) {
      let newSessionList = clientData.sessions;
      newSessionList = newSessionList.map(item => item.sessionTypeName + " " + new Date(item.date).toISOString().slice(0, 10));
      setSessionsList(newSessionList);
      setSessionId(null);
    } else {
      setSessionsList([]);
      setSessionId(null);
    }
  };

  const handleUploadedFilesChange = (files) => {
    setUploadedFiles(files);
  };
  const handleSessionChange = (e) => {
    const sessionName = e.target.value.slice(0, -11); //without session date space + 10 characters
    setGalleryName(sessionName);
    const clientData = mergedData.find(item => item.client === client);
    const sessionData = clientData.sessions.find(item => item.sessionTypeName === sessionName);
    const id = sessionData.id;
    setSessionId(id);
  };

  if (loading) {
    return (<div>Loading...</div>);
  }

  const handleSubmit = async (e) => {
    console.log(client)
    console.log(sessionId)
    if (client !== '' && sessionId !== null) {
      e.preventDefault();
      const formData = new FormData();
      formData.append('sessionId', sessionId);
      formData.append('galleryName', galleryName);
      uploadedFiles.forEach((file, index) => {
        formData.append(`files[${index}]`, file); 
      });

      await handlePost('gallery/add', formData, true);
    }
  };

  return (
    <div>
      <AppBar />
      <div className='flex-column-centered'>
        <h1 className='flex-centered site-header'>Dodaj galerię</h1>
        <form className='flex-centered add-gallery-form'>
          <SelectBox label='Imię i nazwisko klienta' name='client-data' options={clientsList} onChange={handleClientChange} />
          <SelectBox label='Sesja' name='client-data' options={sessionsList} onChange={handleSessionChange} />
        </form>
        <button className='site-button' onClick={handleSubmit}>Dodaj galerię</button>
        <div className='flex-centered'>
          <DropzoneComponent uploadedFiles={uploadedFiles} setUploadedFiles={handleUploadedFilesChange} />
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
}

export default AddGallery;
