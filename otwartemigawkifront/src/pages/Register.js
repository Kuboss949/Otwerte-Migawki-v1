import React, { useState } from 'react';
import { handleRegister } from '../api/RegisterApi';
import { InputBox } from '../components/InputBox.js';
import "../css/Login.css";
import { isValidEmail, isValidPhoneNumber, isValidName, isValidPassword} from '../validationFunc.js';

const Register = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [repassword, setRepassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
  const [popupMessage, setPopupMessage] = useState('');
  const [showPopup, setShowPopup] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await handleRegister(email, password, name, surname, phone, setPopupMessage, setShowPopup);
  };

  return (
    <div className='login-page flex-centered'>
      <img src='images/logo.png' alt='logo'/>
      <form className='flex-centered login-form' method='POST' onSubmit={handleSubmit}>   
        <div className='flex-centered register-form-main'>
          <div className='flex-centered register-form-column'>
            <InputBox label='Imię' name='name' value={name} onChange={(e) => setName(e.target.value)} validator={isValidName} validationMsg="To pole nie powinno być puste"/>
            <InputBox label='Nazwisko' name='surname' value={surname} onChange={(e) => setSurname(e.target.value)} validator={isValidName} validationMsg="To pole nie powinno być puste"/>
            <InputBox label='Nr telefonu' name='phone' value={phone} onChange={(e) => setPhone(e.target.value)} validator={isValidPhoneNumber} validationMsg="Wprowadź poprawny numer"/>
          </div>
          <div className='flex-centered register-form-column'>
            <InputBox label='Email' name='email' value={email} onChange={(e) => setEmail(e.target.value)} validator={isValidEmail} validationMsg="Wprowadź poprawny adres"/>
            <InputBox label='Hasło' name='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} validator={isValidPassword} validationMsg="Hasło powinno składać się z co najmniej 8 znaków, zawierać małe i duże litery oraz cyfry"/>
            <InputBox label='Powtórz hasło' name='re-password' type='password' onChange={(e) => setRepassword(e.target.value)}/>
          </div>
        </div>
        <a href="/login" className='login-page-link'>Masz już konto?</a>
        <button type="submit" className='site-button'>Zarejestruj</button>
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

export default Register;
