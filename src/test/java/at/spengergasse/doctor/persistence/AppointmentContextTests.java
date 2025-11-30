package at.spengergasse.persistence;

import at.spengergasse.doctor.models.appointments.Appointment;
import at.spengergasse.doctor.models.appointments.AppointmentException;
import at.spengergasse.doctor.models.appointments.ConfirmedAppointmentState;
import at.spengergasse.doctor.models.patients.Patient;
import at.spengergasse.doctor.models.shared.InsuranceNumber;
import at.spengergasse.doctor.models.shared.PhoneNumber;
import at.spengergasse.doctor.models.shared.TimeSlot;
import at.spengergasse.doctor.models.staff.Doctor;
import at.spengergasse.doctor.persistence.AppointmentRepository;
import at.spengergasse.doctor.persistence.DoctorRepository;
import at.spengergasse.doctor.persistence.PatientRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


// The Database sets ID and JPA (Hibernate) sets the id into entity (BaseEntity)
//   patientRepository.save(patient);

// To ensure that we are really fetching from DB and not from Cache:
//   entityManager.clear();


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentContextTests {

    @Autowired PatientRepository patientRepository;
    @Autowired DoctorRepository doctorRepository;
    @Autowired AppointmentRepository appointmentRepository;

    @Autowired EntityManager entityManager;


    // --- AddPatientTest ---

    @Test
    void addPatientTest() {
        // Given
        var patient = new Patient(
            "David", "Cameron",
            new InsuranceNumber("1234567890"),
            new PhoneNumber("06601234567"));

        // When
        patientRepository.save(patient);
        entityManager.clear();

        // Then
        var patientRetrieved = patientRepository.findById(patient.getId()).orElseThrow();
        assertEquals(patient.getFirstname(), patientRetrieved.getFirstname());
    }


    // --- InsuranceNummerNotValidTest ---

    @Test
    void insuranceNummerNotValidTest() {
        assertThrows(AppointmentException.class,
            () -> new InsuranceNumber("123"));
    }


    // --- AddAppointmentWithStateConfirmedTest ---

    @Test
    void addAppointmentWithStateConfirmedTest() {
        // Given
        var patient = new Patient(
            "David", "Cameron",
            new InsuranceNumber("1234567890"),
            new PhoneNumber("06601234567")
        );

        var doctor = new Doctor(
            "Ana", "Palastanga", "doc@example.com");

        // Life Cycle Chef (Parent)
        var appointment = new Appointment(
            LocalDate.of(2025, 11, 26),
            LocalDateTime.of(2025, 11, 26, 0, 0, 0),
            patient
        );

        // Child
        var appointmentState = new ConfirmedAppointmentState(
            appointment,
            LocalDateTime.of(2025, 11, 25, 12, 0, 0),
            doctor,
            new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 30)),
            "Info Text"
        );

        // Life Cycle Chef (Parent) sets Child
        appointment.setCurrentState(appointmentState);
        // BETTER Real Business Methods: appointment.confirm(..)

        // When
        patientRepository.save(patient);
        doctorRepository.save(doctor);
        appointmentRepository.save(appointment);
        entityManager.clear();

        // Then
        var loaded = appointmentRepository.findById(appointment.getId()).orElseThrow();
        assertNotNull(loaded.getCurrentState());
        assertInstanceOf(ConfirmedAppointmentState.class, loaded.getCurrentState());
    }


    // --- DoctorEmailThrowsDbUpdateExceptionIfNotUniqueTest ---

    @Test
    void doctorEmailThrowsDbUpdateExceptionIfNotUniqueTest() {
        // Given
        Doctor d1 = new Doctor("Ana", "Palastanga", "ana@example.com");
        doctorRepository.saveAndFlush(d1);

        Doctor d2 = new Doctor("Ana", "Palastanga", "ana@example.com");
        doctorRepository.save(d2);

        // When / Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            doctorRepository.flush(); // triggert Unique-Constraint
        });
    }
}

