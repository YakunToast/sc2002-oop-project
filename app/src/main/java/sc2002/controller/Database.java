package sc2002.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sc2002.model.appointment.Appointment;
import sc2002.model.role.Doctor;
import sc2002.model.role.Patient;
import sc2002.model.role.User;

public class Database implements Serializable {
    private static Database database;

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

    public static void add(Doctor d) {
        database.doctors.put(d.getId(), d);
        save();
    }

    public static Doctor getDoctor(String id) {
        return database.doctors.get(id);
    }

    public static User getUserById(String id) {
        User user = database.patients.get(id);
        if (user != null) {
            return user;
        }
        user = database.doctors.get(id);
        if (user != null) {
            return user;
        }
        return null;
    }

    public static User getUserByUsername(String username) {
        return database.patients.values().stream()
                .map(u -> (User) u)
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(database.doctors.values().stream()
                        .map(u -> (User) u)
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
}
