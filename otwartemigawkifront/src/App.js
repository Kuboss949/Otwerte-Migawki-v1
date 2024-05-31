import { BrowserRouter, Routes, Route } from 'react-router-dom';
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
import ManageClients from './pages/ManageClients';
import ManageSessions from './pages/ManageSessions';
import ProtectedRoute from './ProtectedRoute';
import AdminRoute from './AdminRoute';
import LoadingScreen from './components/LoadingScreen';
import { NotificationProvider } from './context/NotificationContext';
import NotificationPopup from './components/Notification';

 

import './global.css'


const getUsername = () => "u2@u2.pl";

function App() {
  const username = getUsername();
  return (
    <NotificationProvider username={username}>
    <BrowserRouter>
    <NotificationPopup />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/rejestracja" element={<Register />} />
        <Route path="/rezerwacja" element={<Reservation />} />
        <Route path="/cennik" element={<Price />} />

        <Route exact path="/moje-sesje" element={<ProtectedRoute />}>
          <Route exact path="/moje-sesje" element={<GalleryOverview />} />
        </Route>
        <Route exact path="/galeria/:id" element={<ProtectedRoute />}>
          <Route exact path="/galeria/:id" element={<ClientGallery />} />
        </Route>
        <Route exact path="/konto" element={<ProtectedRoute />}>
          <Route exact path="/konto" element={<Account />} />
        </Route>

        <Route exact path="/dodaj-galerie" element={<AdminRoute />}>
          <Route exact path="/dodaj-galerie" element={<AddGallery />} />
        </Route>
        <Route exact path="/dodaj-daty" element={<AdminRoute />}>
          <Route exact path="/dodaj-daty" element={<AddDates />} />
        </Route>
        <Route exact path="/klienci" element={<AdminRoute />}>
          <Route exact path="/klienci" element={<ManageClients />} />
        </Route>
        <Route exact path="/sesje" element={<AdminRoute />}>
          <Route exact path="/sesje" element={<ManageSessions />} />
        </Route>
      </Routes>
    </BrowserRouter>
    </NotificationProvider>
  );
}

export default App;
