import React, { useState, useEffect, useCallback } from 'react';
import './components-css/AppBar.css';
import { fetchUserRole } from '../api/AuthenticationApi';

const Link = ({ text, destination }) => {
    return (
        <span>
            <a href={destination} className="link header-link">{text}</a>
        </span>
    );
};

const AppBar = () => {
    const [role, setRole] = useState('visitor');

    const fetchAndSetRole = useCallback(async () => {
        try {
            const fetchedRole = await fetchUserRole();
            setRole(fetchedRole);
            localStorage.setItem('userType', fetchedRole);
        } catch (error) {
            setRole('visitor');
            console.error('Error fetching authentication status:', error);
        }
    }, []);

    useEffect(() => {
        const storedUserType = localStorage.getItem('userType');
        if (storedUserType) {
            setRole(storedUserType);
        } else {
            fetchAndSetRole();
        }
    }, [fetchAndSetRole]);

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
