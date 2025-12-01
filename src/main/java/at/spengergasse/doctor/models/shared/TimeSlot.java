package at.spengergasse.doctor.models.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalTime;

// C#           Java                SQL                 Example
// DateOnly     -> LocalDate        -> SQL DATE         LocalDate.of(2023, 6, 15)
// TimeOnly     -> LocalTime        -> SQL TIME         LocalTime.of(14, 30)
// DateTime     -> LocalDateTime    -> SQL TIMESTAMP    LocalDateTime.of(2023, 6, 15, 14, 30)
// decimal      -> BigDecimal       -> SQL DECIMAL      new BigDecimal("19.99") or BigDecimal.valueOf(19.99)

// Lombok
@Getter
@ToString
// @NoArgsConstructor
// @AllArgsConstructor

// JPA
@Embeddable
public class TimeSlot {

    // Must be Nullable columns, because TimeSlot is used in ConfirmedAppointmentState
    @Column(name = "start_time", nullable = true)
    private LocalTime start;

    // Must be Nullable columns, because TimeSlot is used in ConfirmedAppointmentState
    @Column(name = "end_time", nullable = true)
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
