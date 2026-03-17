import CategoryBadge from './CategoryBadge.jsx';
import { formatDate } from '../utils/dateUtils.js';

function CurrentBookingInfo({ reservation }) {
    const category = reservation.vehicleCategory || reservation.category;

    return (
        <div className="loaded-info">
            <div className="loaded-info-top">
                <CategoryBadge category={category} />
            </div>
            <div className="current-dates-row">
                <div className="current-date-item">
                    <span className="current-date-lbl">Current pick-up</span>
                    <span className="current-date-val">
            {formatDate(reservation.startDate)}
          </span>
                </div>
                <span className="current-arrow">→</span>
                <div className="current-date-item">
                    <span className="current-date-lbl">Current return</span>
                    <span className="current-date-val">
            {formatDate(reservation.endDate)}
          </span>
                </div>
                <span className="current-days-pill">
          {reservation.numberOfDays} days
        </span>
            </div>
        </div>
    );
}

export default CurrentBookingInfo;