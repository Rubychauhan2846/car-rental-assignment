import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import './styles/global.css';
import {lazy, Suspense} from "react";

const HomePage             = lazy(() => import('./pages/HomePage.jsx'));
const ResultsPage          = lazy(() => import('./pages/ResultsPage.jsx'));
const BookingPage          = lazy(() => import('./pages/BookingPage.jsx'));
const ModifyReservationPage = lazy(() => import('./pages/ModifyReservationPage.jsx'));
const CancelReservationPage = lazy(() => import('./pages/CancelReservationPage.jsx'));


function PageLoader() {
    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '60vh',
            color: '#5f6b7a',
            fontSize: '14px',
            gap: '12px',
        }}>
            <div style={{
                width: '24px',
                height: '24px',
                border: '3px solid #e0e0e0',
                borderTopColor: '#1a3a5c',
                borderRadius: '50%',
                animation: 'spin 0.7s linear infinite',
            }} />
            Loading...
        </div>
    );
}

function App() {
    return (
        <BrowserRouter>
            <Navbar />
            <div className="page-wrapper">
                <Suspense fallback={<PageLoader />}>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/results" element={<ResultsPage />} />
                    <Route path="/booking" element={<BookingPage />} />
                    <Route path="/modify" element={<ModifyReservationPage />} />
                    <Route path="/cancel" element={<CancelReservationPage />} />
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
                </Suspense>
            </div>
        </BrowserRouter>
    );
}

export default App;