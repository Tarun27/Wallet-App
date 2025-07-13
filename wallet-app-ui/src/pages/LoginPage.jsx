// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../lib/api';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const login = async e => {
    e.preventDefault();
    setLoading(true);
    try {
      const { data } = await API.post('/users/login', { email, password });
      alert('JWT:\n' + data.token);          // pop-up
      // store token for 1 h
      localStorage.setItem('wallet-token', data.token);
      localStorage.setItem('wallet-exp', Date.now() + 3600000);
      navigate('/home');
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-50">
      <div className="w-full max-w-sm bg-white rounded-xl shadow-lg p-8">
        <h1 className="text-2xl font-bold mb-6 text-center">Sign In</h1>
        {error && <p className="mb-4 text-sm text-center text-red-600">{error}</p>}
        <form onSubmit={login} className="space-y-4">
          <input type="email" required placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} className="w-full rounded-md border px-3 py-2" />
          <input type="password" required placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} className="w-full rounded-md border px-3 py-2" />
          <button disabled={loading} className="w-full rounded-md bg-sky-600 text-white py-2 disabled:opacity-50">
            {loading ? 'Signing in…' : 'Sign in'}
          </button>
        </form>
      </div>
    </div>
  );
}