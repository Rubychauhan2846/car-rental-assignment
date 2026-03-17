import CategoryBadge from './CategoryBadge.jsx';
import { formatDate } from '../utils/dateUtils.js';

function BookingSummaryBar({ category, pickupDate, returnDate,
                               numberOfDays, totalAmount, onChangeVehicle }) {

    return (
        <div className="booking-summary-bar">
            <div className="summary-item">
                <span className="summary-lbl">Category</span>
                <CategoryBadge category={category} />
            </div>
            <div className="summary-divider" />
            <div className="summary-item">
                <span className="summary-lbl">Pick-up</span>
                <span className="summary-val">{formatDate(pickupDate)}</span>
            </div>
            <div className="summary-divider" />
            <div className="summary-item">
                <span className="summary-lbl">Return</span>
                <span className="summary-val">{formatDate(returnDate)}</span>
            </div>
            <div className="summary-divider" />
            <div className="summary-item">
                <span className="summary-lbl">Duration</span>
                <span className="summary-val">{numberOfDays} days</span>
            </div>
            <div className="summary-divider" />
            <div className="summary-item">
                <span className="summary-lbl">Total</span>
                <span className="summary-total">${totalAmount?.toFixed(2)}</span>
            </div>
            <button className="summary-back-btn" onClick={onChangeVehicle}>
                Change vehicle
            </button>
        </div>
    );
}

export default BookingSummaryBar;