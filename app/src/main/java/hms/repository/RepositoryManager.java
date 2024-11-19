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

    /**
     * @return RepositoryManager
     */
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

    /**
     * @param filePath
     * @throws IOException
     */
    public void serialize(String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(instance);
        }
    }

    public boolean save() {
        try {
            this.serialize("database.bin");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveAndLog() {
        if (this.save()) {
            System.out.println("saved database!");
        } else {
            System.out.println("failed to serialise database");
        }
    }

    /**
     * @param filePath
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            instance = (RepositoryManager) in.readObject();
        }
    }

    public static void load() {
        java.io.File file = new java.io.File("database.bin");
        if (!file.exists()) {
            System.err.println("no database found!");
            return;
        }

        try {
            deserialize("database.bin");
            System.out.println("loaded database!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("failed to deserialise database: incompatible database format");
        }
    }

    /**
     * @return UserRepository
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * @return AppointmentRepository
     */
    public AppointmentRepository getAppointmentRepository() {
        return appointmentRepository;
    }

    /**
     * @return InventoryRepository
     */
    public InventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }
}
