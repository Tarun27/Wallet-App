// src/pages/ProfilePage.jsx
import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API from '../lib/api';

export default function ProfilePage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    // Optionally show fields if backend supports them; safe to leave blank if not
    address: '',
    phone: '',
  });
  const [isEditing, setIsEditing] = useState(false);
  const [initialData, setInitialData] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('wallet-token');
    const expiryRaw = localStorage.getItem('wallet-exp');
    const expiry = Number(expiryRaw);

    if (!token || !expiryRaw || isNaN(expiry) || expiry < Date.now()) {
      localStorage.clear();
      navigate('/login', { replace: true });
      return;
    }

    API.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    API.get('/users/me')
      .then(({ data }) => {
        // Only set fields returned by backend; fill the rest as empty string
        setForm({
          name: data.name || '',
          email: data.email || '',
          address: data.address || '',
          phone: data.phone || '',
          password: '',
          confirmPassword: ''
        });
        setInitialData({
          name: data.name || '',
          email: data.email || '',
          address: data.address || '',
          phone: data.phone || '',
          password: '',
          confirmPassword: ''
        });
      })
      .catch(() => {
        localStorage.clear();
        navigate('/login', { replace: true });
      });
  }, [navigate]);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  const handleEdit = () => setIsEditing(true);
  const handleCancel = () => {
    setForm(initialData);
    setIsEditing(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (form.password && form.password !== form.confirmPassword) {
      alert('Passwords do not match');
      return;
    }
    try {
      const payload = { ...form };
      delete payload.confirmPassword;
      await API.put('/users/profile', payload);
      alert('Profile updated');
      setIsEditing(false);
      setInitialData({ ...form, password: '', confirmPassword: '' });
    } catch (err) {
      alert(err.response?.data?.message || 'Update failed');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-md bg-white rounded-xl shadow-lg p-8">
        <h1 className="text-2xl font-bold mb-6 text-center">My Profile</h1>
        {/* View Mode */}
        {!isEditing ? (
          <div className="space-y-4">
            <div>
              <label className="block text-gray-600 text-sm mb-1">Full name</label>
              <div className="w-full rounded-md border px-3 py-2 bg-gray-100 text-gray-900">
                {form.name || <span className="text-gray-400">Not set</span>}
              </div>
            </div>
            <div>
              <label className="block text-gray-600 text-sm mb-1">Email</label>
              <div className="w-full rounded-md border px-3 py-2 bg-gray-100 text-gray-900">
                {form.email || <span className="text-gray-400">Not set</span>}
              </div>
            </div>
            {form.address !== undefined && (
              <div>
                <label className="block text-gray-600 text-sm mb-1">Address</label>
                <div className="w-full rounded-md border px-3 py-2 bg-gray-100 text-gray-900">
                  {form.address || <span className="text-gray-400">Not set</span>}
                </div>
              </div>
            )}
            {form.phone !== undefined && (
              <div>
                <label className="block text-gray-600 text-sm mb-1">Phone</label>
                <div className="w-full rounded-md border px-3 py-2 bg-gray-100 text-gray-900">
                  {form.phone || <span className="text-gray-400">Not set</span>}
                </div>
              </div>
            )}
            <button
              className="w-full rounded-md bg-sky-600 text-white py-2 mt-2"
              onClick={handleEdit}
            >
              Edit
            </button>
            <Link
              to="/home"
              className="mt-4 block text-center text-lg font-semibold text-sky-600 hover:underline"
            >
              Go to Home
            </Link>
          </div>
        ) : (
          // Edit Mode
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              name="name"
              type="text"
              required
              placeholder="Full name"
              value={form.name}
              onChange={handleChange}
              className="w-full rounded-md border px-3 py-2"
            />
            <input
              name="email"
              type="email"
              required
              placeholder="Email"
              value={form.email}
              onChange={handleChange}
              className="w-full rounded-md border px-3 py-2"
            />
            {/* Show address and phone only if present in form */}
            {'address' in form && (
              <input
                name="address"
                type="text"
                placeholder="Address"
                value={form.address}
                onChange={handleChange}
                className="w-full rounded-md border px-3 py-2"
              />
            )}
            {'phone' in form && (
              <input
                name="phone"
                type="tel"
                placeholder="Phone"
                value={form.phone}
                onChange={handleChange}
                className="w-full rounded-md border px-3 py-2"
              />
            )}
            <input
              name="password"
              type="password"
              placeholder="New password (leave blank to keep)"
              value={form.password}
              onChange={handleChange}
              className="w-full rounded-md border px-3 py-2"
            />
            <input
              name="confirmPassword"
              type="password"
              placeholder="Confirm new password"
              value={form.confirmPassword}
              onChange={handleChange}
              className="w-full rounded-md border px-3 py-2"
            />
            <div className="flex gap-2">
              <button
                type="submit"
                className="flex-1 rounded-md bg-sky-600 text-white py-2"
              >
                Save
              </button>
              <button
                type="button"
                className="flex-1 rounded-md bg-gray-300 text-gray-700 py-2"
                onClick={handleCancel}
              >
                Cancel
              </button>
            </div>
            <Link
              to="/home"
              className="mt-4 block text-center text-lg font-semibold text-sky-600 hover:underline"
            >
              Go to Home
            </Link>
          </form>
        )}
      </div>
    </div>
  );
}
