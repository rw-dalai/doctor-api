package at.spengergasse.doctor.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@MappedSuperclass
@ToString
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;

        BaseEntity other = (BaseEntity) o;

        // Two Transient Entities are never equal
        if (isTransient() /*|| other.isTransient()*/)
            return false;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode()
    {
        // Two Transient Entities _should_ have different HashCodes
        // (but now they have 0)
        return Objects.hashCode(id);
    }

    // Never seen the Database
    public boolean isTransient() {
        return id == null;
    }
}
