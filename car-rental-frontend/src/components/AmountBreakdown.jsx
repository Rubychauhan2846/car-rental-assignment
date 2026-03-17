import { memo } from 'react';

const AmountBreakdown = memo(function AmountBreakdown({
                                                          breakdown,
                                                          totalAmount
                                                      }) {
    return (
        <div className="amount-breakdown">
            <div className="breakdown-title">Amount breakdown</div>

            {breakdown?.map((line) => (
                <div
                    key={line.label}
                    className={`breakdown-row ${
                        line.type === 'SURCHARGE' ? 'breakdown-surcharge' :
                            line.type === 'EXTRA'    ? 'breakdown-extra'    : ''
                    }`}
                >
                    <span>{line.label}</span>
                    <span>${line.amount.toFixed(2)}</span>
                </div>
            ))}

            <div className="breakdown-row breakdown-total">
                <span>Total amount</span>
                <span>${totalAmount?.toFixed(2)}</span>
            </div>
        </div>
    );
});

export default AmountBreakdown;