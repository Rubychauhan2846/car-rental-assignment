import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/HomePage.css";
import { calculateDays, calculateYearsLicensed } from "../utils/dateUtils";

function HomePage() {

    const navigate = useNavigate();

    const today = new Date().toISOString().split("T")[0];

    const [pickupDate, setPickupDate] = useState("");
    const [returnDate, setReturnDate] = useState("");
    const [mileage, setMileage] = useState("");
    const [licenseDate, setLicenseDate] = useState("");
    const [error, setError] = useState("");

    function handleSearch() {

        setError("");

        if (!pickupDate || !returnDate) {
            setError("Please select both dates.");
            return;
        }

        const days = calculateDays(pickupDate, returnDate);

        if (days <= 0) {
            setError("Return date must be after pick-up date.");
            return;
        }

        const yearsLicensed = calculateYearsLicensed(licenseDate);

        navigate("/results", {
            state: {
                pickupDate,
                returnDate,
                numberOfDays: days,
                estimatedDailyMileage: parseFloat(mileage) || 0,
                yearsLicensed,
                licenseDate
            }
        });
    }

    return (
        <div className="home-container">

            <div className="search-card">

                <h2 className="home-title">Find your rental car</h2>

                <div className="form-row">

                    <div className="form-group">
                        <label>Pick-up date</label>
                        <input
                            type="date"
                            min={today}
                            value={pickupDate}
                            onChange={e => setPickupDate(e.target.value)}
                        />
                    </div>

                    <div className="form-group">
                        <label>Return date</label>
                        <input
                            type="date"
                            min={pickupDate || today}
                            value={returnDate}
                            onChange={e => setReturnDate(e.target.value)}
                        />
                    </div>

                </div>


                <div className="form-row">

                    <div className="form-group">
                        <label>Estimated daily mileage</label>
                        <input
                            type="number"
                            min="0"
                            placeholder="e.g. 30"
                            value={mileage}
                            onChange={e => setMileage(e.target.value)}
                        />
                    </div>

                    <div className="form-group">
                        <label>License issue date</label>
                        <input
                            type="date"
                            max={today}
                            value={licenseDate}
                            onChange={e => setLicenseDate(e.target.value)}
                        />
                    </div>

                </div>

                {error && <div className="error-msg">{error}</div>}

                <button className="primary-btn" onClick={handleSearch}>
                    Search vehicles
                </button>

            </div>

        </div>
    );
}

export default HomePage;