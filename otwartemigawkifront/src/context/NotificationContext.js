import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const NotificationContext = createContext();

export const NotificationProvider = ({ children, username }) => {
  const [messages, setMessages] = useState([]);
  const [showNotification, setShowNotification] = useState(false);
  const [latestMessage, setLatestMessage] = useState(null);

  useEffect(() => {
    const fetchMessages = async () => {
        try {
          const response = await axios.get(`/api/notifications/${username}`);
          if (response.data !== undefined && response.data !== '') {
            setMessages((prevMessages) => [...prevMessages, response.data]);
            setLatestMessage(response.data);
            setShowNotification(true);
            
          }
        } catch (error) {
          console.error('Error fetching messages:', error);
        }
      };

    // Set up polling every 5 seconds
    const intervalId = setInterval(fetchMessages, 5000);

    // Clean up interval on component unmount
    return () => clearInterval(intervalId);
  }, [username]);

  const closeNotification = () => {
    setShowNotification(false);
    setLatestMessage(null);
  };

  return (
    <NotificationContext.Provider value={{ messages, showNotification, latestMessage, closeNotification }}>
      {children}
    </NotificationContext.Provider>
  );
};
