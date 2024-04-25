import React from "react";
import Slider from "react-slick";
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './components-css/AutoSlider.css'

const path = "images/slider";
  const imageNames = ["image1.jpg", "image2.jpg", "image3.jpg", "image4.jpg", "image5.jpg"];

  const images = imageNames.map(image => `${path}/${image}`);
function AutoSlider() {
    const settings = {
      dots: false,
      infinite: true,
      slidesToShow: 4,
      slidesToScroll: 1,
      autoplay: true,
      speed: 9000,
      autoplaySpeed: 200,
      cssEase: "linear"
    };
    return (
      <div className="slider-container">
        <Slider {...settings}>
        {images.map((image, index) => (
          <div key={index}>
            <img className="slider-img" src={image} alt={`Image ${index + 1}`} />
          </div>
        ))}
        </Slider>
      </div>
    );
  }



  export default AutoSlider;