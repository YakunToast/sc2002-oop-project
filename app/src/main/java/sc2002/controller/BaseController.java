package sc2002.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sc2002.model.role.User;

public abstract class BaseController {

    private static Map<UUID, User> patientsByUUID = new HashMap<>();

    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("patients.bin"))) {
            oos.writeObject(patientsByUUID);
            System.out.println("Objects serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
        }
    }

    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("patients.bin"))) {
            patientsByUUID = (Map<UUID, User>) ois.readObject();
            System.out.println("Objects deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
        }
    }

    public static void add(User u) {
        patientsByUUID.put(u.getId(), u);
        save();
    }

    public static User get(UUID uuid) {
        return patientsByUUID.get(uuid);
    }

    public static User get(String username) {
        // Return the first matching user (if any) to satisfy the "only one value"
        // requirement
        return patientsByUUID.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public static void remove(User u) {
        patientsByUUID.remove(u.getId());
        save();
    }
}
