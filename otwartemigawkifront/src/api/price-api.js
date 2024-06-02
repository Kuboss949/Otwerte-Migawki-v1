import axios from "axios";
export const fetchPrices = async (setSessionTypes) => {
    try {
        const typesData = await axios.get('/api/session/all-enabled');
        setSessionTypes(typesData.data);
    } catch (error) {
        console.error('Error fetching data:', error);
    }
};