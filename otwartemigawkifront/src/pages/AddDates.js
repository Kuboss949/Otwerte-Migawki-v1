import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import "../css/AddDates.css";
import AppBar from '../components/AppBar';
import { SelectBox } from '../components/InputBox';
import { fetchData } from '../api/GetApi';
import { handlePost } from '../api/PostApi';

const AddDates = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [datesTimes, setDatesTimes] = useState([]); // State to manage selected dates and hours
  const [selectedHour, setSelectedHour] = useState(null);
  const [sessionTypes, setSessionTypes] = useState([]);
  const [availableDates, setAvailableDates] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [lastSelectedSession, setLastSelectedSession] = useState('');
  const [showPopup, setShowPopup] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');
  const [responseSuccess, setResponseSuccess] = useState(false);

  useEffect(() => {
    const fetchDataFromApi = async () => {
      try {
        const result = await fetchData('/api/session/all');
        setSessionTypes(result.map(session => session.sessionTypeName));
        const result2 = await fetchData('/api/session/fetchTimes');
        setAvailableDates(result2);
        setIsLoading(false);
      } catch (error) {
        setSessionTypes(["Nie udało się połączyć z serwerem"]);
      }
    };
    fetchDataFromApi();
  }, []);

  const handleUpdateDates = async (e) => {
    if (lastSelectedSession !== '') {
      e.preventDefault();
      const requestBody = {
        sessionTypeName: lastSelectedSession,
        availableDates: datesTimes
      };
      await handlePost('/session/addTimes', requestBody, setPopupMessage, setResponseSuccess, setShowPopup);
    }
  };

  const handleSessionChange = (event) => {
    if (lastSelectedSession !== '') {
      console.log("xD")
      const lastSessionData = availableDates.find(session => session.sessionTypeName === lastSelectedSession);
      lastSessionData.availableDates = datesTimes;
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
    console.log(availableDates);
    console.log(datesTimes);
    setSelectedDate(date);
  };

  const tileDisabled = ({ date }) => {
    return date < new Date();
  };

  const handleHourClick = (hour) => {
    setSelectedHour(hour);
    const dateKey = selectedDate.toLocaleDateString('en-CA'); // Get the date in YYYY-MM-DD format respecting local timezone
    const updatedDatesTimes = [...datesTimes];
    const dateIndex = updatedDatesTimes.findIndex(d => d.date === dateKey);

    if (dateIndex !== -1) {
      const hourIndex = updatedDatesTimes[dateIndex].times.indexOf(hour);
      if (hourIndex !== -1) {
        updatedDatesTimes[dateIndex].times.splice(hourIndex, 1); // Remove hour if it already exists
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
      curr_date={selectedDate.toLocaleDateString('en-CA')} // Pass the date in YYYY-MM-DD format respecting local timezone
      handleHourClick={handleHourClick}
    />
  ));

  if (isLoading) {
    return <div>Loading...</div>
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
      {showPopup && (
        <div className={`popup ${responseSuccess ? 'success' : 'failed'}`}>
          {/* Display popup message */}
          <p>{popupMessage}</p>
          <button className='site-button' onClick={() => setShowPopup(false)} >Zamknij</button>
        </div>
      )}
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
