import axios from "axios";
export const fetchGalleryImages = async (setImages, id,
    setLoading) => {
    try {
        const galleryData = await axios.get('/api/gallery/' + id);
        setImages(galleryData.data);
    } catch (error) {
        console.error('Error fetching data:', error);
    } finally {
        setLoading(false);
    }
};