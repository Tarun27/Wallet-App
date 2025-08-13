// src/pages/CheckoutPage.jsx
import React, { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { loadStripe } from '@stripe/stripe-js';
import { Elements, useStripe, useElements, CardElement } from '@stripe/react-stripe-js';

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PK);

const CheckoutForm = ({ plan }) => {
  const stripe   = useStripe();
  const elements = useElements();
  const nav      = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error,   setError]   = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!stripe || !elements) return;
    setLoading(true);
    setError(null);

    const res = await fetch('/api/create-subscription', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ plan }),
    });
    const { clientSecret } = await res.json();

    const { error: stripeError } = await stripe.confirmCardPayment(clientSecret, {
      payment_method: { card: elements.getElement(CardElement) },
    });

    if (stripeError) setError(stripeError.message);
    else nav('/success');
    setLoading(false);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div className="border border-gray-300 rounded-md p-4 bg-white">
        <CardElement options={{ hidePostalCode: true }} />
      </div>

      {error && <p className="text-sm text-red-600">{error}</p>}

      <button
        disabled={!stripe || loading}
        className="w-full bg-green-600 text-white font-semibold py-2.5 rounded-md
                   hover:bg-green-700 disabled:opacity-60"
      >
        {loading ? 'Processing…' : `Subscribe to ${plan}`}
      </button>
    </form>
  );
};

export default function CheckoutPage() {
  const [params] = useSearchParams();
  const plan = params.get('plan');

  if (plan === 'free') return <p className="p-8">Nothing to pay for the free plan.</p>;
  if (!plan || plan === 'enterprise') return <p className="p-8">Invalid plan.</p>;

  const planName = plan.charAt(0).toUpperCase() + plan.slice(1);

  return (
    <div className="min-h-screen bg-gray-50 flex items-start md:items-center justify-center p-4 md:p-12">
      <div className="w-full max-w-4xl grid md:grid-cols-5 gap-0 shadow-xl rounded-lg overflow-hidden">
        {/* Left summary column */}
        <div className="md:col-span-2 bg-white p-8">
          <h2 className="text-xl font-semibold mb-4">Order summary</h2>
          <div className="space-y-3 text-sm">
            <div className="flex justify-between">
              <span>{planName}</span>
              <span>$9 / month</span>
            </div>
            <hr />
            <div className="flex justify-between font-semibold">
              <span>Total due today</span>
              <span>$9</span>
            </div>
          </div>
        </div>

        {/* Right payment column */}
        <div className="md:col-span-3 bg-gray-900 text-white p-8">
          <h2 className="text-xl font-semibold mb-4">Payment</h2>
          <Elements stripe={stripePromise}>
            <CheckoutForm plan={planName} />
          </Elements>
        </div>
      </div>
    </div>
  );
}