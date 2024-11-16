package hms.model.medication;

import java.io.Serializable;

public class MedicationSideEffect implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String shortDescription;
    private String fullDescription;

    public MedicationSideEffect(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public MedicationSideEffect(String shortDescription, String fullDescription) {
        this(shortDescription);
        this.fullDescription = fullDescription;
    }

    @Override
    public String toString() {
        if (fullDescription == null || fullDescription.isEmpty()) {
            return shortDescription;
        } else {
            return new StringBuilder(this.shortDescription)
                    .append(": ")
                    .append(this.fullDescription)
                    .toString();
        }
    }
}
