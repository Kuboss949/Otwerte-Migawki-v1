import {React, useState} from 'react';
import AppBar from '../components/AppBar.js';
import "../css/ClientGallery.css";
import { Gallery } from "react-grid-gallery";


const IMAGES = [
    {
       src: "images/slider/image1.jpg",
       width: 440,
       height: 293
    },
    {
      src: "images/slider/image2.jpg",
      width: 440,
      height: 293
   },
   {
      src: "images/slider/image3.jpg",
      width: 440,
      height: 293
   },
   {
      src: "images/slider/image4.jpg",
      width: 440,
      height: 293
   },
 ];

 let imagesToSelect = 2;


const ClientGallery = () => {

   const [images, setImages] = useState(IMAGES);


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

  return (
    <div>
    <AppBar />
    <div id='gallery-header'>
        <h3 className='header-side-element'>Pozostało do wyboru: <span>{imagesToSelect}</span></h3>
        <h1>Nazwa sesji</h1>
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