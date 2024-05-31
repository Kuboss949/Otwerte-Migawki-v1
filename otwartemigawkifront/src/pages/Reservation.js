import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import AppBar from '../components/AppBar.js';
import { InputBox, SelectBox, Checkbox } from '../components/InputBox.js';
import "../css/Reservation.css";
import { fetchData } from '../api/GetApi.js';
import { isValidName, isValidPhoneNumber, updateDisableSubmit } from '../validationFunc.js';
import Popup from '../components/Popup.js';
import usePost from '../hooks/usePost.js';

const Reservation = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [chosenDate, setChosenDate] = useState(new Date());
  const [selectedHour, setSelectedHour] = useState(null);
  const [showAdditionalFields, setShowAdditionalFields] = useState(false); // State to track checkbox status
  const [isAuthenticated, setIsAuthenticated] = useState(false); // State to track authentication status
  const [sessionTypes, setSessionTypes] = useState([]);
  const [availableDates, setAvailableDates] = useState([]);
  const [datesTimes, setDatesTimes] = useState([]);
  const [selectedSessionType, setSelectedSessionType] = useState('notValue');
  const [timesForCurrentDate, setTimesForCurrentDate] = useState([]);
  const [disableSubmit, setDisableSubmit] = useState(true);
  const [tmpName, setTmpName] = useState('');
  const [tmpSurname, setTmpSurname] = useState('');
  const [tmpPhone, setTmpPhone] = useState('');
  const { popupMessage, responseSuccess, showPopup, handlePost, closePopup } = usePost();
  
  
  
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    const tzoffset = (new Date()).getTimezoneOffset() * 60000;
    const chosenDateISO = new Date(chosenDate - tzoffset).toISOString().split('T')[0];
    const requestBody = {
      isRegistered: localStorage.getItem("loggedIn"),
      date: chosenDateISO,
      hour: selectedHour,
      sessionTypeName: selectedSessionType,
      name: tmpName,
      surname: tmpSurname,
      phone: tmpPhone
    };
    console.log(requestBody);
    console.log(requestBody.isRegistered);
    await handlePost('reservation/add', requestBody);
  };

  useEffect(() => {
    const fetchDataFromApi = async () => {
      try {
        const result = await fetchData('/api/session/all-enabled');
        setSessionTypes(result.map(session => session.sessionTypeName));
        const result2 = await fetchData('/api/session/fetchTimes');
        setAvailableDates(result2);
        //setIsLoading(false);
      } catch (error) {
        setSessionTypes(["Nie udało się połączyć z serwerem"]);
      }
    };
    fetchDataFromApi();
  }, []);

  useEffect(() => {
    const isLoggedIn = localStorage.getItem('loggedIn');
    if (isLoggedIn === "true") {
      setIsAuthenticated(true);
    }
  }, []);

  useEffect(() => {
    if(chosenDate.getDate() === new Date().getDate() 
      || selectedSessionType === "notValue"
      || selectedHour === null
    ){
      setDisableSubmit(true);
      return;
    }
    if(!isAuthenticated){
      updateDisableSubmit(setDisableSubmit, 'unregistered-form');
      return;
    }
      setDisableSubmit(false);
  },[chosenDate, selectedSessionType, selectedHour, isAuthenticated])

  const isSameDate = (d1, d2) => {
    return (
      d1.getFullYear() === d2.getFullYear() &&
      d1.getMonth() === d2.getMonth() &&
      d1.getDate() === d2.getDate()
    );
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
    const foundRow = datesTimes.find(session => {
      const sessionDate = new Date(session.date);
      return isSameDate(sessionDate, date);
    });
    setTimesForCurrentDate(foundRow.times);
  };

  const tileDisabled = ({ activeStartDate, date, view }) => {
    const availableDatesArray = datesTimes.map(session => new Date(session.date));
    
    if (view === 'month') {
      const isAvailable = availableDatesArray.some(availableDate =>
        availableDate.getFullYear() === date.getFullYear() &&
        availableDate.getMonth() === date.getMonth() &&
        availableDate.getDate() === date.getDate()
      );

      return !isAvailable || date < new Date();
    }
    return false;
  };

  const handleHourClick = (hour) => {
    setSelectedHour(hour);
    setChosenDate(selectedDate);
    console.log(hour);
  };

  const handleCheckboxChange = () => {
    setShowAdditionalFields(!showAdditionalFields); // Toggle the checkbox status
  };

  const handleLoginRedirect = () => {
    window.location.href = '/';
  };

  const handleSessionChange = (event) => {
    const newSessionType = event.target.value;
    setSelectedSessionType(newSessionType);
    setChosenDate(new Date());
    setSelectedHour(null);
    const sessionData = availableDates.find(session => session.sessionTypeName === newSessionType);
    if (sessionData) {
      setDatesTimes(sessionData.availableDates.map(date => ({
        date: date.date,
        times: date.times
      })));
    } else {
      setDatesTimes([]);
    }
    console.log(datesTimes);
  }

  return (
    <div>
      <AppBar />
      <div id="main-section">
        <div className='sub-section'>
          <h2>Twoje dane</h2>
          {!isAuthenticated ? (
            <>
              <button className='site-button' onClick={handleLoginRedirect}>Zaloguj</button>
              <Checkbox label="Bez rejestracji" name="anonymous" onChange={handleCheckboxChange} />
              {showAdditionalFields && (
                <form id="unregistered-form">
                  <SelectBox label="Rodzaj sesji" name="session-type" options={sessionTypes} onChange={handleSessionChange} />
                  <InputBox 
                  label="Imię" 
                  name="name" 
                  onChange={setTmpName}
                  validator={isValidName}
                  validationMsg="To pole nie powinno być puste"
                  />
                  <InputBox 
                  label="Nazwisko" 
                  name="surname"
                  onChange={setTmpSurname}
                  validator={isValidName}
                  validationMsg="To pole nie powinno być puste" 
                  />
                  <InputBox 
                  label="Nr Telefonu" 
                  name="phone-number"
                  onChange={setTmpPhone}
                  validator={isValidPhoneNumber}
                  validationMsg="Wprowadź poprawny numer telefonu"
                   />
                </form>
              )}
            </>
          ) : (
            <SelectBox label="Rodzaj sesji" name="session-type" options={sessionTypes} onChange={handleSessionChange} />
          )}
          <button className='site-button reservation-button' disabled={disableSubmit} onClick={handleSubmit}>Zarezerwuj</button>
        </div>
        <div className='sub-section'>
          <h2>Data</h2>
          <Calendar
            className='om-calendar'
            next2Label={null}
            prev2Label={null}
            onChange={handleDateChange}
            value={selectedDate}
            tileDisabled={tileDisabled}
            tileClassName={({ date }) =>
              isSameDate(date, chosenDate) ? 'highlighted-date' : null
            }
          />
        </div>
        <div className='sub-section'>
          <h2>Godzina</h2>
          {
            timesForCurrentDate.map(time => 
              <button
                className={selectedHour === time && isSameDate(selectedDate, chosenDate) ? 'hour-button clicked-button' : 'hour-button'}
                onClick={() => handleHourClick(time)}
                key={time}
              >
                {time}:00
              </button>
            )
          } 
        </div>
      </div>
      <Popup
        showPopup={showPopup}
        popupMessage={popupMessage}
        responseSuccess={responseSuccess}
        onClose={closePopup}
      />
    </div>
  );
};

export default Reservation;