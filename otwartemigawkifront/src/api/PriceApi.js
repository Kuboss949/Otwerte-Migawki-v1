import axios from 'axios';

export const fetchSessionTypes = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/session/all');
        return response.data;
    } catch (error) {
        throw new Error('Error fetching data:', error);
    }
};