import { useState, useEffect, useCallback, useTransition } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getOptions } from '../api/reservationApi.js';
import DateBar from '../components/DateBar.jsx';
import VehicleRow from '../components/VehicleRow.jsx';
import '../styles/ResultsPage.css';

function ResultsPage() {
    const location = useLocation();
    const navigate = useNavigate();

    const {
        pickupDate,
        returnDate,
        numberOfDays,
        estimatedDailyMileage,
        yearsLicensed,
        licenseDate,
    } = location.state || {};

    const [options, setOptions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [isPending, startTransition] = useTransition();

    const fetchOptions = useCallback(async () => {
        try {
            const response = await getOptions(
                numberOfDays,
                estimatedDailyMileage || 0,
                yearsLicensed || 0
            );
            startTransition(() => {
                setOptions(response.data);
            });
        } catch (err) {
            console.error('Error fetching options:', err);
            setError('Failed to load options. Make sure backend is running.');
        } finally {
            setLoading(false);
        }
    }, [numberOfDays, estimatedDailyMileage, yearsLicensed, startTransition]);


    useEffect(() => {
        if (!numberOfDays) {
            navigate('/');
            return;
        }
        fetchOptions().catch((err) => {
            console.error('Something went wrong while fetching car details:', err);
        });
    }, [numberOfDays, navigate, fetchOptions]);

    const getCheapestCategory = useCallback(() => {
        if (!options.length) return null;
        return options.reduce((prev, curr) =>
            curr.bookingAmount < prev.bookingAmount ? curr : prev
        ).vehicleCategory;
    }, [options]);

    const handleModifySearch = useCallback(() => {
        navigate('/');
    }, [navigate]);


    const handleBookNow = useCallback((option) => {
        navigate('/booking', {
            state: {
                category: option.vehicleCategory,
                totalAmount: option.bookingAmount,
                breakdown: option.breakdown || [],  // ← from backend
                pickupDate,
                returnDate,
                numberOfDays,
                estimatedDailyMileage: estimatedDailyMileage || 0,
                yearsLicensed: yearsLicensed || 0,
                licenseDate: licenseDate || '',
            },
        });
    }, [navigate, pickupDate, returnDate, numberOfDays,
        estimatedDailyMileage, yearsLicensed, licenseDate]);

    if (loading) {
        return (
            <div className="results-loading">
                <div className="spinner" />
                <p>Finding available vehicles...</p>
            </div>
        );
    }

    return (
        <div className="results-container">

            <DateBar
                pickupDate={pickupDate}
                returnDate={returnDate}
                numberOfDays={numberOfDays}
                estimatedDailyMileage={estimatedDailyMileage}
                yearsLicensed={yearsLicensed}
                licenseDate={licenseDate}
                onModify={handleModifySearch}
            />

            {error && <div className="error-msg">{error}</div>}

            {isPending && (
                <div className="updating-text">Updating...</div>
            )}

            <div className="results-header">
        <span className="results-count">
          <strong>{options.length}</strong> vehicle types — sorted by price
        </span>
            </div>

            <div className="cards-list">
                {options.map((option) => (
                    <VehicleRow
                        key={option.vehicleCategory}
                        option={option}
                        numberOfDays={numberOfDays}
                        isCheapest={option.vehicleCategory === getCheapestCategory()}
                        onBookNow={handleBookNow}
                    />
                ))}
            </div>

        </div>
    );
}

export default ResultsPage;