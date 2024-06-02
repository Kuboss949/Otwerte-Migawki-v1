import axios from "axios";
export const fetchDatesData = async (setSessionTypes, 
    setAvailableDates, setLoading) => {
    try {
        const result = await axios.get('/api/session/all-enabled');
        setSessionTypes(result.data.map(session => session.sessionTypeName));
        const result2 = await axios.get('/api/session/fetchTimes');
        setAvailableDates(result2.data);
        setLoading(false);
    } catch (error) {
        setLoading(false);
        setSessionTypes(["Nie udało się połączyć z serwerem"]);
        console.error('Error fetching info:', error);
    }
};
