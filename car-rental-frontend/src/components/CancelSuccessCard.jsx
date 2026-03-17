function CancelSuccessCard({ reservationId, onCancelAnother, onBackToSearch }) {
    return (
        <div className="cancel-card">

            <div className="success-icon-wrap">
                <svg width="40" height="40" viewBox="0 0 40 40">
                    <circle cx="20" cy="20" r="20" fill="#FCEBEB"/>
                    <path d="M14 14l12 12M26 14L14 26"
                          stroke="#A32D2D" strokeWidth="2.5"
                          strokeLinecap="round"/>
                </svg>
            </div>

            <h2 className="cancel-title">Reservation cancelled</h2>
            <p className="cancel-sub">
                Reservation <strong>#{reservationId}</strong> has been
                successfully cancelled. The vehicle has been released.
            </p>

            <div className="cancel-note">
                If you need to make a new booking, use the Search page.
            </div>

            <div className="action-row">
                <button className="btn-secondary" onClick={onCancelAnother}>
                    Cancel another
                </button>
                <button className="btn-primary" onClick={onBackToSearch}>
                    Back to search
                </button>
            </div>

        </div>
    );
}

export default CancelSuccessCard;