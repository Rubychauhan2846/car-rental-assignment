import { memo } from 'react';

const PricingSummary = memo(function PricingSummary({ pricingSummary }) {
    return (
        <div className="row-pricing-summary">
            {pricingSummary}
        </div>
    );
});

export default PricingSummary;