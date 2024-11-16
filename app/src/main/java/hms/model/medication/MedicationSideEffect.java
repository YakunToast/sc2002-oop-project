package hms.model.medication;

import java.io.Serializable;

public record MedicationSideEffect(String name) implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return this.name;
    }
}
