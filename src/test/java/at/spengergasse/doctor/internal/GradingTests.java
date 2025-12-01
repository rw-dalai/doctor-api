package at.spengergasse.doctor.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * GRADING TESTS THAT VALIDATE THE DATABASE SCHEMA AND CONSTRAINTS
 * FOR STUDENT SOLUTIONS TO THIS EXERCISE.
 */

@DataJpaTest
public class GradingTests {

    @Autowired JdbcTemplate jdbc;

    @BeforeEach
    void disableFk() {
        jdbc.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }

    @Test
    void T00_canCreateDatabaseTest() {
        Integer count = jdbc.queryForObject(
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", Integer.class);

        assertThat(count)
            .as("Less than 3 tables found. Check your entities & mappings.")
            .isNotNull()
            .isGreaterThanOrEqualTo(3);
    }

    @Test
    void T01_insertDoctorTest() {
        insertOk(
            "INSERT INTO doctor (firstname, lastname, email) VALUES ('first', 'last', 'x@y.at')"
        );
    }

    @Test
    void T02_insertPatientTest() {
        insertOk(
            "INSERT INTO patient (firstname, lastname, insurance_number, mobile) VALUES ('first', 'last', '1234567890', NULL)"
        );
    }

    @Test
    void T03_insertAppointmentTest() {
        insertOk(
            "INSERT INTO appointment (date, created, patient_id) VALUES ('2025-12-02', '2025-11-24T13:00:00', 1)"
        );
    }

    @Test
    void T04_insertAppointmentStateTest() {
        insertOk(
            "INSERT INTO appointment (date, created, patient_id) VALUES ('2025-12-02', '2025-11-24T13:00:00', 1)"
        );
    }

    @Test
    void T05_insertConfirmedAppointmentStateTest() {
        insertOk(
            "INSERT INTO appointment_state (appointment_id, created, type, doctor_id, start_time, end_time, infotext) " +
                "VALUES (1, '2025-11-24T13:00:00', 'Confirmed', 1, '13:00:00', '14:00:00', null)"
        );
    }

    @Test
    void T06_insertCancelledAppointmentStateTest() {
        insertOk(
            "INSERT INTO appointment_state (appointment_id, created, type, doctor_id, start_time, end_time, infotext) " +
                "VALUES (1, '2025-11-24T13:00:00', 'Cancelled', null, null, null, null)"
        );
    }

    @Test
    void T07_doctorEmailIsUniqueTest() {
        insertOk(
            "INSERT INTO doctor (firstname, lastname, email) VALUES ('first', 'last', 'x@y.at')");

        insertShouldFail(
            "INSERT INTO doctor (firstname, lastname, email) VALUES ('first', 'last', 'x@y.at')",
            "INSERT INTO doctor (firstname, lastname, email) VALUES ('first2', 'last2', 'x@y.at')"
        );
    }

    @Test
    void T08_appointmentDateAndPatientIsUniqueTest() {
        insertOk(
            "INSERT INTO appointment (date, created, patient_id) VALUES ('2025-12-02', '2025-11-24T13:00:00', 1)"
        );

        insertShouldFail(
            "INSERT INTO appointment (date, created, patient_id) VALUES ('2025-12-02', '2025-11-24T13:00:00', 1)",
            "INSERT INTO appointment (date, created, patient_id) VALUES ('2025-12-02', '2025-11-24T14:00:00', 1)"
        );
    }

    private void insertOk(String... sqls) {
        for (String sql : sqls) {
            jdbc.update(sql);
        }
    }

    private void insertShouldFail(String... sqls) {
        boolean failed = false;
        for (String sql : sqls) {
            try {
                jdbc.update(sql);
            } catch (DataIntegrityViolationException ex) {
                failed = true;
            }
        }
        if (!failed) {
            fail("Query should fail, but it didn't.\n" + String.join("\n", sqls));
        }
    }
}
