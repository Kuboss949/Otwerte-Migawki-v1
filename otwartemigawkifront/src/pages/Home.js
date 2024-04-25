import React from 'react';
import AppBar from '../components/AppBar.js';
import AutoSlider from "../components/AutoSlider.js";
import "../css/Home.css";


const Home = () => {
  return (
    <div>
    <AppBar />
    <AutoSlider />
    <AboutMe />
    </div>
  );
}


const AboutMe = () =>{
  const title = "O mnie";
  const content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Odio facilisis mauris sit amet massa vitae tortor. Turpis egestas pretium aenean pharetra. Odio ut enim blandit volutpat maecenas. Pretium fusce id velit ut tortor pretium viverra suspendisse potenti. Pharetra massa massa ultricies mi quis. Sit amet volutpat consequat mauris nunc congue."
  return(
    <div class="flex-centered about-me">
      <h1>{title}</h1>
      <p>{content}</p>
    </div>
  );
}


export default Home;