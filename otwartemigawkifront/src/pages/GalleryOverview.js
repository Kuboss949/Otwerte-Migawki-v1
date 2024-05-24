import React, { useState, useEffect } from 'react';
import AppBar from '../components/AppBar.js';
import "../css/GalleryOverview.css";
import { fetchData } from '../api/GetApi.js';


/*
TODO:
Dodać możliwość anulowania sesji
*/

const GalleryOverview = () => {

  const [galleries, setGalleries] = useState([]);
  const [upcomingSessions, setUpcomingSessions] = useState([]);
  const [loading, setLoading] = useState(true);
  
  

  useEffect(() => {
    const fetchSessionDetails = async () => {
      try {
        const galleryData = await fetchData('/api/gallery/get-user-galleries');
        setGalleries(galleryData);
        const sessionData = await fetchData('/api/session/all-user-upcoming');
        setUpcomingSessions(sessionData);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchSessionDetails();
  }, []);


  if(loading){
    return(<div>Loading...</div>)
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
          <GalleryCard image = {gallery.coverPhoto} name={gallery.galleryName} date={gallery.date} galleryId={gallery.galleryId} />
        )}

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
    <h4 className='gallery-desc'>{date.slice(0, 10)}</h4>
    </a>
  );

}


export default GalleryOverview;