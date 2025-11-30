package at.spengergasse.doctor.persistence;

import at.spengergasse.doctor.models.staff.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
