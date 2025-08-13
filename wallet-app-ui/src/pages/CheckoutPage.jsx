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

    // 1. Ask your backend to create a PaymentIntent
    const res  = await fetch('/api/create-subscription', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ plan }),
    });
    const { clientSecret } = await res.json();

    // 2. Confirm card payment
    const { error: stripeError } = await stripe.confirmCardPayment(clientSecret, {
      payment_method: { card: elements.getElement(CardElement) },
    });

    if (stripeError) {
      setError(stripeError.message);
    } else {
      nav('/success');
    }
    setLoading(false);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <CardElement className="p-3 border rounded-md bg-white" />
      {error && <p className="text-red-600 text-sm">{error}</p>}
      <button
        disabled={!stripe || loading}
        className="w-full bg-sky-600 text-white py-2 rounded-md disabled:opacity-60"
      >
        {loading ? 'Processing…' : 'Subscribe'}
      </button>
    </form>
  );
};

export default function CheckoutPage() {
  const [params] = useSearchParams();
  const plan = params.get('plan'); // pro / free (skip) / enterprise (blocked)

  // Guard against bad links
  if (plan === 'free') {
    return <p className="p-8">Free plan selected – nothing to pay.</p>;
  }
  if (!plan || plan === 'enterprise') {
    return <p className="p-8">Invalid plan.</p>;
  }

  return (
    <div className="min-h-screen bg-gray-50 p-8 flex items-center justify-center">
      <div className="max-w-md w-full bg-white shadow-xl rounded-xl p-8">
        <h1 className="text-2xl font-bold mb-2">Complete subscription</h1>
        <p className="mb-6 text-gray-600">Plan: <span className="font-semibold capitalize">{plan}</span></p>

        <Elements stripe={stripePromise}>
          <CheckoutForm plan={plan} />
        </Elements>
      </div>
    </div>
  );
}