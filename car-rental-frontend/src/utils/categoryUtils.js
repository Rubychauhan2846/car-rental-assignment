export function getCategoryLabel(category) {
    if (!category) return '';

    if (category === 'PICKUP_TRUCK') {
        return 'Pickup Truck';
    }

    return category.charAt(0) + category.slice(1).toLowerCase();
}

export function getBadgeClass(category) {
    switch (category) {
        case 'SEDAN':
            return 'cat-badge badge-sedan';
        case 'VAN':
            return 'cat-badge badge-van';
        case 'SUV':
            return 'cat-badge badge-suv';
        case 'PICKUP_TRUCK':
            return 'cat-badge badge-pickup';
        default:
            return 'cat-badge';
    }
}