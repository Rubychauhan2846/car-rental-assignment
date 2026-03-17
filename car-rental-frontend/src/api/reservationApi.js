import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const getOptions = (numberOfDays, estimatedDailyMileage, yearsLicensed) =>
    api.get('/reservations/options', {
        params: { numberOfDays, estimatedDailyMileage, yearsLicensed },
    });

export const reserveCar = (requestData) =>
    api.post('/reservations', requestData);

export const modifyReservation = (id, requestData) =>
    api.put(`/reservations/${id}`, requestData);

export const cancelReservation = (id) =>
    api.delete(`/reservations/${id}`);

export const getReservation = (id) =>
    api.get(`/reservations/${id}`);

export default api;