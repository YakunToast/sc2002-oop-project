package sc2002.model;

// Patient class to manage patient operations
class Patient {
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    public Patient(String patientId, String name, String dateOfBirth, String gender) {
        this.medicalRecord = new MedicalRecord(patientId, name, dateOfBirth, gender);
        this.appointments = new ArrayList<>();
    }

    public void updateContactInformation(String phone, String email) {
        medicalRecord.updateContactInformation(phone, email);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getPastAppointments() {
        return appointments.stream()
                .filter(a -> a.getDateTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
