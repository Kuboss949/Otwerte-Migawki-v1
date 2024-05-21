import axios from 'axios';

export const handlePost = async (endpoint, requestBody, setPopupMessage, setResponseSuccess, setShowPopup) => {
    try {
    const response = await axios.post('/api/' + endpoint, requestBody);
    const message = response.data;
    setPopupMessage(message.message);
    setResponseSuccess(message.success)
    setShowPopup(true);
  } catch (error) {
    setPopupMessage("Wystąpił problem: " + error);
    setResponseSuccess(false)
    setShowPopup(true);
  }
};