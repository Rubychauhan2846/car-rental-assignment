import { useState } from 'react';
import { getReservation } from '../api/reservationApi.js';

function useReservationLoader() {
    const [reservation, setReservation] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    async function load(id) {
        setError('');
        setReservation(null);

        if (!id.trim()) {
            setError('Please enter a reservation ID.');
            return;
        }

        setLoading(true);
        try {
            const response = await getReservation(id);
            const res = response.data;

            if (res.status === 'CANCELLED') {
                setError(`Reservation #${id} is already cancelled.`);
                return;
            }

            setReservation(res);

        } catch (err) {
            setError(
                err.response?.status === 404
                    ? `Reservation #${id} not found.`
                    : 'Failed to load. Make sure backend is running.'
            );
        } finally {
            setLoading(false);
        }
    }

    function reset() {
        setReservation(null);
        setError('');
    }

    return { reservation, loading, error, load, reset };
}

export default useReservationLoader;