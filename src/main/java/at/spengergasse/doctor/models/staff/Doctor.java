package at.spengergasse.doctor.models.patients;


import at.spengergasse.doctor.models.BaseEntity;
import at.spengergasse.doctor.models.shared.InsuranceNumber;
import at.spengergasse.doctor.models.shared.PhoneNumber;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

// Java Bean Validation
// NotNull, NotEmpty, NotBlank, Size, Min, Max, Pattern, Email, Future, Past, Positive, Negative, etc.


// anaemic domain vs rich domain
// anaemic domain -> only properties, no behavior
// rich domain   -> properties + behavior (methods)

@Getter
@ToString(callSuper = true)

@Entity
@Table(name = "patient")
public class Patient extends BaseEntity {

    @NotBlank @Size(max = 50)               // Java Bean Validation
    @Column(nullable = false, length = 50) // VARCHAR(50) NOT NULL
    private String firstname;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String lastname;

    @NotNull
    @Embedded
    private InsuranceNumber insuranceNumber; // not nullable

    @Embedded
    private PhoneNumber mobile; // nullable


    // JPA Ctor
    protected Patient() {
    }

    // Business constructor
    public Patient(String firstname, String lastname, InsuranceNumber insuranceNumber, PhoneNumber mobile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.insuranceNumber = insuranceNumber;
        this.mobile = mobile;
    }
}
