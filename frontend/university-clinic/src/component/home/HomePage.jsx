import React from 'react';

const Home = () => {
    return (
        <div className="card">
            <h2>Welcome to the University Clinic</h2>
            <p>
                Manage your appointments, view patient records, and consult with
                specialists—all in one place.
            </p>
            <ul>
                <li>Patients can book and view appointments</li>
                <li>Doctors can manage their schedules</li>
                <li>Admins have full control of the system</li>
            </ul>
        </div>
    );
};

export default Home;
