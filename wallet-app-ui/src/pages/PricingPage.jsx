import React from 'react';
import { useNavigate } from 'react-router-dom';

const plans = [
  { name: 'Free',       price: 0,      desc: '1 card, 100 tx / mo', color: 'bg-green-500' },
  { name: 'Pro',        price: 9,      desc: 'Unlimited',           color: 'bg-sky-500'   },
  { name: 'Enterprise', price: null,   desc: 'Custom',              color: 'bg-purple-500'},
];

export default function PricingPage() {
  const nav = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-8 text-center">Pricing</h1>

      <div className="grid md:grid-cols-3 gap-6 max-w-5xl mx-auto">
        {plans.map((p) => (
          <div
            key={p.name}
            className={`rounded-xl shadow-lg p-6 text-white flex flex-col ${p.color}`}
          >
            <h2 className="text-2xl font-bold mb-2">{p.name}</h2>
            <p className="text-3xl mb-3">
              {p.price === null ? 'Contact us' : `$${p.price}/mo`}
            </p>
            <p className="mb-4 flex-grow">{p.desc}</p>

            <button
              onClick={() =>
                p.price === null
                  ? (window.location.href = 'mailto:sales@yourapp.com')
                  : nav(`/checkout?plan=${p.name.toLowerCase()}`)
              }
              className="w-full bg-white/20 hover:bg-white/30 rounded py-2 mt-auto"
            >
              {p.price === null ? 'Contact Sales' : 'Choose Plan'}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}