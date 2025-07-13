// src/App.jsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Landing      from './pages/Landing';
import LoginPage    from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Home         from './pages/Home';
import ProfilePage         from './pages/ProfilePage';


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/"         element={<Landing />} />
        <Route path="/login"    element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/home"     element={<Home />} />
        <Route path="/profile"  element={<ProfilePage />} />
        <Route path="*"         element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}