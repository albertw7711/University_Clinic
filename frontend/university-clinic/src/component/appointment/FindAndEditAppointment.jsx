import React, { useState } from 'react';
import axios from 'axios';

const FindAndEditAppointment = () => {
    const [searchId, setSearchId] = useState('');
    const [appointment, setAppointment] = useState(null);
    const [error, setError] = useState(null);
    const [saving, setSaving] = useState(false);

    const handleSearch = async () => {
        setError(null);
        setAppointment(null);
        try {
            const response = await axios.get(`http://localhost:8080/api/appointments/${searchId}`);
            setAppointment(response.data);
        } catch (err) {
            setError('Appointment not found.');
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAppointment({ ...appointment, [name]: value });
    };

    const handleSave = async () => {
        setSaving(true);
        try {
            await axios.put(`http://localhost:8080/api/appointments/${appointment.id}`, appointment);
            alert('Appointment updated successfully');
        } catch (err) {
            alert('Failed to update appointment');
        } finally {
            setSaving(false);
        }
    };

    return (
        <div>
            <h2>Find and Edit Appointment</h2>
            <div className="card">
                <label>Appointment ID</label>
                <input
                    type="text"
                    value={searchId}
                    onChange={(e) => setSearchId(e.target.value)}
                />
                <button onClick={handleSearch}>Search</button>
            </div>

            {error && <p>{error}</p>}

            {appointment && (
                <div className="card">
                    <h3>Edit Appointment #{appointment.id}</h3>

                    <label>Patient ID</label>
                    <input type="text" value={appointment.patientId} disabled />

                    <label>Doctor ID</label>
                    <input type="text" value={appointment.doctorId} disabled />

                    <label>Date</label>
                    <input
                        type="date"
                        name="date"
                        value={appointment.date}
                        onChange={handleChange}
                    />

                    <label>Time</label>
                    <input
                        type="time"
                        name="time"
                        value={appointment.time}
                        onChange={handleChange}
                    />

                    <label>Status</label>
                    <select
                        name="status"
                        value={appointment.status}
                        onChange={handleChange}
                    >
                        <option value="SCHEDULED">Scheduled</option>
                        <option value="COMPLETED">Completed</option>
                        <option value="CANCELED">Canceled</option>
                    </select>

                    <button onClick={handleSave} disabled={saving}>
                        {saving ? 'Saving...' : 'Save Changes'}
                    </button>
                </div>
            )}
        </div>
    );
};

export default FindAndEditAppointment;
