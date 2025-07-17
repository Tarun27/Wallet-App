// src/pages/Landing.jsx
import React, { useState } from 'react';
import { Link } from 'react-router-dom';

export default function Landing() {
  const [email, setEmail] = useState('');

  return (
    <div className="min-h-screen bg-gray-100 text-slate-800">
      {/* === Navbar === */}
      <header className="border-b border-sky-200 bg-gray-100">
        <nav className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4">
          {/* Logo */}
          <div className="flex items-center space-x-2">
            <svg width="32" height="32" viewBox="0 0 32 32" fill="none">
              <circle cx="16" cy="16" r="14" fill="#0ea5e9" />
              <circle cx="16" cy="16" r="6" stroke="white" strokeWidth="2" />
            </svg>
            <span className="text-xl font-bold text-sky-700">Wallet</span>
          </div>

          {/* Center links */}
          <ul className="hidden md:flex space-x-6 text-sm font-medium">
          <li><Link to="/product" className="hover:text-sky-600">Product</Link></li>
<li><Link to="/solutions" className="hover:text-sky-600">Solutions</Link></li>
<li><Link to="/resources" className="hover:text-sky-600">Resources</Link></li>
<li><Link to="/open-source" className="hover:text-sky-600">Open Source</Link></li>
<li><Link to="/enterprise" className="hover:text-sky-600">Enterprise</Link></li>
<li><Link to="/pricing" className="hover:text-sky-600">Pricing</Link></li>

          </ul>

          {/* Right buttons */}
          <div className="flex items-center space-x-4 text-sm">
            <Link to="/login" className="font-medium hover:text-sky-600">
              Sign in
            </Link>
            <Link
              to="/register"
              className="rounded-md bg-sky-600 px-4 py-1.5 text-white font-medium hover:bg-sky-700"
            >
              Sign up
            </Link>
          </div>
        </nav>
      </header>

      {/* === Hero === */}
      <main className="mx-auto max-w-7xl px-6 py-28 text-center">
        <h1 className="text-5xl md:text-6xl font-extrabold leading-tight">
         Manage, track and grow your assets <br />
          <span className="text-sky-600"> all in one place.</span>
        </h1>
        <p className="mt-6 max-w-2xl mx-auto text-lg text-slate-600">
          Join the world’s most widely adopted, AI-powered wallet platform.
        </p>

        {/* Email capture + Copilot box */}
        <div className="mt-10 max-w-lg mx-auto flex flex-col sm:flex-row gap-3">
  <input
    type="email"
    placeholder="Enter your email"
    value={email}
    onChange={(e) => setEmail(e.target.value)}
    className="flex-1 rounded-md border border-slate-300 px-4 py-2.5 focus:border-sky-500 focus:outline-none"
  />
  <Link
    to="/register"
    className="rounded-md bg-sky-600 px-5 py-2.5 text-white font-medium hover:bg-sky-700 shrink-0"
  >
    Sign up for Wallet
  </Link>
  <a
    href="https://chat.openai.com"
    target="_blank"
    rel="noopener noreferrer"
    className="rounded-md bg-white border border-slate-300 px-4 py-2.5 text-center text-sm font-medium hover:border-sky-500 shrink-0"
  >
    Try Wallet AI
  </a>
</div>
      </main>
    </div>
  );
}