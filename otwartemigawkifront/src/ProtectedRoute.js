import React, { useState, useEffect } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import { fetchAuthenticationStatus } from './api/AuthenticationApi';
import LoadingScreen from './components/LoadingScreen';

const ProtectedRoute = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchAuthentication = async () => {
            try {
                const isAuthenticated = await fetchAuthenticationStatus('is-authenticated');
                setIsAuthenticated(isAuthenticated);
            } catch (error) {
                console.error('Error fetching authentication status:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchAuthentication();
    }, []);

    if (isLoading) {
        return <LoadingScreen />;
    }

    return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
};

export default ProtectedRoute;
