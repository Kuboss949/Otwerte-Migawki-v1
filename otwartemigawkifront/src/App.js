import { BrowserRouter, Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Reservation from './pages/Reservation';
import GalleryOverview from './pages/GalleryOverview';
import ClientGallery from './pages/ClientGallery';
import Account from './pages/Account';
import Price from './pages/Price';
import AddGallery from './pages/AddGallery';
import AddDates from './pages/AddDates';
import ShowClients from './pages/ShowClients';


 

import './global.css'


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/rejestracja" element={<Register />} />
        <Route path="/rezerwacja" element={<Reservation />} />
        <Route path="/moje-sesje/:id" element={<GalleryOverview />} />
        <Route path="/galeria/:id" element={<ClientGallery />} />
        <Route path="/konto/:id" element={<Account />} />
        <Route path="/cennik" element={<Price />} />
        <Route path="/dodaj-galerie" element={<AddGallery />} />
        <Route path="/dodaj-daty" element={<AddDates />} />
        <Route path="/klienci" element={<ShowClients />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
