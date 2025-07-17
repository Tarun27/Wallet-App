import React from 'react';
const features = [
  { title: 'Multi-Currency Wallet', desc: 'Hold USD, EUR, BTC, ETH in one place.' },
  { title: 'AI Insights', desc: 'Spending forecasts & anomaly alerts.' },
  { title: 'Instant P2P', desc: 'Send money with 1-tap QR.' },
];
export default function ProductPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Product</h1>
      <div className="grid md:grid-cols-3 gap-6">
        {features.map((f) => (
          <div key={f.title} className="bg-white rounded-xl p-6 shadow">
            <h3 className="text-xl font-semibold mb-2">{f.title}</h3>
            <p className="text-slate-600">{f.desc}</p>
          </div>
        ))}
      </div>
    </div>
  );
}