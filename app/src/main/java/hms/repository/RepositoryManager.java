package hms.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Singleton class that manages the repositories for the HMS (Hospital Management System). It
 * handles the serialization and deserialization of the repositories to and from a file.
 */
public class RepositoryManager implements Serializable {
    private static RepositoryManager instance;

    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;
    private InventoryRepository inventoryRepository;

    /** Private constructor to prevent instantiation. Initializes the repositories. */
    private RepositoryManager() {
        // Dynamically initialize repositories
        this.userRepository = new UserRepository();
        this.appointmentRepository = new AppointmentRepository();
        this.inventoryRepository = new InventoryRepository();
    }

    /**
     * Returns the singleton instance of RepositoryManager.
     *
     * @return The singleton instance of RepositoryManager.
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

    /** Destroys the singleton instance of RepositoryManager. */
    public static void destroyInstance() {
        if (instance != null) {
            synchronized (RepositoryManager.class) {
                instance = null;
            }
        }
    }

    /**
     * Serializes the RepositoryManager instance to a file.
     *
     * @param filePath The path to the file where the RepositoryManager instance will be serialized.
     * @throws IOException If an I/O error occurs during serialization.
     */
    public void serialize(String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(instance);
        }
    }

    /**
     * Saves the current state of the RepositoryManager to a file named "database.bin".
     *
     * @return true if the operation was successful, false otherwise.
     */
    public boolean save() {
        try {
            this.serialize("database.bin");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Saves the current state of the RepositoryManager to a file and logs the result. */
    public void saveAndLog() {
        if (this.save()) {
            System.out.println("saved database!");
        } else {
            System.out.println("failed to serialise database");
        }
    }

    /**
     * Deserializes the RepositoryManager instance from a file.
     *
     * @param filePath The path to the file from which the RepositoryManager instance will be
     *     deserialized.
     * @throws IOException If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    public static void deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            instance = (RepositoryManager) in.readObject();
        }
    }

    /**
     * Loads the RepositoryManager instance from a file named "database.bin". Logs an error message
     * if the file does not exist or if an error occurs during deserialization.
     */
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
     * Returns the UserRepository managed by this RepositoryManager.
     *
     * @return The UserRepository managed by this RepositoryManager.
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Returns the AppointmentRepository managed by this RepositoryManager.
     *
     * @return The AppointmentRepository managed by this RepositoryManager.
     */
    public AppointmentRepository getAppointmentRepository() {
        return appointmentRepository;
    }

    /**
     * Returns the InventoryRepository managed by this RepositoryManager.
     *
     * @return The InventoryRepository managed by this RepositoryManager.
     */
    public InventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }
}
