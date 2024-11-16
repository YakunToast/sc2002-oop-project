package hms.model.medication;

public enum MedicationSideEffect {
    DROWSINESS("Drowsiness"),
    NAUSEA("Nausea"),
    VOMITING("Vomiting"),
    HEADACHE("Headache"),
    DIARRHEA("Diarrhea"),
    CONSTIPATION("Constipation"),
    FATIGUE("Fatigue"),
    BLURRED_VISION("Blurred Vision"),
    DIZZINESS("Dizziness"),
    JOINT_PAIN("Joint Pain"),
    MUSCLE_PAIN("Muscle Pain"),
    RASH("Rash"),
    ITCHING("Itching"),
    DRY_MOUTH("Dry Mouth"),
    STOMACH_PAIN("Stomach Pain"),
    INSOMNIA("Insomnia"),
    WEIGHT_GAIN("Weight Gain"),
    WEIGHT_LOSS("Weight Loss"),
    CHOLESTEOLINEMIA("High Cholesterol"),
    ANXIETY("Anxiety");

    private final String description;

    MedicationSideEffect(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
