import React from 'react';
export default function PricingPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Pricing</h1>
      <ul className="list-disc list-inside text-slate-600 text-lg space-y-2">
        <li>Free tier – 1 card, 100 transactions / month</li>
        <li>Pro – $9 / month – unlimited cards & transactions</li>
        <li>Enterprise – contact sales</li>
      </ul>
    </div>
  );
}