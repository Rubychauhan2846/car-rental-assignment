import { calculateDays } from '../utils/dateUtils.js';

function DateEditForm({ startDate, endDate, onStartChange,
                          onEndChange, onSave, saving, error }) {

    const today = new Date().toISOString().split('T')[0];
    const days = calculateDays(startDate, endDate);

    return (
        <div className="date-edit-section">

            <div className="dates-row">
                <div className="form-group">
                    <label>Start date</label>
                    <input
                        type="date"
                        min={today}
                        value={startDate}
                        onChange={(e) => onStartChange(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label>End date</label>
                    <input
                        type="date"
                        min={startDate || today}
                        value={endDate}
                        onChange={(e) => onEndChange(e.target.value)}
                    />
                </div>
            </div>

            {days > 0 && (
                <div className="days-pill">
                    {days} day{days !== 1 ? 's' : ''} rental
                </div>
            )}

            {error && <div className="msg-error">{error}</div>}

            <button
                className="btn-primary full-width"
                onClick={onSave}
                disabled={saving}
            >
                {saving ? 'Saving...' : 'Modify Booking'}
            </button>

        </div>
    );
}

export default DateEditForm;