import { memo } from 'react';
import CarIcon from './CarIcon.jsx';
import CategoryBadge from './CategoryBadge.jsx';
import PricingSummary from './PricingSummary.jsx';

const VehicleRow = memo(function VehicleRow({
                                                option,
                                                numberOfDays,
                                                isCheapest,
                                                onBookNow,
                                            }) {
    const { vehicleCategory, bookingAmount,
        pricingSummary, surchargeApplied,
        surchargePercentage } = option;

    return (
        <div className="vehicle-row">

            <div className="row-icon">
                <CarIcon category={vehicleCategory} />
            </div>

            <div className="row-info">
                <div className="row-top">
                    <CategoryBadge category={vehicleCategory} />
                    {isCheapest && (
                        <span className="best-price-badge">Best price</span>
                    )}
                </div>

                <div className="row-days">
                    {numberOfDays} day{numberOfDays !== 1 ? 's' : ''} rental
                </div>

                <PricingSummary pricingSummary={pricingSummary} />

                {surchargeApplied && (
                    <span className="row-surcharge">
            +{surchargePercentage}% surcharge applied
          </span>
                )}
            </div>

            <div className="row-price-block">
                <div className="row-price-lbl">Total amount</div>
                <div className="row-price">
                    ${bookingAmount.toFixed(2)}
                </div>
                <button
                    className="row-book-btn"
                    onClick={() => onBookNow(option)}
                >
                    Book now
                </button>
            </div>

        </div>
    );
});

export default VehicleRow;