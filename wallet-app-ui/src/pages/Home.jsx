// src/pages/Home.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API from '../lib/api';
import WALLET from '../lib/walletApi';

export default function Home() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);             // { name, id, ... }
  const [cards, setCards] = useState([]);
  const [loadingCards, setLoadingCards] = useState(true);
  const [showAddCard, setShowAddCard] = useState(false);
  const [addCardForm, setAddCardForm] = useState({
    number: '',
    exp: '',
    nameOnCard: '',
    cardType: 'Credit',
  });

  /* ---------- Auth & Data ---------- */
  const doLogout = () => {
    localStorage.clear();
    navigate('/login', { replace: true });
  };

  const loadCards = async (uid) => {
    setLoadingCards(true);
    try {
      const { data } = await WALLET.get(`/wallets/${uid}/cards`);
      setCards(data);
    } catch (err) {
      if (err.response?.status === 401) doLogout();
      else console.error(err);
    } finally {
      setLoadingCards(false);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('wallet-token');
    const expiry = Number(localStorage.getItem('wallet-exp'));
    if (!token || isNaN(expiry) || expiry < Date.now()) return doLogout();

    const init = async () => {
      API.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const { data: me } = await API.get('/users/me');
      setUser(me);
      await loadCards(me.id);
    };
    init();
  }, [navigate]);

  /* ---------- Card Management ---------- */
  const handleInputChange = (e) =>
    setAddCardForm({ ...addCardForm, [e.target.name]: e.target.value });

  const handleAddCard = async (e) => {
    e.preventDefault();
    const payload = {
      last4: addCardForm.number.slice(-4),
      exp: addCardForm.exp,
      nameOnCard: addCardForm.nameOnCard,
      type: addCardForm.cardType,
    };
    try {
      await WALLET.post(`/wallets/${user?.id}/cards`, payload);
      await loadCards(user.id);
      setShowAddCard(false);
      setAddCardForm({ number: '', exp: '', nameOnCard: '', cardType: 'Credit' });
    } catch (err) {
      alert(err.response?.data?.message || 'Add card failed');
    }
  };

  /* ---------- Mock Transactions ---------- */
  const mockTx = [
    { type: 'received', amount: 2500, desc: 'Salary', date: 'Today, 2:30 PM', icon: '💼' },
    { type: 'sent', amount: 45.99, desc: 'Groceries', date: 'Yesterday', icon: '🛒' },
    { type: 'received', amount: 120, desc: 'Freelance', date: 'Dec 10', icon: '💻' },
    { type: 'sent', amount: 89.5, desc: 'Electric Bill', date: 'Dec 9', icon: '⚡' },
    { type: 'sent', amount: 25, desc: 'Coffee', date: 'Dec 8', icon: '☕' },
  ];

  /* ---------- UI ---------- */
  const totalBalance = cards.reduce((sum, c) => Number(c.balance || 0) + sum, 0);

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/* Header with icons on top-right */}
<header className="bg-white border-b border-gray-200 px-4 py-4 flex items-center justify-between">
  <Link
    to="/profile"
    className="flex items-center gap-3 hover:bg-gray-100 rounded-md px-2 py-1"
  >
    <div className="h-10 w-10 rounded-full bg-sky-600 flex items-center justify-center text-white font-bold">
      {(user?.name || 'U')[0].toUpperCase()}
    </div>
    <p className="font-semibold">{user?.name || 'User'}</p>
  </Link>

  <button onClick={doLogout} className="text-sm font-medium text-red-600 hover:underline">
    Logout
  </button>
</header>

{/* Balance Card (single, prominent) */}
<div className="p-4">
  <div className="bg-gradient-to-r from-indigo-500 to-purple-600 rounded-xl p-6 text-white">
    <p className="text-indigo-200 text-sm">Wallet Balance</p>
    <p className="text-3xl font-bold">${totalBalance.toLocaleString()}</p>
    <div className="mt-4 flex gap-3">
      <button className="flex-1 bg-white/20 rounded-md py-2 text-white text-sm">Send</button>
      <button className="flex-1 bg-white/20 rounded-md py-2 text-white text-sm">Request</button>
      <button className="flex-1 bg-white/20 rounded-md py-2 text-white text-sm">QR</button>
    </div>
  </div>
</div>


{/* Credit Cards (no balance) */}
<div className="px-4 mb-6">
  <div className="flex justify-between items-center mb-3">
    <h2 className="text-lg font-semibold">My Cards</h2>
    <button onClick={() => setShowAddCard(true)} className="text-sm font-medium text-sky-600 hover:underline">
      + Add Card
    </button>
  </div>

  <div className="flex gap-3 overflow-x-auto pb-2">
    {cards.map((c) => (
      <div
        key={c.id}
        className="min-w-[240px] rounded-xl p-4 text-white bg-gradient-to-r from-blue-500 to-cyan-500"
      >
        <div className="flex justify-between items-center">
          <div>
            <p className="text-xs opacity-80">{c.type}</p>
            <p className="font-bold">•••• {c.last4}</p>
          </div>
        </div>
      </div>
    ))}
  </div>
 
  {showAddCard && (
  <form onSubmit={handleAddCard} className="mt-4 space-y-2 bg-white p-4 rounded-xl shadow">
    {/* Card Number */}
    <input
      name="number"
      type="text"
      required
      placeholder="Card Number"
      maxLength={19}
      value={addCardForm.number}
      onChange={(e) => {
        let val = e.target.value.replace(/\D/g, '').slice(0, 16);
        val = val.replace(/(.{4})/g, '$1 ').trim();
        setAddCardForm({ ...addCardForm, number: val });
      }}
      className="w-full rounded-md border px-3 py-2"
    />

    {/* MM / YY – same width, user can type or pick */}
    <div className="relative w-full">
      <input
        name="exp"
        type="text"
        required
        placeholder="MM / YY"
        maxLength={5}
        pattern="(0[1-9]|1[0-2])\/(\d{2})"
        title="MM/YY – month 01-12, year two digits"
        value={addCardForm.exp}
        onChange={(e) => {
          let val = e.target.value.replace(/\D/g, '');
          if (val.length >= 2) val = val.slice(0, 2) + '/' + val.slice(2, 4);
          setAddCardForm({ ...addCardForm, exp: val });
        }}
        className="w-full rounded-md border px-3 py-2 pr-8"
      />
      <select
        className="absolute right-1 top-1/2 -translate-y-1/2 h-[calc(100%-4px)] w-auto bg-transparent border-none text-sm"
        onChange={(e) => {
          const [mm, yy] = e.target.value.split('/');
          setAddCardForm({ ...addCardForm, exp: `${mm}/${yy}` });
        }}
      >
        <option value="" disabled>▼</option>
        {Array.from({ length: 12 }, (_, m) => {
          const month = String(m + 1).padStart(2, '0');
          return Array.from({ length: 20 }, (_, y) => {
            const year = String(new Date().getFullYear() + y).slice(-2);
            return (
              <option key={`${month}/${year}`} value={`${month}/${year}`}>
                {month}/{year}
              </option>
            );
          });
        }).flat()}
      </select>
    </div>

    <input
      name="nameOnCard"
      type="text"
      required
      placeholder="Name on Card"
      value={addCardForm.nameOnCard}
      onChange={handleInputChange}
      className="w-full rounded-md border px-3 py-2"
    />

    <select
      name="cardType"
      value={addCardForm.cardType}
      onChange={handleInputChange}
      className="w-full rounded-md border px-3 py-2"
    >
      <option value="Credit">Credit</option>
      <option value="Debit">Debit</option>
    </select>

    <div className="flex gap-2">
      <button type="submit" className="flex-1 rounded-md bg-sky-600 text-white py-2">Save</button>
      <button type="button" className="flex-1 rounded-md bg-gray-200 py-2" onClick={() => setShowAddCard(false)}>Cancel</button>
    </div>
  </form>
)}
      </div>

      {/* Transactions */}
      <div className="px-4 mb-20">
        <div className="flex justify-between items-center mb-3">
          <h2 className="text-lg font-semibold">Recent Transactions</h2>
        </div>
        <div className="bg-white rounded-xl shadow overflow-hidden">
          {mockTx.map((tx, i) => (
            <div key={i} className={`flex items-center justify-between p-4 ${i !== mockTx.length - 1 ? 'border-b border-gray-100' : ''}`}>
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center text-lg">{tx.icon}</div>
                <div>
                  <p className="font-medium">{tx.desc}</p>
                  <p className="text-sm text-gray-500">{tx.date}</p>
                </div>
              </div>
              <p className={`font-semibold ${tx.type === 'received' ? 'text-green-600' : 'text-gray-900'}`}>
                {tx.type === 'received' ? '+' : '-'}${tx.amount.toFixed(2)}
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}