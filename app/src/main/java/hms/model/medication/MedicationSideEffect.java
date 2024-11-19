package hms.model.medication;

/** Enum representing the possible side effects of a medication. */
public enum MedicationSideEffect {
    /** Drowsiness as a side effect. */
    DROWSINESS("Drowsiness"),

    /** Nausea as a side effect. */
    NAUSEA("Nausea"),

    /** Vomiting as a side effect. */
    VOMITING("Vomiting"),

    /** Headache as a side effect. */
    HEADACHE("Headache"),

    /** Diarrhea as a side effect. */
    DIARRHEA("Diarrhea"),

    /** Constipation as a side effect. */
    CONSTIPATION("Constipation"),

    /** Fatigue as a side effect. */
    FATIGUE("Fatigue"),

    /** Blurred vision as a side effect. */
    BLURRED_VISION("Blurred Vision"),

    /** Dizziness as a side effect. */
    DIZZINESS("Dizziness"),

    /** Joint pain as a side effect. */
    JOINT_PAIN("Joint Pain"),

    /** Muscle pain as a side effect. */
    MUSCLE_PAIN("Muscle Pain"),

    /** Rash as a side effect. */
    RASH("Rash"),

    /** Itching as a side effect. */
    ITCHING("Itching"),

    /** Dry mouth as a side effect. */
    DRY_MOUTH("Dry Mouth"),

    /** Stomach pain as a side effect. */
    STOMACH_PAIN("Stomach Pain"),

    /** Insomnia as a side effect. */
    INSOMNIA("Insomnia"),

    /** Weight gain as a side effect. */
    WEIGHT_GAIN("Weight Gain"),

    /** Weight loss as a side effect. */
    WEIGHT_LOSS("Weight Loss"),

    /** High cholesterol as a side effect. */
    CHOLESTEOLINEMIA("High Cholesterol"),

    /** Anxiety as a side effect. */
    ANXIETY("Anxiety");

    /** Description of the side effect. */
    private final String description;

    /**
     * Constructor to initialize the side effect with its description.
     *
     * @param description the description of the side effect
     */
    MedicationSideEffect(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the side effect.
     *
     * @return the description of the side effect
     */
    public String getDescription() {
        return description;
    }
}
