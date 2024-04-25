import React from 'react';
import {InputBox} from '../components/InputBox.js';
import "../css/Login.css";



const Login = () => {
  return (
    <div className='login-page flex-centered'>
        <img src='images/logo.png' alt='logo'/>
        <form className='flex-centered login-form' method='POST' action='/login'>
        <InputBox label='Email' name='email'/>
        <InputBox label='Hasło' name='password' type='password'/>
        <a href="/rejestracja" className='login-page-link'>Nie masz konta?</a>
        <button className='site-button'>Zaloguj</button>
        </form>
      </div>
  );
}

export default Login;