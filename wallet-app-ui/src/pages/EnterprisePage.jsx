import React from 'react';
export default function EnterprisePage() {
  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-4xl font-bold text-sky-700 mb-6">Enterprise</h1>
      <ul className="list-disc list-inside text-lg text-slate-600 space-y-2">
        <li>White-label apps (your branding)</li>
        <li>On-premise deployment</li>
        <li>SSO / SAML / OAuth2</li>
        <li>Audit trails & compliance reports</li>
        <li>24×7 dedicated support</li>
      </ul>
    </div>
  );
}