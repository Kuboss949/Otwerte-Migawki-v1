import React, { useState } from 'react';
import { InputBox } from '../components/InputBox.js';
import { handleLogin } from '../api/LoginApi';
import "../css/Login.css";


const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [popupMessage, setPopupMessage] = useState('');
  const [showPopup, setShowPopup] = useState(false);



  const handleSubmit = async (e) => {
    e.preventDefault();
    await handleLogin(email, password, setPopupMessage, setShowPopup);
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
        <div className="popup">
          {/* Display popup message */}
          <p>{popupMessage}</p>
          <button className='site-button' onClick={() => setShowPopup(false)}>Zamknij</button>
        </div>
      )}
    </div>
  );
}

export default Login;