📘 Wallet App – Learning README

A lightweight React wallet landing page built while learning modern front-end tooling.
🧭 Quick Start

# 1. Clone / unzip
cd wallet-app

# 2. Install
npm install

# 3. Run dev server
npm run dev          # http://localhost:5173

🧱 Tech Stack

| Layer       | Tech                                                   |
| ----------- | ------------------------------------------------------ |
| **Bundler** | Vite 5 (dev server, HMR, lightning-fast builds)        |
| **UI**      | React 18 + Hooks (`useState`)                          |
| **Styling** | Tailwind CSS 3 (utility-first, responsive, dark/light) |
| **Icons**   | Inline SVG logo (no extra packages)                    |
| **Linting** | ESLint (via Vite template)                             |
| **Runtime** | Node ≥ 18, modern browsers                             |



📁 Project Layout

wallet-app/
├── public/                  # static assets (favicon, images)
├── src/
│   ├── components/
│   │   └── LoginForm.jsx    # email / password form
│   ├── pages/
│   │   ├── LoginPage.jsx    # centered login card
│   │   └── Landing.jsx      # GitHub-style landing page
│   ├── App.jsx              # route switcher
│   ├── main.jsx             # React root
│   └── index.css            # Tailwind entry
├── tailwind.config.js       # scan paths, theme colors
├── vite.config.js           # Vite settings
└── README.md                # this file

🎨 Color Palette

| Role       | Tailwind class                          | Hex       |
| ---------- | --------------------------------------- | --------- |
| Background | `bg-slate-50`                           | `#f8fafc` |
| Primary    | `bg-brand-600`                          | `#0284c7` |
| Accent     | `text-sky-600`                          | `#0ea5e9` |
| Text       | `text-slate-800` / `text-slate-600`     |           |
| Borders    | `border-slate-200` / `border-slate-300` |           |


🔍 Key Features (so far)

| Feature               | Notes                            |
| --------------------- | -------------------------------- |
| **Responsive Layout** | Flex, Grid, Tailwind breakpoints |
| **Dark & Light**      | One-line theme toggle via `bg-*` |
| **Form Validation**   | `required`, `type="email"`       |
| **Centered Cards**    | `min-h-screen flex items-center` |
| **GitHub-style hero** | dark → light re-color            |
| **Lightweight GIF**   | optional animated background     |

🛠️ Commands

| Task           | Command           |
| -------------- | ----------------- |
| Dev server     | `npm run dev`     |
| Build for prod | `npm run build`   |
| Preview build  | `npm run preview` |
| Lint           | `npm run lint`    |


🏠 Main Pages

/login - Login Page
Accepts email & password.
Stores JWT token on successful login.

/home - Home Page
Dashboard page with balance/cards (mock data).
Navigation bar with Profile & Logout options.

/profile - Profile Page
Fetches user details from /users/me using the stored JWT token.
Displays user information with an Edit button.
On clicking Edit, fields become editable; user can update details and password.
Saves changes via PUT /users/profile API.

🔗 Backend API Endpoints Used

POST /users/login
→ Authenticates user and returns { token, message }.
GET /users/me
→ Returns current user’s profile.
PUT /users/profile
→ Updates user’s profile details.


⚙️ Configuration

The backend API base URL is set in src/lib/api.js.
Make sure CORS is enabled on your backend for http://localhost:5173.

📝 Notes

All tokens are stored in localStorage and checked for expiry on each protected page.
Profile updates:
If password field is blank, the password remains unchanged.
All other fields update as entered.


🧪 Next Steps

[ ] Add React-Router (/login, /dashboard)
[ ] State management (Zustand / Redux)
[ ] Connect to backend API (REST / GraphQL)
[ ] Wallet authentication (Web3 / OAuth)
[ ] Dark-mode toggle (<html class="dark">)
[ ] i18n with react-i18next
Happy hacking! 🚀

