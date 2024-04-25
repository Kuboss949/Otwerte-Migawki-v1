import React from 'react';
import AppBar from '../components/AppBar.js';
import "../css/Price.css";


const Price = () => {
  return (
    <div>
      <AppBar />
      <h1 className='flex-centered site-header'>Cennik</h1>
      <div className='flex-centered price-segment-main'>
      <PriceSegment type="Test" price="30" description="Opis pakietu/sesji"/>
      <PriceSegment type="Test" price="30" description="Opis pakietu/sesji"/>
      <PriceSegment type="Test" price="30" description="Opis pakietu/sesji"/>
      </div>
    </div>
  );
}


const PriceSegment = ({type, price, description}) =>{
  return(
  <div className='price-segment sparkle u-hover--sparkle'>
    <img src='images/slider/image1.jpg' className='price-segment-image' alt={type}/>
    <div className='price-segment-description'>
      <h2>{type}</h2> 
      <h4>{price} PLN</h4>
      <span>{description}</span>
      <a className='link price-link' href="/rezerwacja">Zarezerwuj</a>
    </div>
  </div>
  );
}


export default Price;