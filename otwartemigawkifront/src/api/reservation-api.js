import axios from "axios";
export const fetchReservationData = async (setSessionTypes, setAvailableDates) => {
    try {
        const result = await axios.get('/api/session/all-enabled');
        setSessionTypes(result.data.map(session => session.sessionTypeName));
        const result2 = await axios.get('/api/session/fetchTimes');
        setAvailableDates(result2.data);
    } catch (error) {
        setSessionTypes(["Nie udało się połączyć z serwerem"]);
    }
};