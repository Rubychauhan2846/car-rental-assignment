import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { cancelReservation } from '../api/reservationApi.js';
import useReservationLoader from '../hooks/useReservationLoader.js';
import ReservationLookup from '../components/ReservationLookup.jsx';
import CancelConfirmCard from '../components/CancelConfirmCard.jsx';
import CancelSuccessCard from '../components/CancelSuccessCard.jsx';
import '../styles/CancelReservationPage.css';

function CancelReservationPage() {
    const navigate = useNavigate();

    const [reservationId, setReservationId] = useState('');
    const [cancelling, setCancelling] = useState(false);
    const [cancelError, setCancelError] = useState('');
    const [cancelled, setCancelled] = useState(false);

    const { reservation, loading, error, load, reset } =
        useReservationLoader();

    async function handleLoad() {
        setCancelError('');
        await load(reservationId);
    }

    async function handleConfirmCancel() {
        setCancelError('');
        setCancelling(true);
        try {
            await cancelReservation(reservationId);
            setCancelled(true);
        } catch (err) {
            setCancelError(
                err.response?.data?.message ||
                err.response?.status === 404
                    ? 'Reservation not found. Please check the ID.'
                    : 'Failed to cancel. Please try again.'
            );
        } finally {
            setCancelling(false);
        }
    }

    function handleReset() {
        setReservationId('');
        setCancelError('');
        setCancelled(false);
        reset();
    }

    if (cancelled) {
        return (
            <div className="cancel-container">
                <CancelSuccessCard
                    reservationId={reservationId}
                    onCancelAnother={handleReset}
                    onBackToSearch={() => navigate('/')}
                />
            </div>
        );
    }

    return (
        <div className="cancel-container">

            {!reservation && (
                <div className="cancel-card">
                    <h2 className="cancel-title">Cancel reservation</h2>
                    <p className="cancel-sub">
                        Enter your reservation ID to cancel your booking
                    </p>
                    <ReservationLookup
                        value={reservationId}
                        onChange={(val) => {
                            setReservationId(val);
                            reset();
                            setCancelError('');
                        }}
                        onLoad={handleLoad}
                        loading={loading}
                        error={error}
                    />
                </div>
            )}

            {reservation && (
                <CancelConfirmCard
                    reservation={reservation}
                    onConfirm={handleConfirmCancel}
                    onKeep={() => { reset(); setCancelError(''); }}
                    loading={cancelling}
                    error={cancelError}
                />
            )}

        </div>
    );
}

export default CancelReservationPage;