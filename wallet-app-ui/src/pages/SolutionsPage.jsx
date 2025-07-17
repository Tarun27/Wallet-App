import React from 'react';
const solutions = [
  { title: 'Personal', desc: 'Budget, split-bills, savings goals.' },
  { title: 'Business', desc: 'Expense cards, real-time analytics.' },
  { title: 'Enterprise', desc: 'On-premise, SSO, audit logs.' },
];
export default function SolutionsPage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Solutions</h1>
      <div className="grid md:grid-cols-3 gap-6">
        {solutions.map((s) => (
          <div key={s.title} className="bg-white rounded-xl p-6 shadow">
            <h3 className="text-xl font-semibold mb-2">{s.title}</h3>
            <p className="text-slate-600">{s.desc}</p>
          </div>
        ))}
      </div>
    </div>
  );
}