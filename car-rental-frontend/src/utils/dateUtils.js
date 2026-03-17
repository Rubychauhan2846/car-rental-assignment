export function formatDate(dateStr) {
    if (!dateStr) return '';

    return new Date(dateStr).toLocaleDateString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric'
    });
}

export function calculateDays(start, end) {
    const s = new Date(start);
    const e = new Date(end);

    return Math.ceil((e - s) / (1000 * 60 * 60 * 24));
}

export function calculateYearsLicensed(licenseDate) {
    if (!licenseDate) return 0;

    const issue = new Date(licenseDate);
    const now = new Date();

    let years = now.getFullYear() - issue.getFullYear();
    const m = now.getMonth() - issue.getMonth();

    if (m < 0 || (m === 0 && now.getDate() < issue.getDate())) {
        years--;
    }

    return Math.max(0, years);
}