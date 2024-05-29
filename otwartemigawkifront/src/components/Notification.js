import React, { useContext } from 'react';
import { NotificationContext } from '../context/NotificationContext';
import './components-css/Notification.css'; // Add styles for your notification popup

const NotificationPopup = () => {
    const { showNotification, latestMessage, closeNotification } = useContext(NotificationContext);
  
  
    if (!showNotification || !latestMessage || latestMessage === '') return null;
  
    return (
      <div className="notification-popup flex-column-centered">
        <p>{latestMessage}</p>
        <button onClick={closeNotification}>Zamknij</button>
      </div>
    );
  };

export default NotificationPopup;
