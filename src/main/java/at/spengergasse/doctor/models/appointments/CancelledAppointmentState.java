package at.spengergasse.doctor.models.appointments;




import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity

@DiscriminatorValue("Confirmed")
public class ConfirmedAppointmentState {
}
