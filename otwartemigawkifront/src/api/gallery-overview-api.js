import axios from "axios";
export const fetchGalleryData = async (setGalleries,
    setUpcomingSessions, setLoading) => {
    try {
        const galleryData = await axios.get('/api/gallery/get-user-galleries');
        setGalleries(galleryData.data);
        const sessionData = await axios.get('/api/session/all-user-upcoming');
        setUpcomingSessions(sessionData.data);
    } catch (error) {
        console.error('Error fetching data:', error);
    } finally {
        setLoading(false);
    }
};