import { useState, useCallback } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { reserveCar } from '../api/reservationApi.js';
import BookingSummaryBar from '../components/BookingSummaryBar.jsx';
import AmountBreakdown from '../components/AmountBreakdown.jsx';
import BookingSuccessCard from '../components/BookingSuccessCard.jsx';
import '../styles/BookingPage.css';

function BookingPage() {
    const location = useLocation();
    const navigate = useNavigate();

    const {
        category,
        totalAmount,
        pickupDate,
        returnDate,
        numberOfDays,
        estimatedDailyMileage,
        yearsLicensed,
        breakdown,
    } = location.state || {};

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [reservation, setReservation] = useState(null);

    function validate() {
        if (!name.trim()) {
            setError('Please enter your full name.');
            return false;
        }
        if (!email.trim()) {
            setError('Please enter your email address.');
            return false;
        }
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            setError('Please enter a valid email address.');
            return false;
        }
        return true;
    }

    const handleChangeVehicle = useCallback(() => {
        navigate(-1);
    }, [navigate]);

    async function handleConfirm() {
        setError('');
        if (!validate()) return;
        setLoading(true);
        try {
            const response = await reserveCar({
                customerName: name.trim(),
                customerEmail: email.trim(),
                vehicleCategory: category,
                startDate: pickupDate,
                endDate: returnDate,
                dailyMileage: estimatedDailyMileage || 0,
                yearsLicensed: yearsLicensed || 0,
            });
            setReservation(response.data);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                'Booking failed. Please try again.'
            );
        } finally {
            setLoading(false);
        }
    }

    if (!category) {
        return (
            <div className="booking-container">
                <div className="booking-empty">
                    <p>No booking details found.</p>
                    <button className="btn-primary" onClick={() => navigate('/')}>
                        Back to search
                    </button>
                </div>
            </div>
        );
    }

    if (reservation) {
        return (
            <div className="booking-container">
                <BookingSuccessCard
                    reservation={reservation}
                    onNewBooking={() => navigate('/')}
                />
            </div>
        );
    }

    return (
        <div className="booking-container">

            <BookingSummaryBar
                category={category}
                pickupDate={pickupDate}
                returnDate={returnDate}
                numberOfDays={numberOfDays}
                totalAmount={totalAmount}
                onChangeVehicle={handleChangeVehicle}
            />

            <div className="booking-form-card">

                <h2 className="booking-form-title">Complete your booking</h2>
                <p className="booking-form-sub">
                    Enter your details to confirm the reservation
                </p>

                {error && <div className="msg-error">{error}</div>}

                <div className="form-row">
                    <div className="form-group">
                        <label>Full name</label>
                        <input
                            type="text"
                            placeholder="e.g. John Doe"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Email address</label>
                        <input
                            type="email"
                            placeholder="e.g. john@gmail.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                </div>

                <AmountBreakdown
                    breakdown={breakdown}
                    totalAmount={totalAmount}
                />

                <button
                    className="btn-primary full-width"
                    onClick={handleConfirm}
                    disabled={loading}
                >
                    {loading ? 'Confirming...' : 'Confirm booking'}
                </button>

            </div>

        </div>
    );
}

export default BookingPage;