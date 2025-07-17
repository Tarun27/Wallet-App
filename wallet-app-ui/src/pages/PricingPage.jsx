import React from 'react';
const plans = [
  { name: 'Free', price: 0, desc: '1 card, 100 tx / mo', color: 'bg-green-500' },
  { name: 'Pro', price: 9, desc: 'Unlimited', color: 'bg-sky-500' },
  { name: 'Enterprise', price: 'Contact', desc: 'Custom', color: 'bg-purple-500' },
];

export default function PricingPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-8 text-center">Pricing</h1>
      <div className="grid md:grid-cols-3 gap-6">
        {plans.map((p) => (
          <div key={p.name} className={`rounded-xl shadow p-6 text-white ${p.color}`}>
            <h2 className="text-2xl font-bold mb-2">{p.name}</h2>
            <p className="text-3xl mb-3">{typeof p.price === 'number' ? `$${p.price}/mo` : p.price}</p>
            <p className="mb-4">{p.desc}</p>
            <button className="w-full bg-white/20 hover:bg-white/30 rounded py-2">
              {p.name === 'Enterprise' ? 'Contact Sales' : 'Choose Plan'}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}