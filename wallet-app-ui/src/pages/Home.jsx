// src/pages/Home.jsx
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();
  const token = localStorage.getItem('wallet-token');
  const expiry = localStorage.getItem('wallet-exp');

  useEffect(() => {
    if (!token || Date.now() > +expiry) {
      localStorage.clear();
      navigate('/login', { replace: true });
    }
  }, [token, expiry, navigate]);

  return (
    <div className="min-h-screen flex items-center justify-center text-3xl font-bold">
      Welcome Home! 🏠
    </div>
  );
}