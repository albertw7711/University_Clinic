import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AllDoctors = () => {
    const [doctors, setDoctors] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDoctors = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/doctors');
                setDoctors(response.data);
            } catch (err) {
                setError('Failed to load doctors');
            } finally {
                setLoading(false);
            }
        };

        fetchDoctors();
    }, []);

    if (loading) return <p>Loading doctors...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h2>All Doctors</h2>
            {doctors.length === 0 ? (
                <p>No doctors found.</p>
            ) : (
                <div>
                    {doctors.map((doctor) => (
                        <div key={doctor.id} className="card">
                            <h3>{doctor.name}</h3>
                            <p><strong>Specialty:</strong> {doctor.specialty}</p>
                            <p><strong>Email:</strong> {doctor.email}</p>
                            <p><strong>Available:</strong> {doctor.availability ? 'Yes' : 'No'}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default AllDoctors;
