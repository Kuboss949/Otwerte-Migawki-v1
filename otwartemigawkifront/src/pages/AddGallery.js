import React from 'react';
import AppBar from '../components/AppBar.js';
import {SelectBox} from '../components/InputBox.js';
import "../css/AddGallery.css";
import Dropzone from 'react-dropzone'


const AddGallery = () => {

  const klienciTmp = ["Anna Kowalska (555 534 223)", "Piotr Nowak (555 534 223)"];
  const sesjeTmp = ["Rodzinna 21-01-2024", "Dzień matki 21-01-2024"]

  return (
    <div>
    <AppBar />
    <h1 className='flex-centered site-header'>Dodaj galerię</h1>
      <form className='flex-centered add-gallery-form' method='GET'>
      <SelectBox label='Imię i nazwisko klienta' name='client-data' options={klienciTmp} />
      <SelectBox label='Sesja' name='client-data' options={sesjeTmp} />
      </form>
      <div className='flex-centered'>
        <Dropzone  onDrop={acceptedFiles => console.log(acceptedFiles)}>
        {({getRootProps, getInputProps}) => (
          <section className='image-drop flex-centered'>
            <div {...getRootProps()}>
              <input {...getInputProps()} />
              <p>Dodaj zdjęcia do galerii</p>
            </div>
          </section>
      )}
    
        </Dropzone>
      </div>
      
    </div>
  );
}



export default AddGallery;