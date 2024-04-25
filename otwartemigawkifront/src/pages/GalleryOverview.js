import React from 'react';
import AppBar from '../components/AppBar.js';
import "../css/GalleryOverview.css";


/*
TODO:
Dodać możliwość anulowania sesji
*/

const GalleryOverview = () => {
  return (
    <div>
    <AppBar />
    <div id="main-section-gallery-overview">
      <div className='flex-centered gallery-overview-header'>
      <h1>Moje Sesje</h1>
      <h2>Nadchodzące</h2>
      <h3>03-01-2024  10:00  Sesja na dzień mamy</h3>
      </div>
      <div id='gallery-overview'>
      <GalleryCard image = 'images/slider/image1.jpg' name='Nazwa sesji' date='01-01-2024' />
      <GalleryCard image = 'images/slider/image1.jpg' name='Nazwa sesji' date='01-01-2024' />
      <GalleryCard image = 'images/slider/image1.jpg' name='Nazwa sesji' date='01-01-2024' />
      <GalleryCard image = 'images/slider/image1.jpg' name='Nazwa sesji' date='01-01-2024' />
      <GalleryCard image = 'images/slider/image1.jpg' name='Nazwa sesji' date='01-01-2024' />
      </div>
    </div>
    </div>
  );
}




const GalleryCard = ({ image, name, date, galleryId}) => {
  return(
    <a className='gallery-card' href={`/gallery/${galleryId}`}>
    <img className='gallery-thumbnail' src={image}></img>
    <h3 className='gallery-desc'>{name}</h3>
    <h4 className='gallery-desc'>{date}</h4>
    </a>
  );

}


export default GalleryOverview;