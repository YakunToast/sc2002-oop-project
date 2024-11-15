package sc2002.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainView {
    public void start() {
        // Define a simple in-memory user store (replace with a database in a real
        // application)
        private static Map<String, String> users = new HashMap<>();

        // Create a Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Display login prompt
        System.out.println("Login");
        System.out.println("-----");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Authenticate user
        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
            // Add code here to proceed after successful login (e.g., display a menu,
            // redirect to another view)
            System.out.println("What would you like to do next?");
            System.out.println("1. View Dashboard");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.println("Dashboard (TODO: implement)");
                    break;
                case 2:
                    System.out.println("Logging out...");
                    start(); // Recursive call to restart the login process
                    break;
                default:

            }
        }
    }
}
