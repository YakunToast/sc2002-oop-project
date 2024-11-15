package sc2002.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import sc2002.model.role.User;

public abstract class BaseController {

    private static Map<String, User> patientsByID = new HashMap<>();

    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("patients.bin"))) {
            oos.writeObject(patientsByID);
            System.out.println("Objects serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
        }
    }

    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("patients.bin"))) {
            patientsByID = (Map<String, User>) ois.readObject();
            System.out.println("Objects deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
        }
    }

    public static void add(User u) {
        patientsByID.put(u.getId(), u);
        save();
    }

    public static User get(String ID) {
        return patientsByID.get(ID);
    }

    public static void remove(User u) {
        patientsByID.remove(u.getId());
        save();
    }
}
