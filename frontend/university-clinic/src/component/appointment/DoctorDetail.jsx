import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const DoctorDetail = () => {
    const { id } = useParams();
    const [doctor, setDoctor] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDoctor = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/doctors/${id}`);
                setDoctor(response.data);
            } catch (err) {
                setError('Failed to load doctor details');
            } finally {
                setLoading(false);
            }
        };

        fetchDoctor();
    }, [id]);

    if (loading) return <p>Loading doctor information...</p>;
    if (error) return <p>{error}</p>;
    if (!doctor) return <p>Doctor not found.</p>;

    return (
        <div>
            <h2>Dr. {doctor.name}</h2>
            <div className="card">
                <p><strong>Specialty:</strong> {doctor.specialty}</p>
                <p><strong>Email:</strong> {doctor.email}</p>
                <p><strong>Phone:</strong> {doctor.phone}</p>
                <p><strong>Available for Appointments:</strong> {doctor.availability ? 'Yes' : 'No'}</p>
                <p><strong>Years of Experience:</strong> {doctor.experience} years</p>
                <p><strong>Office:</strong> {doctor.office}</p>
                <p><strong>Biography:</strong> {doctor.bio}</p>
            </div>
        </div>
    );
};

export default DoctorDetail;
