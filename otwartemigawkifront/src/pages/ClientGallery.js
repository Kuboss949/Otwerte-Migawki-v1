import { React, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import AppBar from '../components/AppBar.js';
import "../css/ClientGallery.css";
import { Gallery } from "react-grid-gallery";
import axios from 'axios';
import LoadingScreen from '../components/LoadingScreen.js';


let imagesToSelect = 2;
const ClientGallery = () => {
   
   const { id } = useParams();
   const [images, setImages] = useState([]);
   const [loading, setLoading] = useState(true);


   useEffect(() => {
      const fetchSessionDetails = async () => {
         try {
            const galleryData = await axios.get('/api/gallery/' + id);
            setImages(galleryData.data);
         } catch (error) {
            console.error('Error fetching data:', error);
         } finally {
            setLoading(false);
         }
      };
      fetchSessionDetails();
   }, []);


   const handleSelect = (index) => {
      const updatedImages = images.map((image, i) => {
         if (i === index) {
            const isSelected = !image.isSelected;
            if (isSelected && imagesToSelect === 0) return image; // If no selections left
            const imagesToSelectChange = isSelected ? -1 : 1;
            imagesToSelect += imagesToSelectChange;
            return { ...image, isSelected };
         } else {
            return image;
         }
      });
      setImages(updatedImages);
   };

   if (loading) {
      return <LoadingScreen />;
   }
   console.log(images);
   return (
      <div>
         <AppBar />
         <div id='gallery-header'>
            <h3 className='header-side-element'>Pozostało do wyboru: <span>{imagesToSelect}</span></h3>
            <h1>Twoja sesja</h1>
            <button className='site-button header-side-element'>Zamów</button>
         </div>
         <Gallery
            className='client-gallery'
            images={images}
            onClick={handleSelect}
            margin={5}
            rowHeight={300}
         />
      </div>
   );
}



export default ClientGallery;