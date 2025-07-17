import React from 'react';
export default function ResourcesPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Resources</h1>
      <ul className="list-disc list-inside text-slate-600 text-lg space-y-2">
        <li>API docs & SDKs</li>
        <li>Video tutorials</li>
        <li>Security best-practices</li>
      </ul>
    </div>
  );
}