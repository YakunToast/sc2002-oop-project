package hms.model.medication;

import java.io.Serializable;

public record MedicationSideEffect(String name) implements Serializable {
    @Override
    public String toString() {
        return this.name;
    }
}
