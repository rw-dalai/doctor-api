package at.spengergasse.doctor.persistence;

import at.spengergasse.doctor.models.patients.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
