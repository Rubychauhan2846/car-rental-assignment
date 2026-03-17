import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { modifyReservation } from '../api/reservationApi.js';
import useReservationLoader from '../hooks/useReservationLoader.js';
import ReservationLookup from '../components/ReservationLookup.jsx';
import CurrentBookingInfo from '../components/CurrentBookingInfo.jsx';
import DateEditForm from '../components/DateEditForm.jsx';
import ModifySuccessCard from '../components/ModifySuccessCard.jsx';
import { calculateDays } from '../utils/dateUtils.js';
import '../styles/ModifyReservationPage.css';

function ModifyReservationPage() {
    const navigate = useNavigate();

    const [reservationId, setReservationId] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [saving, setSaving] = useState(false);
    const [saveError, setSaveError] = useState('');
    const [success, setSuccess] = useState(null);

    const { reservation, loading, error, load, reset } =
        useReservationLoader();

    async function handleLoad() {
        setSaveError('');
        setStartDate('');
        setEndDate('');
        await load(reservationId);
    }

    // Populate dates once reservation loads
    if (reservation && !startDate && !endDate) {
        setStartDate(reservation.startDate || '');
        setEndDate(reservation.endDate || '');
    }

    async function handleSave() {
        setSaveError('');

        if (!startDate || !endDate) {
            setSaveError('Please select both dates.');
            return;
        }
        if (calculateDays(startDate, endDate) <= 0) {
            setSaveError('End date must be after start date.');
            return;
        }

        setSaving(true);
        try {
            const requestData = {
                customerName: reservation.customerName,
                customerEmail: reservation.customerEmail,
                vehicleCategory: reservation.vehicleCategory || reservation.category,
                startDate,
                endDate,
                dailyMileage: reservation.estimatedDailyMileage
                    || reservation.dailyMileage || 0,
                yearsLicensed: reservation.yearsLicensed || 0,
            };
            const response = await modifyReservation(reservationId, requestData);
            setSuccess(response.data);
        } catch (err) {
            setSaveError(
                err.response?.data?.message ||
                'Failed to update reservation. Please try again.'
            );
        } finally {
            setSaving(false);
        }
    }

    function handleReset() {
        setReservationId('');
        setStartDate('');
        setEndDate('');
        setSaveError('');
        setSuccess(null);
        reset();
    }

    if (success) {
        return (
            <div className="modify-container">
                <ModifySuccessCard
                    reservation={success}
                    onModifyAnother={handleReset}
                    onBackToSearch={() => navigate('/')}
                />
            </div>
        );
    }

    return (
        <div className="modify-container">
            <div className="modify-card">

                <h2 className="modify-title">Modify reservation</h2>
                <p className="modify-sub">
                    Enter your reservation ID to update your booking dates
                </p>

                <ReservationLookup
                    value={reservationId}
                    onChange={(val) => {
                        setReservationId(val);
                        reset();
                        setSaveError('');
                    }}
                    onLoad={handleLoad}
                    loading={loading}
                    error={error}
                />

                {reservation && (
                    <>
                        <CurrentBookingInfo reservation={reservation} />
                        <DateEditForm
                            startDate={startDate}
                            endDate={endDate}
                            onStartChange={setStartDate}
                            onEndChange={setEndDate}
                            onSave={handleSave}
                            saving={saving}
                            error={saveError}
                        />
                    </>
                )}

            </div>
        </div>
    );
}

export default ModifyReservationPage;