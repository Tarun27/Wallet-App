// src/pages/Home.jsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();
  const [balance] = useState(12450.38); // mock
  const [cards]   = useState([
    { id: 1, last4: '4242', type: 'Visa', exp: '12/27', balance: 2450.5 },
    { id: 2, last4: '5555', type: 'Master', exp: '08/26', balance:  820.0 },
  ]);

  useEffect(() => {
    const token  = localStorage.getItem('wallet-token');
    const expiry = localStorage.getItem('wallet-exp');
    if (!token || Date.now() > +expiry) {
      localStorage.clear();
      navigate('/login', { replace: true });
    }
  }, [navigate]);

  const logout = () => {
    localStorage.clear();
    navigate('/login', { replace: true });
  };

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow">
  <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">
    <Link to="/home" className="text-xl font-bold text-sky-700 hover:opacity-80 cursor-pointer">
      Wallet
    </Link>
    <button
      onClick={() => {
        localStorage.clear();
        window.location.href = '/';
      }}
      className="text-sm font-medium text-slate-600 hover:text-red-600"
    >
      Logout
    </button>
  </div>
</header>

      {/* Main */}
      <main className="max-w-7xl mx-auto px-6 py-8 grid grid-cols-1 md:grid-cols-3 gap-8">
        {/* Balance Card */}
        <div className="md:col-span-2 bg-white rounded-xl shadow p-6">
          <h2 className="text-lg font-semibold mb-2">Total Balance</h2>
          <p className="text-4xl font-bold text-sky-600">$ {balance.toLocaleString()}</p>
          <div className="mt-4 flex gap-3">
            <button className="rounded-md bg-sky-600 px-4 py-2 text-white text-sm">
              Add Funds
            </button>
            <button className="rounded-md bg-white border border-sky-600 px-4 py-2 text-sky-600 text-sm">
              Send
            </button>
          </div>
        </div>

        {/* Credit Card Section */}
        <div className="bg-white rounded-xl shadow p-6">
          <h2 className="text-lg font-semibold mb-4">Credit Cards</h2>
          {cards.map(c => (
            <div key={c.id} className="mb-3 flex justify-between items-center">
              <div>
                <p className="font-medium">{c.type} **** {c.last4}</p>
                <p className="text-xs text-slate-500">Expires {c.exp}</p>
              </div>
              <p className="text-sm font-semibold">${c.balance.toLocaleString()}</p>
            </div>
          ))}
          <button className="mt-2 w-full rounded-md bg-sky-100 text-sky-700 text-sm py-2">
            Add Card
          </button>
        </div>
      </main>
    </div>
  );
}