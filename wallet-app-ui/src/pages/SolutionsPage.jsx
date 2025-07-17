import React from 'react';
export default function SolutionsPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Solutions</h1>
      <ul className="list-disc list-inside text-slate-600 text-lg space-y-2">
        <li>Personal finance management</li>
        <li>SME expense tracking</li>
        <li>Enterprise treasury tools</li>
      </ul>
    </div>
  );
}