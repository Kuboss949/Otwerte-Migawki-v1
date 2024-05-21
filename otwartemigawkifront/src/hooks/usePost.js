import { useState } from 'react';
import axios from 'axios';

const usePost = () => {
  const [popupMessage, setPopupMessage] = useState('');
  const [responseSuccess, setResponseSuccess] = useState(false);
  const [showPopup, setShowPopup] = useState(false);

  const handlePost = async (endpoint, requestBody) => {
    try {
      const response = await axios.post('/api/' + endpoint, requestBody);
      const message = response.data;
      setPopupMessage(message.message);
      setResponseSuccess(message.success);
      setShowPopup(true);
    } catch (error) {
      setPopupMessage("Wystąpił problem: " + error);
      setResponseSuccess(false);
      setShowPopup(true);
    }
  };

  const closePopup = () => {
    setShowPopup(false);
  };

  return {
    popupMessage,
    responseSuccess,
    showPopup,
    handlePost,
    closePopup,
  };
};

export default usePost;
