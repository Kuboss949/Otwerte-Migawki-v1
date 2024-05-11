import React, { useState } from 'react';
import axios from 'axios';
import { InputBox } from '../components/InputBox.js';
import "../css/Login.css";

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();

    const requestBody = {
      email: document.querySelector('#email').value,
      password: document.querySelector('#password').value
    };

    try {
      console.log(requestBody);
      const response = await axios.post('http://localhost:8080/api/auth/login', requestBody);
      
      if (response.status === 200) {
        // Successful login, do something (redirect, show success message, etc.)
        console.log('Login successful');
        //window.location.href = "/";
      } else {
        // Login failed, handle error (show error message, etc.)
        console.error('Login failed');
      }
    } catch (error) {
      console.error('Error occurred:', error);
    }
  };

  return (
    <div className='login-page flex-centered'>
      <img src='images/logo.png' alt='logo'/>
      <form className='flex-centered login-form' id='login-form' onSubmit={handleLogin}>
        <InputBox label='Email' name='email' value={email} onChange={(e) => setEmail(e.target.value)} />
        <InputBox label='Hasło' name='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
        <a href="/rejestracja" className='login-page-link'>Nie masz konta?</a>
        <button type="submit" className='site-button'>Zaloguj</button>
      </form>
    </div>
  );
}

export default Login;