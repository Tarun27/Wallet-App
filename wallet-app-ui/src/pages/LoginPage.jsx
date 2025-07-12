// src/pages/LoginPage.jsx
import React from 'react';
import LoginForm from '../components/LoginForm';

export default function LoginPage() {
  return (
    <div className="flex min-h-screen items-center justify-center bg-slate-100">
      <div className="w-full max-w-sm">
        {/* Card */}
        <div className="rounded-xl bg-white shadow-lg border border-slate-200 p-8">
          <h1 className="text-xl font-semibold text-center mb-6">
            Sign in to your wallet
          </h1>
          <LoginForm />
        </div>

        {/* Footer link */}
        <p className="mt-4 text-center text-sm text-slate-600">
          New?{' '}
          <a href="/signup" className="text-brand-600 hover:underline">
            Create an account
          </a>
        </p>
      </div>
    </div>
  );
}