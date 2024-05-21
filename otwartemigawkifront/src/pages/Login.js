import React, { useState } from 'react';
import { InputBox } from '../components/InputBox.js';
import Popup from '../components/Popup.js';
import "../css/Login.css";
import usePost from '../hooks/usePost.js';


const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { popupMessage, responseSuccess, showPopup, handlePost, closePopup } = usePost();


  const handleSubmit = async (e) => {
    e.preventDefault();
    const requestBody = {
      email: email,
      password: password
    };
    await handlePost('auth/login', requestBody);
    localStorage.clear();
    localStorage.setItem("loggedIn", true);
  };

  if(responseSuccess){
    setTimeout(function() {
      window.location.href = '/';
    }, 3000);
  }

  return (
    <div className='login-page flex-centered'>
      <img src='images/logo.png' alt='logo'/>
      <form className='flex-centered login-form' id='login-form' onSubmit={handleSubmit}>
        <InputBox label='Email' name='email' value={email} onChange={(e) => setEmail(e.target.value)} />
        <InputBox label='Hasło' name='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
        <a href="/rejestracja" className='login-page-link'>Nie masz konta?</a>
        <button type="submit" className='site-button'>Zaloguj</button>
      </form>
      <Popup
        showPopup={showPopup}
        popupMessage={popupMessage}
        responseSuccess={responseSuccess}
        onClose={closePopup}
      />
    </div>
  );
}

export default Login;