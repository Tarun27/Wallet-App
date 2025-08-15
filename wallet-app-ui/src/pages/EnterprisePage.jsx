// EnterprisePage.tsx
import React from 'react';

export default function EnterprisePage() {
  return (
    <div className="min-h-screen bg-[#f6f8fa]">
      {/* Header */}
      <header className="bg-white border-b border-[#d1d9e0]">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <h1 className="text-4xl font-bold text-[#0d1117]">Enterprise</h1>
          <p className="mt-2 text-lg text-[#656d76]">
            Secure, compliant, and scalable for the largest organizations.
          </p>
        </div>
      </header>

      {/* Main */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        {/* Primary CTA */}
        <div className="bg-[#0969da] text-white rounded-lg p-8 flex flex-col md:flex-row items-center justify-between">
          <div>
            <h2 className="text-2xl font-semibold">Ready to get started?</h2>
            <p className="mt-1 opacity-90">Contact our sales team for a custom quote.</p>
          </div>
          <a
            href="mailto:sales@mywalletapp.com"
            className="mt-4 md:mt-0 bg-white text-[#0969da] font-semibold py-3 px-5 rounded-md hover:bg-[#f3f4f6] transition"
          >
            Contact sales
          </a>
        </div>

        {/* Features grid */}
        <div className="mt-12 grid gap-8 md:grid-cols-2 lg:grid-cols-3">
          {features.map((feat) => (
            <div
              key={feat.title}
              className="bg-white border border-[#d1d9e0] rounded-lg p-6 shadow-sm"
            >
              <div className="text-[#656d76] mb-3">{feat.icon}</div>
              <h3 className="text-lg font-semibold text-[#0d1117]">{feat.title}</h3>
              <p className="mt-2 text-sm text-[#656d76]">{feat.desc}</p>
            </div>
          ))}
        </div>

        {/* Extra details */}
        <section className="mt-16 text-center">
          <h2 className="text-2xl font-semibold text-[#0d1117]">
            Trusted by teams of every size
          </h2>
          <p className="mt-2 max-w-2xl mx-auto text-[#656d76]">
            From 5-person start-ups to Fortune-50 financial institutions, our
            enterprise-grade infrastructure keeps your data safe and your teams
            productive.
          </p>
        </section>
      </main>

      {/* Footer */}
      <footer className="border-t border-[#d1d9e0] bg-white mt-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6 text-sm text-[#656d76]">
          © {new Date().getFullYear()} MyWallet Inc. All rights reserved.
        </div>
      </footer>
    </div>
  );
}

// Feature list
const features = [
  {
    icon: (
      <svg
        className="w-6 h-6"
        fill="currentColor"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M12 2L2 7v10c0 5.55 3.84 9.74 9 11 5.16-1.26 9-5.45 9-11V7l-10-5z" />
      </svg>
    ),
    title: 'Security & Compliance',
    desc:
      'SOC 2 Type II, ISO 27001, GDPR, CCPA. Audit logs and granular access controls.',
  },
  {
    icon: (
      <svg
        className="w-6 h-6"
        fill="currentColor"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" />
      </svg>
    ),
    title: 'Single Sign-On (SSO)',
    desc: 'Native SAML, OIDC, and OAuth2 support with SCIM user provisioning.',
  },
  {
    icon: (
      <svg
        className="w-6 h-6"
        fill="currentColor"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z" />
      </svg>
    ),
    title: 'White-label Apps',
    desc: 'Ship under your brand, custom domains, and enterprise app-store listings.',
  },
  {
    icon: (
      <svg
        className="w-6 h-6"
        fill="currentColor"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M3 17h18v2H3zm0-7h18v5H3zm0-4h18v2H3z" />
      </svg>
    ),
    title: 'On-premise Deployment',
    desc: 'Self-host on your own Kubernetes cluster or air-gapped environments.',
  },
  {
    icon: (
      <svg
        className="w-6 h-6"
        fill="currentColor"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M9 11H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2zm2-7h-1V2h-2v2H8V2H6v2H5c-1.11 0-1.99.9-1.99 2L3 20c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V9h14v11z" />
      </svg>
    ),
    title: '24×7 Dedicated Support',
    desc: 'Named customer-success engineer with 30-min SLA for critical issues.',
  },
  {
    icon: (
      <svg
        className="w-6 h-6"
        fill="currentColor"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M16 6l2.29 2.29-4.88 4.88-4-4L2 16.59 3.41 18l6-6 4 4 6.3-6.29L22 12V6h-6z" />
      </svg>
    ),
    title: '99.99 % Uptime SLA',
    desc: 'Globally distributed, multi-region active-active architecture.',
  },
];