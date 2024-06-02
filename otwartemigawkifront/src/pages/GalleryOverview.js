import React, { useState, useEffect } from 'react';
import AppBar from '../components/AppBar.js';
import "../css/GalleryOverview.css";
import LoadingScreen from '../components/LoadingScreen.js';
import { fetchGalleryData } from '../api/gallery-overview-api.js';

const GalleryOverview = () => {

  const [galleries, setGalleries] = useState([]);
  const [upcomingSessions, setUpcomingSessions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchGalleryData(setGalleries, setUpcomingSessions, setLoading);
  }, []);


  if (loading) {
    return <LoadingScreen />;
  }

  return (
    <div>
      <AppBar />
      <div id="main-section-gallery-overview">
        <div className='flex-centered gallery-overview-header'>
          <h1>Moje Sesje</h1>
          <h2>Nadchodzące</h2>
          {
            upcomingSessions.map(session =>
              <h3>{session.date.slice(0, 10)}  {session.date.slice(11, 16)}  {session.sessionTypeName}</h3>
            )
          }
        </div>
        <div id='gallery-overview'>
          {
            galleries.map(gallery =>
              <GalleryCard image={gallery.coverPhoto} name={gallery.galleryName} date={gallery.date} galleryId={gallery.galleryId} />
            )}

        </div>
      </div>
    </div>
  );
}


const GalleryCard = ({ image, name, date, galleryId }) => {
  return (
    <a className='gallery-card' href={`/galeria/${galleryId}`}>
      <img className='gallery-thumbnail' src={image} alt="pht"></img>
      <h3 className='gallery-desc'>{name}</h3>
      <h4 className='gallery-desc'>{date.slice(0, 10)}</h4>
    </a>
  );

}


export default GalleryOverview;