package sc2002.model;

public abstract class User {
    private String id;
    private String name;
    private UUID id;
    private String email;
    private String phoneNumber;
    private UserRole role;

    public User(String id, String name, String password, String email, String phoneNumber, UserRole role) {
        this.id = id;
        this.name = name;
        this.setPassword(password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getId() {
        return id;
    public UUID getId() {

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        // Hash password using BCrypt
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.hashedPassword);
    }
}
