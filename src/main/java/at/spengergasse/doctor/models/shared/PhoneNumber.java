package at.spengergasse.doctor.models.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString

@Embeddable
public class PhoneNumber {

    // Table Column Configuration
    @Column(name = "phone_number", nullable = false)
    private String value;

    // JPA Ctor
    protected PhoneNumber() {
    }

    // Business constructor
    public PhoneNumber(String value) {
        this.value = value;
    }
}
