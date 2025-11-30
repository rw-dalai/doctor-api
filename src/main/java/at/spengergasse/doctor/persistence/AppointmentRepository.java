package at.spengergasse.doctor.persistence;

import at.spengergasse.doctor.models.appointments.Appointment;
import at.spengergasse.doctor.models.patients.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
