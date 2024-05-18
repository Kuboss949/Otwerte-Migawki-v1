import axios from 'axios';

export const fetchUserInfo = async () => {
    try {
        const response = await axios.get('/api/clients/get-user-info');
        return response.data;
    } catch (error) {
        throw new Error('Error fetching data:', error);
    }
};
