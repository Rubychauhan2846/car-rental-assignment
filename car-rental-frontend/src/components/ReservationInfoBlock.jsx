import CategoryBadge from './CategoryBadge.jsx';
import { formatDate } from '../utils/dateUtils.js';

function ReservationInfoBlock({ reservation }) {
    const category = reservation.vehicleCategory || reservation.category;

    return (
        <div className="info-block">
            <div className="info-row">
                <span className="info-lbl">Reservation ID</span>
                <span className="info-val">{reservation.reservationId}</span>
            </div>
            <div className="info-row">
                <span className="info-lbl">Customer</span>
                <span className="info-val">{reservation.customerName}</span>
            </div>
            <div className="info-row">
                <span className="info-lbl">Email</span>
                <span className="info-val">{reservation.customerEmail}</span>
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
                <span className="info-lbl">License plate</span>
                <span className="info-val">{reservation.licensePlate}</span>
            </div>
            <div className="info-row">
                <span className="info-lbl">Pick-up</span>
                <span className="info-val">{formatDate(reservation.startDate)}</span>
            </div>
            <div className="info-row">
                <span className="info-lbl">Return</span>
                <span className="info-val">{formatDate(reservation.endDate)}</span>
            </div>
            <div className="info-row">
                <span className="info-lbl">Duration</span>
                <span className="info-val">{reservation.numberOfDays} days</span>
            </div>
            <div className="info-row info-total-row">
                <span className="info-lbl">Total amount</span>
                <span className="info-total">
          ${reservation.totalAmount?.toFixed(2)}
        </span>
            </div>
        </div>
    );
}

export default ReservationInfoBlock;