import React, { useState, useEffect } from 'react';
import axios from 'axios';

const NotificationComponent = ({ username }) => {
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        axios.get(`/api/notifications/${username}`)
            .then(response => {
                setNotifications(response.data);
            })
            .catch(error => {
                console.error('Error fetching notifications:', error);
            });
    }, [username]);

    return (
        <div>
            <h2>Notifications for {username}</h2>
            <ul>
                {notifications.map((notification, index) => (
                    <li key={index}>{notification}</li>
                ))}
            </ul>
        </div>
    );
};

export default NotificationComponent;