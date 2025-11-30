package at.spengergasse.doctor.models.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

// Lombok
@Getter
@ToString
// @NoArgsConstructor
// @AllArgsConstructor

// JPA
@Embeddable
public class PhoneNumber {

    // Variante 1:
    @Column(name = "mobile")
    private String value;

    // Variante 2:
    // Patient (and other classes) can user @AttributeOverride to set: column name, nullable, ..
    // @Column(name = "phone_number", nullable = false)
    // private String value;

    // JPA Ctor
    protected PhoneNumber() {
    }

    // Business Ctor
    public PhoneNumber(String value) {
        // if value is null -> throw NullPointerException
        Objects.requireNonNull(value);
        this.value = value;
    }
}
