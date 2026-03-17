function ReservationLookup({ value, onChange, onLoad, loading, error }) {
    return (
        <div className="lookup-section">
            <div className="lookup-row">
                <div className="form-group" style={{ flex: 1 }}>
                    <label>Reservation ID</label>
                    <input
                        type="text"
                        placeholder="e.g. 12345678"
                        value={value}
                        onChange={(e) => onChange(e.target.value)}
                    />
                </div>
                <button
                    className="btn-load"
                    onClick={onLoad}
                    disabled={loading}
                >
                    {loading ? 'Loading...' : 'Load'}
                </button>
            </div>
            {error && <div className="msg-error">{error}</div>}
        </div>
    );
}

export default ReservationLookup;