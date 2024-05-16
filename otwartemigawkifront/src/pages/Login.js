import React, { useState } from 'react';
import { InputBox } from '../components/InputBox.js';
import { handlePost } from '../api/PostApi.js';
import "../css/Login.css";


const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [popupMessage, setPopupMessage] = useState('');
  const [responseSuccess, setResponseSuccess] = useState(false);
  const [showPopup, setShowPopup] = useState(false);



  const handleSubmit = async (e) => {
    e.preventDefault();
    const requestBody = {
      email: email,
      password: password
    };
    await handlePost('auth/login', requestBody, setPopupMessage, setResponseSuccess, setShowPopup);
  };

  return (
    <div className='login-page flex-centered'>
      <img src='images/logo.png' alt='logo'/>
      <form className='flex-centered login-form' id='login-form' onSubmit={handleSubmit}>
        <InputBox label='Email' name='email' value={email} onChange={(e) => setEmail(e.target.value)} />
        <InputBox label='Hasło' name='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
        <a href="/rejestracja" className='login-page-link'>Nie masz konta?</a>
        <button type="submit" className='site-button'>Zaloguj</button>
      </form>
      {showPopup && (
        <div className={`popup ${responseSuccess ? 'success' : 'failed'}`}>
          {/* Display popup message */}
          <p>{popupMessage}</p>
          <button className='site-button' onClick={() => setShowPopup(false)}>Zamknij</button>
        </div>
      )}
    </div>
  );
}

export default Login;