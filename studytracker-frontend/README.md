# Study Tracker Frontend

A React-based frontend for the Study Tracker application that helps you track your study sessions and subjects.

## Features

- 📚 Create and manage subjects
- ⏱️ Log study sessions with duration and notes
- 📊 View total study time per subject
- ✏️ Edit and delete subjects and sessions
- 🎨 Clean, modern UI with responsive design

## Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Java backend running on http://localhost:8080

### Installation

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm run dev
```

3. Open your browser to [http://localhost:3000](http://localhost:3000)

### Backend Setup

Make sure your Spring Boot backend is running on port 8080. The frontend expects the API to be available at:
- `http://localhost:8080/api/subjects`
- `http://localhost:8080/api/sessions`

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build

## Project Structure

```
src/
├── components/          # React components
│   ├── SubjectList.jsx
│   ├── SubjectForm.jsx
│   ├── StudySessionList.jsx
│   └── StudySessionForm.jsx
├── services/            # API service layer
│   └── api.js
├── App.jsx              # Main application component
├── App.css              # Global styles
└── main.jsx             # Application entry point
```

## Technologies Used

- React 18
- Vite (build tool)
- Axios (HTTP client)
- CSS3 (styling)
