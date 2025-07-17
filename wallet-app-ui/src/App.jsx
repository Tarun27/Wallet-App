// src/App.jsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Landing      from './pages/Landing';
import LoginPage    from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Home         from './pages/Home';
import ProfilePage         from './pages/ProfilePage';
import ProductPage   from './pages/ProductPage';
import SolutionsPage from './pages/SolutionsPage';
import ResourcesPage from './pages/ResourcesPage';
import EnterprisePage from './pages/EnterprisePage';
import PricingPage     from './pages/PricingPage';


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
        <Route path="/product"     element={<ProductPage />} />
<Route path="/solutions"   element={<SolutionsPage />} />
<Route path="/resources"   element={<ResourcesPage />} />
<Route path="/enterprise"  element={<EnterprisePage />} />
<Route path="/pricing"     element={<PricingPage />} />
      </Routes>
    </BrowserRouter>
  );
}