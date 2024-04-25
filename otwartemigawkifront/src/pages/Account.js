import React from 'react';
import AppBar from '../components/AppBar.js';
import {InputBox} from '../components/InputBox.js';
import "../css/Account.css";


const Account = () => {
  return (
    <div>
    <AppBar />
    <h1 className='flex-centered site-header'>Twoje dane</h1>
    <div className='account-forms-div'>
      <form className='account-form' method='POST'>
      <InputBox label='Imię' name='name' placeholder='Jan' />
      <InputBox label='Nazwisko' name='surname' placeholder='Kowalski' />
      <InputBox label='Email' name='email' placeholder='example@example.com' />
      <InputBox label='Nr telefonu' name='phone' placeholder='111 222 333' />
      <button className='site-button'>Zmień</button>
      <button className='site-button'>Usuń konto</button>
      </form>
      <form className='account-form' method='POST'>
      <InputBox label='Stare hasło' name='old-password' type='password' />
      <InputBox label='Nowe hasło' name='new-password' type='password' />
      <button className='site-button'>Zmień hasło</button>
      </form>
    </div>
    </div>
  );
}



export default Account;