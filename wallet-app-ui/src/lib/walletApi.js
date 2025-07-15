// src/lib/walletApi.js
import axios from 'axios';

const WALLET = axios.create({
  baseURL: 'http://localhost:8082',   // ← your wallet‐service port
  headers: { 'Content-Type': 'application/json' },
});

// Attach the JWT on every request
WALLET.interceptors.request.use(cfg => {
  const token = localStorage.getItem('wallet-token');
  if (token) cfg.headers.Authorization = `Bearer ${token}`;
  return cfg;
});

export default WALLET;
