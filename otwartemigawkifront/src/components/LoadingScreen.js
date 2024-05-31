import React from 'react';
import './components-css/LoadingScreen.css';

const LoadingScreen = () => {
    return (
      <div className="loading-screen">
        <img src="/images/logo.png" alt="Loading Logo" />
        <p>Ładowanie...</p>
        <div class="lds-ring"><div></div><div></div><div></div><div></div></div>
      </div>
    );
  };
  
  export default LoadingScreen;