import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';

test('renders header and navigation links', () => {
  render(
      <MemoryRouter>
        <App />
      </MemoryRouter>
  );
  expect(screen.getByText(/University Clinic/i)).toBeInTheDocument();
  expect(screen.getByText(/Home/i)).toBeInTheDocument();
  expect(screen.getByText(/Patients/i)).toBeInTheDocument();
  expect(screen.getByText(/Appointments/i)).toBeInTheDocument();
  expect(screen.getByText(/Login/i)).toBeInTheDocument();
});
