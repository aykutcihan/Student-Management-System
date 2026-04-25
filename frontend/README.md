# Frontend — React SPA

React 18 single-page application with Redux Toolkit and Bootstrap 5.

## Requirements

- Node.js 18+
- npm 9+

## Run Locally

```bash
npm install
npm start
```

App runs at `http://localhost:3000`. Requires the backend running on port 8081.

## Run with Docker

From the project root:

```bash
docker compose up --build frontend
```

App is served by Nginx at `http://localhost:3000`.

## Tech Stack

| Package | Purpose |
|---|---|
| React 18 | UI framework |
| Redux Toolkit | Global state (auth, misc) |
| React Router v6 | Client-side routing |
| Axios | HTTP client |
| Bootstrap 5 + PrimeReact | UI components |
| Formik + Yup | Form handling & validation |
| react-data-table-component | Data tables |
| SweetAlert2 | Confirmation dialogs |
| encrypt-storage | Encrypted localStorage (JWT token) |
| react-icons | Icon library |

## Environment Variables

Create a `.env.local` file in this directory:

```
REACT_APP_API_URL=http://localhost:8081
```

## Folder Structure

```
src/
├── api/          # Axios instance + interceptors
├── services/     # API call functions per feature
├── context/      # Redux store, auth slice, misc slice
├── router/       # React Router config, ProtectedRoute
├── layouts/      # UserLayout (topbar + sidebar + footer)
├── pages/
│   ├── user/     # Public pages (Home, Login, About...)
│   ├── admin/    # Protected pages (Dashboard, management...)
│   └── common/   # 404, Unauthorized
├── components/
│   ├── common/   # Topbar, Menubar, Footer
│   ├── admin/    # Admin-specific components (placeholder)
│   └── user/     # Public-facing components (placeholder)
└── styles/       # Global SCSS, variables, mixins
```

## Protected Routes

All `/dashboard/*` routes require authentication. Role-based access:

```
ADMIN              → all management pages
MANAGER            → dean, vice dean, teacher, student
ASSISTANT_MANAGER  → student management
TEACHER            → student info, meetings, grades
STUDENT            → choose lesson, grades & meets
```
