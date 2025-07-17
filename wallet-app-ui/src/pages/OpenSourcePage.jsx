import React from 'react';
export default function OpenSourcePage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Open Source</h1>
      <ul className="list-disc list-inside text-lg text-slate-600 space-y-2">
        <li>
          <a href="https://github.com/your-org/wallet-core" className="text-sky-600 hover:underline">
            wallet-core
          </a>{' '}
          – MIT license, contributions welcome.
        </li>
        <li>
          <a href="https://github.com/your-org/mobile-sdk" className="text-sky-600 hover:underline">
            mobile-sdk
          </a>{' '}
          – React-Native bridge.
        </li>
      </ul>
    </div>
  );
}