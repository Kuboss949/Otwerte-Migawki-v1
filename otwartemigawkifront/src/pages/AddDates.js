import React, { useState } from 'react';
import Calendar from 'react-calendar';
import "../css/AddDates.css";
import AppBar from '../components/AppBar.js';
import {SelectBox} from '../components/InputBox.js';

const AddDates = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [datesTimes, setDatesTimes] = useState({}); // State to manage selected dates and hours
  const [selectedHour, setSelectedHour] = useState(null);

  let sessionTypes = ['Sesja rodzinna', 'Sesja na dzień mamy']

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const tileDisabled = ({ date }) => {
    return date < new Date();
  };

  const handleHourClick = (hour) => {
    setSelectedHour(hour);
    const updatedDatesTimes = { ...datesTimes };
    const dateKey = selectedDate.toISOString().split('T')[0]; // Get the date in YYYY-MM-DD format
    if (!updatedDatesTimes.hasOwnProperty(dateKey)) {
      updatedDatesTimes[dateKey] = [];
    }
    if (updatedDatesTimes[dateKey].includes(hour)) {
      updatedDatesTimes[dateKey] = updatedDatesTimes[dateKey].filter(h => h !== hour); // Remove hour if it already exists
    } else {
      updatedDatesTimes[dateKey].push(hour);
    }
    setDatesTimes(updatedDatesTimes);
  };

  const hours = [];
  for (let hour = 12; hour <= 15; hour++) {
    hours.push(`${hour}:00`);
  }

  const hourButtons = hours.map((hour, index) => (
    <HourButton
      key={index}
      time={hour}
      datesTimes={datesTimes}
      curr_date={selectedDate.toISOString().split('T')[0]} // Pass the date in YYYY-MM-DD format
      handleHourClick={handleHourClick}
    />
  ));

  return (
    <div>
      <AppBar />
      <h1 className='flex-centered site-header'>Dodaj daty</h1>
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
    </div>
  );
};

function HourButton({ time, datesTimes, curr_date, handleHourClick }) {
  return (
    <button
      className={datesTimes[curr_date]?.includes(time) ? 'hour-button clicked-button' : 'hour-button'} // Use optional chaining to avoid errors if datesTimes[curr_date] is undefined
      onClick={() => handleHourClick(time)}
    >
      {time}
    </button>
  );
}

export default AddDates;
