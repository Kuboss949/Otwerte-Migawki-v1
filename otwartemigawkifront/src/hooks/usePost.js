import { useState } from 'react';
import axios from 'axios';

const usePost = () => {
  const [popupMessage, setPopupMessage] = useState('');
  const [responseSuccess, setResponseSuccess] = useState(false);
  const [showPopup, setShowPopup] = useState(false);

  const handlePost = async (endpoint, requestBody, isFormData = false) => {
    try {
      let headers = {};
      if (isFormData) {
        headers['Content-Type'] = 'multipart/form-data';
      }
      const response = await axios.post('/api/' + endpoint, requestBody, 
      { headers });

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
