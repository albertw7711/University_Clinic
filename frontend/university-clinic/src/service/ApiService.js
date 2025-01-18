import axios from 'axios';

// Base URL to your Spring Boot backend (adjust for deployment)
const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance
const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Token management
export const setAuthToken = (token) => {
    if (token) {
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
        delete api.defaults.headers.common['Authorization'];
    }
};

// Auth
export const login = async (credentials) => {
    try {
        const response = await api.post('/auth/login', credentials);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Login failed' };
    }
};

export const logout = () => {
    setAuthToken(null);
};

// Patients
export const fetchPatients = async () => {
    try {
        const response = await api.get('/patients');
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to fetch patients' };
    }
};

export const getPatientById = async (id) => {
    try {
        const response = await api.get(`/patients/${id}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to fetch patient' };
    }
};

export const createPatient = async (patientData) => {
    try {
        const response = await api.post('/patients', patientData);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to create patient' };
    }
};

export const updatePatient = async (id, updatedData) => {
    try {
        const response = await api.put(`/patients/${id}`, updatedData);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to update patient' };
    }
};

export const deletePatient = async (id) => {
    try {
        await api.delete(`/patients/${id}`);
    } catch (error) {
        throw error.response?.data || { message: 'Failed to delete patient' };
    }
};

// Appointments
export const fetchAppointments = async () => {
    try {
        const response = await api.get('/appointments');
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to fetch appointments' };
    }
};

export const getAppointmentById = async (id) => {
    try {
        const response = await api.get(`/appointments/${id}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to fetch appointment' };
    }
};

export const createAppointment = async (appointmentData) => {
    try {
        const response = await api.post('/appointments', appointmentData);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to create appointment' };
    }
};

export const updateAppointment = async (id, updatedData) => {
    try {
        const response = await api.put(`/appointments/${id}`, updatedData);
        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Failed to update appointment' };
    }
};

export const deleteAppointment = async (id) => {
    try {
        await api.delete(`/appointments/${id}`);
    } catch (error) {
        throw error.response?.data || { message: 'Failed to delete appointment' };
    }
};

export default {
    login,
    logout,
    setAuthToken,
    fetchPatients,
    getPatientById,
    createPatient,
    updatePatient,
    deletePatient,
    fetchAppointments,
    getAppointmentById,
    createAppointment,
    updateAppointment,
    deleteAppointment
};
