import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8081',
  headers: { 'Content-Type': 'application/json' },
});

// Add a request interceptor to send the JWT token from localStorage
API.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('wallet-token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default API;
