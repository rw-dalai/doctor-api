package at.spengergasse.doctor.persistence;

import at.spengergasse.doctor.models.appointments.AppointmentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentStateRepository extends JpaRepository<AppointmentState, Long> {
}
