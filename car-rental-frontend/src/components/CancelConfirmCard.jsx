import ReservationInfoBlock from './ReservationInfoBlock.jsx';

function CancelConfirmCard({ reservation, onConfirm,
                               onKeep, loading, error }) {

    return (
        <div className="cancel-card">

            <h2 className="cancel-title">Cancel reservation</h2>
            <p className="cancel-sub">
                Review your booking details before cancelling
            </p>

            <ReservationInfoBlock reservation={reservation} />

            <div className="warning-banner">
                <svg width="16" height="16" viewBox="0 0 18 18"
                     style={{ flexShrink: 0, marginTop: '1px' }}>
                    <path d="M9 1L1 16h16L9 1z"
                          fill="#FAEEDA" stroke="#854F0B"
                          strokeWidth="1" strokeLinejoin="round"/>
                    <text x="9" y="14" textAnchor="middle"
                          fontSize="9" fontWeight="600" fill="#854F0B">!</text>
                </svg>
                <span>
          This action cannot be undone. Your booking will be cancelled immediately.
        </span>
            </div>

            {error && <div className="msg-error">{error}</div>}

            <div className="action-row">
                <button className="btn-secondary" onClick={onKeep}>
                    Keep my booking
                </button>
                <button
                    className="btn-danger"
                    onClick={onConfirm}
                    disabled={loading}
                >
                    {loading ? 'Cancelling...' : 'Yes, cancel it'}
                </button>
            </div>

        </div>
    );
}

export default CancelConfirmCard;