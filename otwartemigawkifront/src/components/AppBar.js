import React, { useState, useEffect } from 'react';
import './components-css/AppBar.css'
import { fetchUserRole } from '../api/AuthenticationApi';

const Link = ({ text, destination }) =>{
    return(
        <span>
            <a href={destination} className="link header-link">{text}</a>
        </span>
    );

};

const AppBar =() => {
    const [role, setRole] = useState('visitor');

    useEffect(() => {
        const fetchRole = async () => {
            try {
                const role = await fetchUserRole();
                setRole(role);
            } catch (error) {
                console.error('Error fetching authentication status:', error);
            }
        };
        fetchRole();
    }, []);

    let links = [];
    if (role === "visitor") {
        links = [
            { text: "HOME", destination: "/" },
            { text: "ZAREZERWUJ", destination: "/rezerwacja" },
            { text: "CENNIK", destination: "/cennik" },
            { text: "LOGOWANIE", destination: "/login" }
        ];
    } else if (role === "admin") {
        links = [
            { text: "GALERIE", destination: "/dodaj-galerie" },
            { text: "SESJE", destination: "/sesje" },
            { text: "KLIENCI", destination: "/klienci" },
            { text: "DOSTĘPNOŚĆ", destination: "/dodaj-daty" }
        ];
    } else if (role === "user") {
        links = [
            { text: "HOME", destination: "/" },
            { text: "ZAREZERWUJ", destination: "/rezerwacja" },
            { text: "MOJE SESJE", destination: "/moje-sesje" },
            { text: "KONTO", destination: "/konto" }
        ];
    }

    


    const firstHalf = links.slice(0, 2);
    const secondHalf = links.slice(2);

    return (
        <div id="menu">
                {firstHalf.map((link, index) => (
                    <Link key={index} text={link.text} destination={link.destination} />
                ))}
                <img src="/images/logo.png" alt="Logo" />
                {secondHalf.map((link, index) => (
                    <Link key={index} text={link.text} destination={link.destination} />
                ))}
        </div>
    );
};

export default AppBar;