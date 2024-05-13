import axios from 'axios';

export const handleLogin = async (email, password, setPopupMessage, setShowPopup) => {
  const requestBody = {
    email: email,
    password: password
  };

  try {
    const response = await axios.post('/api/auth/login', requestBody);
    if (response.status === 200) {
      const message = response.data;
      setPopupMessage(message);
      setShowPopup(true);
    } else {
      console.error('Login failed');
      const message = response.data;
      setPopupMessage(message);
      setShowPopup(true);
    }
  } catch (error) {
    console.error('Error occurred during registration:', error);
  }
};
