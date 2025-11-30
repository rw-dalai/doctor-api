package at.spengergasse.doctor.models.appointments;

import at.spengergasse.doctor.models.shared.TimeSlot;
import at.spengergasse.doctor.models.staff.Doctor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

// Lombok
@Getter
@ToString(callSuper = true)
// @NoArgsConstructor
// @AllArgsConstructor

// JPA
@Entity

// JPA Discriminator Value (= such that JPA knows which table row belongs to which subclass)
@DiscriminatorValue("Confirmed")
public class ConfirmedAppointmentState extends AppointmentState {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Embedded Value Object
    // (-> has start_time, end_time in the table structure !)
    @Embedded
    private TimeSlot plannedSlot;

    // Nullable column
     @Column
    private String infotext;

    // JPA Ctor
    protected ConfirmedAppointmentState() {
    }

    // Business Ctor
    public ConfirmedAppointmentState(
        Appointment appointment, LocalDateTime created,
        Doctor doctor, TimeSlot plannedSlot, String infotext) {

        super(appointment, created);

        this.doctor = doctor;
        this.plannedSlot = plannedSlot;
        this.infotext = infotext;
    }
}
