package at.spengergasse.doctor.models.shared;

import at.spengergasse.doctor.models.appointments.AppointmentException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;


// Lombok
@Getter
@ToString
// @NoArgsConstructor
// @AllArgsConstructor

// JPA
@Embeddable
public class InsuranceNumber {

    @Column(name = "insurance_number", nullable = false, length = 10)
    private String value;

    // JPA Ctor
    protected InsuranceNumber() {
    }

    // Business Ctor
    public InsuranceNumber(String value) {
        // Siehe PDF Angabe: "..soll prüfen, ob der String 10 Stellen lang ist.."

        // Objects.requireNonNull(value, "InsuranceNumber cannot be null");
        // Assert.isTrue(value.length() == 10, "InsuranceNumber must be 10 characters long");

        if (value == null || value.length() != 10) {
            throw new AppointmentException("Invalid InsuranceNumber");
        }

        this.value = value;
    }
}
