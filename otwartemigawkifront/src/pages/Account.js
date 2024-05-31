import { React, useState, useEffect } from 'react';
import AppBar from '../components/AppBar.js';
import { InputBox } from '../components/InputBox.js';
import "../css/Account.css";
import { handlePost } from '../api/PostApi.js';
import { fetchUserInfo } from '../api/AccountApi.js';
import LoadingScreen from '../components/LoadingScreen';
import { updateDisableSubmit, isValidEmail, isValidPhoneNumber, isValidName, isValidPassword } from '../validationFunc.js';

const Account = () => {

  const [userData, setUserData] = useState({});
  const [email, setEmail] = useState('');
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
  const [showPopup, setShowPopup] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');
  const [responseSuccess, setResponseSuccess] = useState(false);
  const [disableSubmit, setDisableSubmit] = useState(true);
  const [disablePasswordSubmit, setDisablePasswordSubmit] = useState(true);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchUserInfo();
        setUserData(data);
        setEmail(data.email);
        setName(data.name);
        setSurname(data.surname);
        setPhone(data.phone);
        setIsLoading(false);
      } catch (error) {
        console.error('Error fetching info:', error);
      }
    };
    fetchData();
  }, []);


  useEffect(() => {
    updateDisableSubmit(setDisableSubmit, 'detail-form');
  }, [email, name, surname, phone]);
  useEffect(() => {
    updateDisableSubmit(setDisablePasswordSubmit, 'password-form');
  }, [newPassword, oldPassword]);

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    const requestBody = {
      id: userData.id,
      email: email,
      password: '',
      name: name,
      surname: surname,
      phone: phone,
      isTmp: false
    };
    await handlePost('clients/update-user-info', requestBody, setPopupMessage, setResponseSuccess, setShowPopup);
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();
    const requestBody = {
      newPassword: newPassword,
      oldPassword: oldPassword
    };
    await handlePost('clients/update-user-password', requestBody, setPopupMessage, setResponseSuccess, setShowPopup);
  };

  const logout = async (e) => {
    await handlePost('auth/logout', {}, setPopupMessage, setResponseSuccess, setShowPopup)
    localStorage.clear();
    localStorage.setItem("loggedIn", false);
    setTimeout(function() {
       window.location.href = '/login';
    }, 3000);
  };

  if (isLoading) {
    return <LoadingScreen />;
  }

  return (
    <div>
      <AppBar />
      <h1 className='flex-centered site-header'>Twoje dane</h1>
      <div className='account-forms-div'>
        <form id='detail-form' className='account-form' method='POST' onSubmit={handleUpdateSubmit}>
          <InputBox label='Imię' name='name' placeholder={userData.name} onChange={(e) => setName(e.target.value)} validator={isValidName} validationMsg="To pole nie powinno być puste" />
          <InputBox label='Nazwisko' name='surname' placeholder={userData.surname} onChange={(e) => setSurname(e.target.value)} validator={isValidName} validationMsg="To pole nie powinno być puste" />
          <InputBox label='Email' name='email' placeholder={userData.email} onChange={(e) => setEmail(e.target.value)} validator={isValidEmail} validationMsg="Wprowadź poprawny adres" />
          <InputBox label='Nr telefonu' name='phone' placeholder={userData.phone} onChange={(e) => setPhone(e.target.value)} validator={isValidPhoneNumber} validationMsg="Wprowadź poprawny numer" />
          <button type='submit' className='site-button' disabled={disableSubmit}>Zmień</button>
          <button className='site-button'>Usuń konto</button>
        </form>
        <form id='password-form' className='account-form' method='POST' onSubmit={handlePasswordSubmit}>
          <InputBox label='Stare hasło' name='old-password' type='password' onChange={(e) => setOldPassword(e.target.value)} validator={isValidPassword} validationMsg="Hasło powinno składać się z co najmniej 8 znaków, zawierać małe i duże litery oraz cyfry" />
          <InputBox label='Nowe hasło' name='new-password' type='password' onChange={(e) => setNewPassword(e.target.value)} validator={isValidPassword} validationMsg="Hasło powinno składać się z co najmniej 8 znaków, zawierać małe i duże litery oraz cyfry" />
          <button type='submit' className='site-button' disabled={disablePasswordSubmit}>Zmień hasło</button>
          <button type='button' className='site-button' onClick={logout}>Wyloguj</button>
        </form>
        
      </div>
      {showPopup && (
        <div className={`popup ${responseSuccess ? 'success' : 'failed'}`}>
          {/* Display popup message */}
          <p>{popupMessage}</p>
          <button className='site-button' onClick={() => setShowPopup(false)} >Zamknij</button>
        </div>
      )}
    </div>
  );
}



export default Account;