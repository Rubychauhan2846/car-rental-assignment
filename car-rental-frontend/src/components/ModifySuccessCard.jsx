import CategoryBadge from './CategoryBadge.jsx';
import { formatDate } from '../utils/dateUtils.js';

function ModifySuccessCard({ reservation, onModifyAnother, onBackToSearch }) {
    const category = reservation.vehicleCategory || reservation.category;

    return (
        <div className="modify-card">

            <div className="success-icon-wrap">
                <svg width="40" height="40" viewBox="0 0 40 40">
                    <circle cx="20" cy="20" r="20" fill="#e1f5ee"/>
                    <path d="M12 21l6 6 10-12" stroke="#085041"
                          strokeWidth="2.5" strokeLinecap="round"
                          strokeLinejoin="round" fill="none"/>
                </svg>
            </div>

            <h2 className="modify-title">Dates updated!</h2>
            <p className="modify-sub">
                Your booking dates have been modified successfully.
            </p>

            <div className="info-block">
                <div className="info-row">
                    <span className="info-lbl">Reservation ID</span>
                    <span className="info-val">#{reservation.reservationId}</span>
                </div>
                <div className="info-row">
                    <span className="info-lbl">Customer</span>
                    <span className="info-val">{reservation.customerName}</span>
                </div>
                <div className="info-row">
                    <span className="info-lbl">Vehicle</span>
                    <span className="info-val">
            {reservation.vehicleMake} {reservation.vehicleModel}
          </span>
                </div>
                <div className="info-row">
                    <span className="info-lbl">Category</span>
                    <span className="info-val">
            <CategoryBadge category={category} />
          </span>
                </div>
                <div className="info-row">
                    <span className="info-lbl">New pick-up</span>
                    <span className="info-val">{formatDate(reservation.startDate)}</span>
                </div>
                <div className="info-row">
                    <span className="info-lbl">New return</span>
                    <span className="info-val">{formatDate(reservation.endDate)}</span>
                </div>
                <div className="info-row">
                    <span className="info-lbl">Duration</span>
                    <span className="info-val">{reservation.numberOfDays} days</span>
                </div>
                <div className="info-row info-total-row">
                    <span className="info-lbl">New total</span>
                    <span className="info-total">
            ${reservation.totalAmount?.toFixed(2)}
          </span>
                </div>
            </div>

            <div className="action-row">
                <button className="btn-secondary" onClick={onModifyAnother}>
                    Modify another
                </button>
                <button className="btn-primary" onClick={onBackToSearch}>
                    Back to search
                </button>
            </div>

        </div>
    );
}

export default ModifySuccessCard;