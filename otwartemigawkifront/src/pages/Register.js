import React from 'react';
import {InputBox} from '../components/InputBox.js';
import "../css/Login.css";



const Register = () => {
  return (
    <div className='login-page flex-centered'>
        <img src='images/logo.png' alt='logo'/>
        <form className='flex-centered login-form' method='POST' action='/login'>
        <InputBox label='Email' name='email'/>
        <InputBox label='Nr telefonu' name='phone'/>
        <InputBox label='Hasło' name='password' type='password'/>
        <InputBox label='Powtórz hasło' name='re-password' type='password'/>
        <a href="/login" className='login-page-link'>Masz już konto?</a>
        <button className='site-button'>Zarejestruj</button>
        </form>
      </div>
  );
}

export default Register;