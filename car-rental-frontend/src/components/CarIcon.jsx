import PropTypes from 'prop-types';
import { Car, Truck, Bus } from 'lucide-react';
import { memo } from 'react';

const CarIcon = memo(function CarIcon({ category }) {
    const iconSize = 36;
    const iconColor = '#6b7280';

    const iconMap = {
        SEDAN:<Car size={iconSize} color={iconColor} />,
        SUV:<Car size={iconSize} color={iconColor} />,
        VAN:<Bus size={iconSize} color={iconColor} />,
        PICKUP_TRUCK: <Truck size={iconSize} color={iconColor} />,
    };

    return iconMap[category] || <Car size={iconSize} color={iconColor} />;
});

CarIcon.propTypes = {
    category: PropTypes.oneOf(['SEDAN', 'VAN', 'SUV', 'PICKUP_TRUCK']).isRequired,
};

export default CarIcon;