import { formatDate } from '../utils/dateUtils.js';
import '../styles/DateBar.css';

function DateBar({ pickupDate, returnDate, numberOfDays,
                     estimatedDailyMileage, yearsLicensed, licenseDate, onModify }) {

    return (
        <div className="date-bar">

            <div className="date-bar-item">
                <span className="date-bar-lbl">Pick-up</span>
                <span className="date-bar-val">{formatDate(pickupDate)}</span>
            </div>

            <div className="date-bar-divider" />

            <div className="date-bar-item">
                <span className="date-bar-lbl">Return</span>
                <span className="date-bar-val">{formatDate(returnDate)}</span>
            </div>

            <div className="date-bar-divider" />

            <div className="date-bar-item">
                <span className="date-bar-lbl">Duration</span>
                <span className="date-bar-val">{numberOfDays} days</span>
            </div>

            {estimatedDailyMileage > 0 && (
                <>
                    <div className="date-bar-divider" />
                    <div className="date-bar-item">
                        <span className="date-bar-lbl">Mileage</span>
                        <span className="date-bar-val">{estimatedDailyMileage} mi/day</span>
                    </div>
                </>
            )}

            {licenseDate && (
                <>
                    <div className="date-bar-divider" />
                    <div className="date-bar-item">
                        <span className="date-bar-lbl">Licensed</span>
                        <span className="date-bar-val">
              {yearsLicensed} yr{yearsLicensed !== 1 ? 's' : ''}
                            {yearsLicensed < 3 && (
                                <span className="date-surcharge-flag"> +10%</span>
                            )}
            </span>
                    </div>
                </>
            )}

            {onModify && (
                <button className="date-bar-modify-btn" onClick={onModify}>
                    Modify search
                </button>
            )}

        </div>
    );
}

export default DateBar;