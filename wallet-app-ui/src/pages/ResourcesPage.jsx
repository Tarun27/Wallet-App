import React from 'react';
const res = [
  { title: 'API Docs', link: '#', icon: '📘' },
  { title: 'Video Tutorials', link: '#', icon: '📺' },
  { title: 'Security Guide', link: '#', icon: '🔒' },
];
export default function ResourcesPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Resources</h1>
      <div className="space-y-4 max-w-2xl">
        {res.map((r) => (
          <a
            key={r.title}
            href={r.link}
            className="block bg-white rounded-xl p-4 shadow hover:shadow-md"
          >
            <span className="text-2xl mr-3">{r.icon}</span>
            <span className="text-lg font-medium text-slate-700">{r.title}</span>
          </a>
        ))}
      </div>
    </div>
  );
}