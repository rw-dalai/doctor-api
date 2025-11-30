package at.spengergasse.doctor.models.appointments;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.ToString;

// @Column(nullable = false)
// NOT NULL in DB

// Lombok
@Getter
@ToString(callSuper = true)
// @NoArgsConstructor
// @AllArgsConstructor

// JPA
@Entity

// JPA Discriminator Value (= such that JPA knows which table row belongs to which subclass)
@DiscriminatorValue("Cancelled")
public class CancelledAppointmentState extends AppointmentState {
}
