// src/pages/PricingPage.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';

const plans = [
  {
    name : 'Free',
    price: 0,
    desc : 'Perfect for trying out or personal side-projects.',
    color: 'border-green-500',
    features: [
      '1 virtual card',
      '100 transactions / month',
      'Community support',
      'Standard 3-D Secure',
    ],
    cta: 'Get started',
  },
  {
    name : 'Pro',
    price: 9,
    desc : 'All the power you need for growing apps.',
    color: 'border-sky-500',
    features: [
      'Unlimited virtual cards',
      'Unlimited transactions',
      'Priority email support',
      'Advanced analytics & exports',
      'Webhook notifications',
    ],
    cta: 'Get started',
  },
  {
    name : 'Enterprise',
    price: null,
    desc : 'Custom solutions for teams and scale.',
    color: 'border-purple-500',
    features: [
      'Everything in Pro',
      'Dedicated account manager',
      'Custom integrations & API',
      'SLA & 24 × 7 support',
      'On-prem or private-cloud option',
    ],
    cta: 'Contact Sales',
  },
];

export default function PricingPage() {
  const nav = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-6xl mx-auto px-4 py-16">
        <h1 className="text-5xl font-bold text-center mb-2">Pricing</h1>
        <p className="text-center text-gray-600 mb-12">
          Choose the plan that’s right for you.
        </p>

        <div className="grid md:grid-cols-3 gap-6">
          {plans.map((p) => (
            <div
              key={p.name}
              className={`rounded-lg border-2 ${p.color} bg-white p-6 flex flex-col`}
            >
              <h2 className="text-xl font-semibold mb-1">{p.name}</h2>
              <p className="text-3xl font-bold mb-1">
                {p.price === null ? 'Custom' : `$${p.price}`}
                <span className="text-base font-normal text-gray-500"> /month</span>
              </p>
              <p className="text-sm text-gray-600 mb-4">{p.desc}</p>

              {/* FEATURE LIST */}
              <ul className="space-y-2 text-sm text-gray-700 mb-6 flex-grow">
                {p.features.map((f) => (
                  <li key={f} className="flex items-start">
                    <svg
                      className="w-4 h-4 text-green-500 mr-2 mt-0.5 shrink-0"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M5 13l4 4L19 7"
                      />
                    </svg>
                    <span>{f}</span>
                  </li>
                ))}
              </ul>

              <button
                onClick={() =>
                  p.price === null
                    ? (window.location.href = 'mailto:sales@yourapp.com')
                    : nav(`/checkout?plan=${p.name.toLowerCase()}`)
                }
                className="w-full rounded-md py-2.5 px-4 font-medium bg-gray-900 text-white
                           hover:bg-gray-700"
              >
                {p.cta}
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}