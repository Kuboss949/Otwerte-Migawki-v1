import React from 'react';
import PropTypes from 'prop-types';

const Popup = ({ showPopup, popupMessage, responseSuccess, onClose }) => {
  if (!showPopup) return null;

  return (
    <div className={`popup ${responseSuccess ? 'success' : 'error'}`}>
      <p>{popupMessage}</p>
      <button className='site-button' onClick={onClose}>Close</button>
    </div>
  );
};

Popup.propTypes = {
  showPopup: PropTypes.bool.isRequired,
  popupMessage: PropTypes.string.isRequired,
  responseSuccess: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
};

export default Popup;
