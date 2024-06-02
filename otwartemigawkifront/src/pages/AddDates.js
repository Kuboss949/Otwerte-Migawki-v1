import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import "../css/AddDates.css";
import AppBar from '../components/AppBar';
import { SelectBox } from '../components/InputBox';
import LoadingScreen from '../components/LoadingScreen';
import Popup from '../components/Popup.js';
import usePost from '../hooks/usePost.js';
import { fetchDatesData } from '../api/add-dates-api.js';

const AddDates = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [datesTimes, setDatesTimes] = useState([]);
  const [sessionTypes, setSessionTypes] = useState([]);
  const [availableDates, setAvailableDates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [lastSelectedSession, setLastSelectedSession] = useState('');
  const { popupMessage, responseSuccess, showPopup, handlePost, closePopup } = usePost();

  useEffect(() => {
    fetchDatesData(setSessionTypes, setAvailableDates, setLoading);
  }, []);

  const handleUpdateDates = async (e) => {
    if (lastSelectedSession !== '') {
      e.preventDefault();
      const requestBody = {
        sessionTypeName: lastSelectedSession,
        availableDates: datesTimes.filter(item => item.times.length > 0)
      };
      await handlePost('/session/addTimes', requestBody);
    }
  };

  const handleSessionChange = (event) => {
    if (lastSelectedSession !== '') {
      const lastSessionData = availableDates.find(session => session.sessionTypeName === lastSelectedSession);
      lastSessionData.availableDates = datesTimes.filter(item => item.times.length > 0);
    }
    const selectedSessionType = event.target.value;
    const sessionData = availableDates.find(session => session.sessionTypeName === selectedSessionType);
    if (sessionData) {
      setDatesTimes(sessionData.availableDates.map(date => ({
        date: date.date,
        times: date.times
      })));
      setLastSelectedSession(selectedSessionType);
    } else if (selectedSessionType !== "notValue") {
      availableDates.push({ sessionTypeName: selectedSessionType, availableDates: [] });
      setDatesTimes([]);
    } else {
      setDatesTimes([]);
    }
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const tileDisabled = ({ date }) => {
    return date < new Date();
  };

  const handleHourClick = (hour) => {
    const dateKey = selectedDate.toLocaleDateString('en-CA'); 
    const updatedDatesTimes = [...datesTimes];
    const dateIndex = updatedDatesTimes.findIndex(d => d.date === dateKey);

    if (dateIndex !== -1) {
      const hourIndex = updatedDatesTimes[dateIndex].times.indexOf(hour);
      if (hourIndex !== -1) {
        updatedDatesTimes[dateIndex].times.splice(hourIndex, 1); 
      } else {
        updatedDatesTimes[dateIndex].times.push(hour);
      }
    } else {
      updatedDatesTimes.push({ date: dateKey, times: [hour] });
    }

    setDatesTimes(updatedDatesTimes);
  };

  const hours = [];
  for (let hour = 12; hour <= 15; hour++) {
    hours.push(`${hour}`);
  }

  const hourButtons = hours.map((hour, index) => (
    <HourButton
      key={index}
      time={hour}
      datesTimes={datesTimes}
      curr_date={selectedDate.toLocaleDateString('en-CA')} 
      handleHourClick={handleHourClick}
    />
  ));

  if (loading) {
    return <LoadingScreen />;
  }

  return (
    <div>
      <AppBar />
      <h1 className='flex-centered site-header'>Dodaj daty</h1>
      <div className='flex-column-centered'>
        <SelectBox
          label="Rodzaj sesji"
          name='session-type'
          options={sessionTypes}
          onChange={handleSessionChange}
        />
        <div className='flex-centered'>
          <div className='add-dates-panel flex-centered'>
            <h2>Data</h2>
            <Calendar
              className='om-calendar'
              next2Label={null}
              prev2Label={null}
              onChange={handleDateChange}
              value={selectedDate}
              tileDisabled={tileDisabled}
            />
          </div>
          <div className='add-dates-panel flex-centered'>
            <h2>Godziny</h2>
            {hourButtons}
          </div>
        </div>
        <button className='site-button' onClick={handleUpdateDates}>Zatwierdź dla {lastSelectedSession}</button>
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

function HourButton({ time, datesTimes, curr_date, handleHourClick }) {
  return (
    <button
      className={datesTimes.find(d => d.date === curr_date)?.times.includes(time) ? 'hour-button clicked-button' : 'hour-button'} // Use optional chaining to avoid errors if datesTimes[curr_date] is undefined
      onClick={() => handleHourClick(time)}
    >
      {time}
    </button>
  );
}

export default AddDates;
