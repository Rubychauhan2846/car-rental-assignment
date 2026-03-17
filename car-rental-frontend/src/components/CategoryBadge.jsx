import { getCategoryLabel, getBadgeClass } from '../utils/categoryUtils';
import {memo} from "react";

const CategoryBadge = memo(function CategoryBadge({ category }) {
    return (
        <span className={getBadgeClass(category)}>
            {getCategoryLabel(category)}
        </span>
    );
});

export default CategoryBadge;