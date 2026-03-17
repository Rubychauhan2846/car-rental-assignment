import CategoryBadge from './CategoryBadge.jsx';
import { formatDate } from '../utils/dateUtils.js';

function BookingSuccessCard({ reservation, onNewBooking }) {
    return (
        <div className="success-card">

            <div className="success-icon-wrap">
                <svg width="40" height="40" viewBox="0 0 40 40">
                    <circle cx="20" cy="20" r="20" fill="#e1f5ee"/>
                    <path d="M12 21l6 6 10-12" stroke="#085041"
                          strokeWidth="2.5" strokeLinecap="round"
                          strokeLinejoin="round" fill="none"/>
                </svg>
            </div>

            <h2 className="success-title">Booking confirmed!</h2>
            <p className="success-sub">
                Your reservation has been made successfully.
            </p>

            <div className="success-details">
                <div className="success-row">
                    <span className="success-lbl">Reservation ID</span>
                    <span className="success-val">{reservation.reservationId}</span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Name</span>
                    <span className="success-val">{reservation.customerName}</span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Email</span>
                    <span className="success-val">{reservation.customerEmail}</span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Vehicle</span>
                    <span className="success-val">
            {reservation.vehicleMake} {reservation.vehicleModel}
          </span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Category</span>
                    <span className="success-val">
            <CategoryBadge category={reservation.vehicleCategory} />
          </span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">License plate</span>
                    <span className="success-val">{reservation.licensePlate}</span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Pick-up</span>
                    <span className="success-val">
            {formatDate(reservation.startDate)}
          </span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Return</span>
                    <span className="success-val">
            {formatDate(reservation.endDate)}
          </span>
                </div>
                <div className="success-row">
                    <span className="success-lbl">Duration</span>
                    <span className="success-val">{reservation.numberOfDays} days</span>
                </div>
                <div className="success-row success-total-row">
                    <span className="success-lbl">Total amount</span>
                    <span className="success-total">
            ${reservation.totalAmount?.toFixed(2)}
          </span>
                </div>
            </div>

            <div className="success-note">
                Save your Reservation ID <strong>{reservation.reservationId}</strong>
                — you will need it to modify or cancel your booking.
            </div>

            <button className="btn-primary full-width" onClick={onNewBooking}>
                Make another booking
            </button>

        </div>
    );
}

export default BookingSuccessCard;