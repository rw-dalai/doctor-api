package at.spengergasse.doctor.models.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

// C#           Java                SQL
// DateOnly     -> LocalDate        -> SQL DATE
// TimeOnly     -> LocalTime        -> SQL TIME
// DateTime     -> LocalDateTime    -> SQL TIMESTAMP

// Lombok
@Getter
@ToString
// @NoArgsConstructor
// @AllArgsConstructor

// JPA
@Embeddable
public class TimeSlot {

    @Column(name = "start_time", nullable = false)
    private LocalTime start;

    @Column(name = "end_time", nullable = false)
    private LocalTime end;

    // JPA Ctor
    protected TimeSlot() {
    }

    // Business constructor
    public TimeSlot(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }
}
