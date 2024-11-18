package hms.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RepositoryManager implements Serializable {
    private static RepositoryManager instance;

    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;
    private InventoryRepository inventoryRepository;

    private RepositoryManager() {
        // Dynamically initialize repositories
        this.userRepository = new UserRepository();
        this.appointmentRepository = new AppointmentRepository();
        this.inventoryRepository = new InventoryRepository();
    }

    public static RepositoryManager getInstance() {
        if (instance == null) {
            synchronized (RepositoryManager.class) {
                if (instance == null) {
                    instance = new RepositoryManager();
                }
            }
        }
        return instance;
    }

    public static void destroyInstance() {
        if (instance != null) {
            synchronized (RepositoryManager.class) {
                instance = null;
            }
        }
    }

    public void serialize(String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(instance);
        }
    }

    public void save() {
        try {
            this.serialize("database.bin");
            System.out.println("saved database!");
        } catch (IOException e) {
            System.out.println("failed to serialise database");
            e.printStackTrace();
        }
    }

    public static void deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            instance = (RepositoryManager) in.readObject();
        }
    }

    public static void load() {
        try {
            deserialize("database.bin");
            System.out.println("loaded database!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("failed to deserialise database");
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AppointmentRepository getAppointmentRepository() {
        return appointmentRepository;
    }

    public InventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }
}
