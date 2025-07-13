// src/pages/RegisterPage.jsx
import React, { useState } from 'react';
import API from '../lib/api';

export default function RegisterPage() {
  const [form, setForm] = useState({ name:'', username:'', password:'' });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    try {
      await API.post('/users/register', form);
      setMessage('✅ Registered! Redirecting…');
      setTimeout(() => (window.location.href = '/login'), 1500);
    } catch (err) {
      setMessage(err.response?.data?.message || 'Registration failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-50">
      <div className="w-full max-w-sm bg-white rounded-xl shadow-lg p-8">
        <h1 className="text-2xl font-bold mb-6 text-center">Create Account</h1>
        {message && <p className="mb-4 text-sm text-center text-red-600">{message}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <input name="name"     type="text"     required placeholder="Full name"   value={form.name}     onChange={handleChange} className="w-full rounded-md border px-3 py-2 focus:border-sky-500" />
          <input name="username" type="text"     required placeholder="Username"    value={form.username} onChange={handleChange} className="w-full rounded-md border px-3 py-2 focus:border-sky-500" />
          <input name="password" type="password" required placeholder="Password"    value={form.password} onChange={handleChange} className="w-full rounded-md border px-3 py-2 focus:border-sky-500" />
          <button disabled={loading} className="w-full rounded-md bg-sky-600 text-white py-2 disabled:opacity-50">
            {loading ? 'Creating…' : 'Register'}
          </button>
        </form>
        <p className="text-center text-sm mt-4">
          Already have an account? <a href="/login" className="text-sky-600">Log in</a>
        </p>
      </div>
    </div>
  );
}