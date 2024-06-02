import axios from "axios";
export const fetchAllManageData = async (setSessionDetails, setUpcomingSessions, setLoading) => {
  try {
    const sessionData = await axios.get('/api/session/all');
    const upcomingData = await axios.get('/api/session/all-upcoming');
    setSessionDetails(sessionData.data);
    setUpcomingSessions(upcomingData.data);
  } catch (error) {
    console.error('Error fetching data:', error);
  } finally {
    setLoading(false);
  }
};