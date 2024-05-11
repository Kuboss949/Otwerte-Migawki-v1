import axios from 'axios';

export const fetchClients = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/clients/all');
        return response.data;
    } catch (error) {
        throw new Error('Error fetching data:', error);
    }
};

