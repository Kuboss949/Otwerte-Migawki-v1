import React from 'react';
import './components-css/AppBar.css'

const Link = ({ text, destination }) =>{
    return(
        <span>
            <a href={destination} className="link header-link">{text}</a>
        </span>
    );

};




const AppBar =() => {
    return(
        <div id="menu">
            <Link text="HOME" destination="/" />
            <Link text="ZAREZERWUJ" destination="/rezerwacja" />
            <img src="/images/logo.png"></img>
            <Link text="CENNIK" destination="/cennik" />
            <Link text="LOGOWANIE" destination="/login" />
        </div>
    );

};

export default AppBar;