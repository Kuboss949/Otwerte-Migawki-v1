import React, { useState } from 'react';
import './components-css/InputBox.css';


const InputBox = ({ label, name, type = "text", placeholder = "", onChange = () => {}, validator, validationMsg }) => {
  const [value, setValue] = useState(placeholder);
  const [error, setError] = useState('');
  const [focused, setFocused] = useState(false);

  const handleInputChange = (e) => {
    const inputValue = e.target.value;
    setValue(inputValue);
    if (validator && !validator(inputValue)) {
      setError(validationMsg);
    } else {
      setError('');
    }
    onChange(e);
  };

  const handleFocus = () => {
    setFocused(true);
  };

  const handleBlur = () => {
    setFocused(false);
  };

  return (
    <div className={`floating-label ${error ? 'input-error' : ''}`}>
      <label className="form-label" htmlFor={name}>{label}</label>
      <input
        className={`form-field ${error ? 'input-error' : ''}`}
        id={name}
        placeholder={placeholder}
        type={type}
        name={name}
        value={value}
        onChange={handleInputChange}
        onFocus={handleFocus}
        onBlur={handleBlur}
        style={{ backgroundColor: error ? '#ffcccc' : 'inherit' }} // Change background color only when there's an error
      />
      {error && focused && <span className="error-message">{error}</span>}
    </div>
  );
};



const SelectBox = ({ label, name, options, onChange = () => {} }) => {
  return (
      <div className="floating-label">
          <label className="form-label">{label}</label>
          <select className="form-field" name={name} onChange={onChange}>
              <option value="notValue">Wybierz sesje</option>
              {options.map((option, index) => (
                  <option key={index} value={option}>
                      {option}
                  </option>
              ))}
          </select>
      </div>
  );
};


const Checkbox = ({ label, name, onChange }) => { // Added onChange prop
    return (
      <div className="checkbox-wrapper-46">
        <input
          className="inp-cbx"
          id={name} 
          type="checkbox"
          name={name}
          onChange={onChange} // Added onChange event handler
        />
        <label className="cbx" htmlFor={name}>
          <span>
            <svg width="12px" height="10px" viewBox="0 0 12 10">
              <polyline points="1.5 6 4.5 9 10.5 1"></polyline>
            </svg>
          </span>
          <span>{label}</span>
        </label>
      </div>
    );
  };

export {InputBox, SelectBox, Checkbox};