import axios from 'axios';

export const handleRegister = async (email, password, name, surname, phone, setPopupMessage, setShowPopup) => {
  const requestBody = {
    email: email,
    password: password,
    name: name,
    surname: surname,
    phone: phone,
    isTmp: false
  };

  try {
    const response = await axios.post('/api/auth/register', requestBody);
    if (response.status === 200) {
      console.log('Registration successful');
      const message = response.data;
      setPopupMessage(message);
      setShowPopup(true);
    } else {
      console.error('Registration failed');
      const message = response.data;
      setPopupMessage(message);
      setShowPopup(true);
    }
  } catch (error) {
    console.error('Error occurred during registration:', error);
  }
};
