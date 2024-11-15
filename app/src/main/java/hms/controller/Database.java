package hms.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.user.BaseUser;
import hms.model.user.Doctor;
import hms.model.user.Patient;

public class Database implements Serializable {
    private static Database database;

    private static final long serialVersionUID = 1L;

    private Map<String, Patient> patients;
    private Map<String, Doctor> doctors;
    private Map<UUID, Appointment> appointments;

    public Database() {
        if (database != null) {
            return;
        }

        database = this;
        this.patients = new HashMap<>();
        this.doctors = new HashMap<>();
        this.appointments = new HashMap<>();
    }

    // TODO: IDK, go back to CSV?
    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.bin"))) {
            oos.writeObject(database);
            System.out.println("Objects serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
        }
    }

    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.bin"))) {
            System.out.println("Objects deserialized successfully.");
            database = (Database) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            database = new Database();
        }
    }

    public static void add(Patient p) {
        database.patients.put(p.getId(), p);
        save();
    }

    public static Patient getPatient(String id) {
        return database.patients.get(id);
    }

    public static List<Patient> getPatientsOfDoctor(Doctor doctor) {
        return database.patients.values().stream()
                .map(p -> (Patient) p)
                .filter(p -> p.getDoctor() != null)
                .filter(p -> p.getDoctor().equals(doctor))
                .collect(Collectors.toList());
    }

    public static void add(Doctor d) {
        database.doctors.put(d.getId(), d);
        save();
    }

    public static Doctor getDoctor(String id) {
        return database.doctors.get(id);
    }

    public static BaseUser getUserById(String id) {
        BaseUser user = database.patients.get(id);
        if (user != null) {
            return user;
        }
        user = database.doctors.get(id);
        if (user != null) {
            return user;
        }
        return null;
    }

    public static BaseUser getUserByUsername(String username) {
        return database.patients.values().stream()
                .map(u -> (BaseUser) u)
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(database.doctors.values().stream()
                        .map(u -> (BaseUser) u)
                        .filter(u -> u.getUsername().equals(username))
                        .findFirst()
                        .orElse(null));
    }

    public static void add(Appointment a) {
        database.appointments.put(a.getId(), a);
        save();
    }

    public static Appointment getAppointment(UUID id) {
        return database.appointments.get(id);
    }

    public static List<Appointment> getAppointmentsOfDoctor(Doctor doctor) {
        return database.appointments.values().stream()
                .map(a -> (Appointment) a)
                .filter(a -> a.getDoctor() != null)
                .filter(a -> a.getDoctor().equals(doctor))
                .collect(Collectors.toList());
    }

    public static List<Appointment> getAppointmentsOfPatient(Patient patient) {
        return database.appointments.values().stream()
                .map(a -> (Appointment) a)
                .filter(a -> a.getPatient() != null)
                .filter(a -> a.getPatient().equals(patient))
                .collect(Collectors.toList());
    }
}
