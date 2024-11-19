package hms.view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import hms.controller.AdministratorController;
import hms.model.appointment.Appointment;
import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Pharmacist;
import hms.model.user.Staff;
import hms.model.user.User;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

/**
 * Represents the view for an administrator in the hospital management system. It displays various
 * options for managing hospital staff, viewing appointment details, and managing medication
 * inventory.
 */
public class AdministratorView {
    private Administrator administrator;
    private AdministratorController ac;

    /**
     * Initializes a new instance of the AdministratorView class.
     *
     * @param administrator the administrator instance associated with this view
     */
    public AdministratorView(Administrator administrator) {
        this.administrator = administrator;
        this.ac = new AdministratorController(administrator);
    }

    /**
     * Starts the administrator view, displaying a menu and handling user input.
     *
     * @param sc a Scanner object for reading user input
     * @throws RuntimeException if an error occurs during any operation
     */
    void start(Scanner sc) {
        while (true) {
            displayMenu();
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            try {
                switch (option) {
                    case 1 -> viewAndManageHospitalStaff(sc);
                    case 2 -> viewAppointmentDetails();
                    case 3 -> viewAndManageMedicationInventory(sc);
                    case 4 -> approveReplenishmentRequests(sc);
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid option! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }

            // Save after every option return
            RepositoryManager.getInstance().save();
        }
    }

    /** Displays the main menu for administrators, listing available options. */
    private void displayMenu() {
        System.out.println("\n=== Administrator Menu ===");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("0. Logout");
    }

    /**
     * Displays the staff management menu, allowing the user to view, add, update, or remove staff.
     *
     * @param sc a Scanner object for reading user input
     * @throws IllegalArgumentException if an invalid option is selected
     */
    private void viewAndManageHospitalStaff(Scanner sc) {
        while (true) {
            System.out.println("\n=== Staff Management ===");
            System.out.println("1. View Staff List");
            System.out.println("2. Add New Staff Member");
            System.out.println("3. Update Staff Information");
            System.out.println("4. Remove Staff Member");
            System.out.println("5. Filter Staff List");
            System.out.println("0. Back to Main Menu");

            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (option) {
                case 1 -> viewStaffList();
                case 2 -> addStaffMember(sc);
                case 3 -> updateStaffMember(sc);
                case 4 -> removeStaffMember(sc);
                case 5 -> filterStaffList(sc);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    /**
     * Displays a list of all hospital staff members. Retrieves the staff list from the
     * administrator controller and formats the output in a tabular form.
     *
     * @throws RuntimeException if the staff retrieval encounters an error.
     */
    private void viewStaffList() {
        // Fetch the list of all staff members.
        List<Staff> staffList = ac.getStaffs();

        // Check if the list is empty and handle accordingly.
        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
            return;
        }

        // Print the staff list in a structured format.
        System.out.println("\nStaff List:");
        System.out.println("ID\tRole\t\tName");
        System.out.println("----------------------------------------");
        for (Staff staff : staffList) {
            System.out.printf(
                    "%-7s %-15s %s %s%n",
                    staff.getId(), staff.getRole(), staff.getFirstName(), staff.getLastName());
        }
    }

    /**
     * Adds a new staff member to the system.
     *
     * <p>This method prompts the user to input the details of a new staff member, such as role,
     * username, first name, last name, email, and phone number. Based on the user input, it creates
     * a new Doctor or Pharmacist instance and adds it to the system.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void addStaffMember(Scanner sc) {
        System.out.println("\nAdd New Staff Member");

        System.out.print("Enter staff role (DOCTOR/PHARMACIST): ");
        UserRole role = UserRole.valueOf(sc.nextLine().toUpperCase());

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone number: ");
        String number = sc.nextLine();

        Staff newStaff;
        if (role == UserRole.DOCTOR) {
            newStaff =
                    new Doctor(username, username, firstName, lastName, "password", email, number);
        } else {
            newStaff =
                    new Pharmacist(
                            username, username, firstName, lastName, "password", email, number);
        }
        ac.addUser(newStaff);
        System.out.println("Staff member added successfully! ID: " + newStaff.getId());
    }

    /**
     * Updates the information of an existing staff member.
     *
     * <p>This method allows updating the first name and last name of an existing staff member. It
     * uses a username to identify the staff member and implements name changes based on user input.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void updateStaffMember(Scanner sc) {
        System.out.print("Enter staff username to update: ");
        String staffUsername = sc.nextLine();

        Optional<Staff> staffOpt = ac.getStaffByUsername(staffUsername);
        if (staffOpt.isEmpty()) {
            System.out.println("Staff member not found!");
            return;
        }

        Staff staff = staffOpt.get();
        System.out.println("Current Information:");
        System.out.printf(
                "Name: %s %s%nRole: %s%n",
                staff.getFirstName(), staff.getLastName(), staff.getRole());

        System.out.print("Enter new first name (or press Enter to keep current): ");
        String firstName = sc.nextLine();
        if (!firstName.isEmpty()) {
            staff.setFirstName(firstName);
        }

        System.out.print("Enter new last name (or press Enter to keep current): ");
        String lastName = sc.nextLine();
        if (!lastName.isEmpty()) {
            staff.setLastName(lastName);
        }

        System.out.println("Staff member updated successfully!");
    }

    /**
     * Removes a staff member from the system.
     *
     * <p>This method prompts the user to confirm the removal of a staff member identified by their
     * username. It ensures staff removal upon confirmation.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void removeStaffMember(Scanner sc) {
        System.out.print("Enter staff username to remove: ");
        String staffUsername = sc.nextLine();

        Optional<Staff> staffOpt = ac.getStaffByUsername(staffUsername);
        if (staffOpt.isEmpty()) {
            System.out.println("Staff member not found!");
            return;
        }

        System.out.print("Are you sure you want to remove this staff member? (y/n): ");
        if (sc.nextLine().toLowerCase().startsWith("y")) {
            ac.removeUser((User) staffOpt.get());
            System.out.println("Staff member removed successfully!");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    /**
     * Filters the staff list based on user-selected criteria.
     *
     * <p>This method provides options to filter the staff list either by role or by name, and then
     * displays the filtered results.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void filterStaffList(Scanner sc) {
        System.out.println("\nFilter by:");
        System.out.println("1. Role");
        System.out.println("2. Name");

        System.out.print("Choose filter option: ");
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline

        List<? extends Staff> filteredList;
        switch (option) {
            case 1 -> {
                System.out.print("Enter role (DOCTOR/PHARMACIST): ");
                UserRole role = UserRole.valueOf(sc.nextLine().toUpperCase());
                filteredList =
                        switch (role) {
                            case UserRole.DOCTOR -> ac.getDoctors();
                            case UserRole.PHARMACIST -> ac.getPharmacists();
                            default -> new ArrayList<>();
                        };
            }
            case 2 -> {
                System.out.print("Enter name to search: ");
                String name = sc.nextLine();
                filteredList =
                        ac.getStaffs().stream()
                                .filter(s -> s.getName().contains(name))
                                .collect(Collectors.toList());
            }
            default -> {
                System.out.println("Invalid option!");
                return;
            }
        }

        if (filteredList.isEmpty()) {
            System.out.println("No staff members found matching the criteria.");
            return;
        }

        System.out.println("\nFiltered Staff List:");
        System.out.println("ID\tRole\t\tName");
        System.out.println("----------------------------------------");
        for (Staff staff : filteredList) {
            System.out.printf(
                    "%-7s %-15s %s %s%n",
                    staff.getId(), staff.getRole(), staff.getFirstName(), staff.getLastName());
        }
    }

    /**
     * Displays appointment details for all scheduled appointments.
     *
     * <p>Shows details such as Patient ID, Doctor ID, appointment start time, end time, and status.
     * Only non-free appointments are displayed.
     */
    private void viewAppointmentDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("\n=== Appointment Details ===");
        List<Appointment> appointments = ac.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        System.out.println("Patient ID\tDoctor ID\tStart\t\t\tEnd\t\t\tStatus");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------");
        for (Appointment appointment : appointments) {
            if (!appointment.isFree()) {
                System.out.printf(
                        "%s\t\t%s\t\t%s\t%s\t%s%n",
                        appointment.getPatient().getId(),
                        appointment.getDoctor().getId(),
                        appointment.getStart().format(formatter),
                        appointment.getEnd().format(formatter),
                        appointment.getState());
            }
        }
    }

    /**
     * Manages the medication inventory, allowing for viewing and updating.
     *
     * <p>Provides options to view inventory, add new medications, and update stock levels or alert
     * levels. Users can choose these options via prompted commands.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void viewAndManageMedicationInventory(Scanner sc) {
        while (true) {
            System.out.println("\n=== Medication Inventory Management ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Medication");
            System.out.println("3. Update Stock Level");
            System.out.println("4. Update Low Stock Alert Level");
            System.out.println("0. Back to Main Menu");

            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (option) {
                case 1 -> viewInventory();
                case 2 -> addNewMedication(sc);
                case 3 -> updateStockLevel(sc);
                case 4 -> updateLowStockAlertLevel(sc);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    /**
     * Displays the current medication inventory.
     *
     * <p>Lists all medications with their stock levels and low stock alert settings.
     */
    private void viewInventory() {
        Inventory inventory = ac.getInventory();
        List<Medication> medications = inventory.getMedications();

        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }
        System.out.println("\nCurrent Inventory:");
        System.out.println("Name\t\t\tStock Level\tLow Stock Alert Level");
        System.out.println("----------------------------------------------------------------");
        for (Medication med : medications) {
            System.out.printf(
                    "%-23s %-15d %d%n",
                    med.getName(), ac.getMedicationStock(med), ac.getMedicationStockAlert(med));
        }
    }

    /**
     * Adds a new medication to the inventory.
     *
     * <p>Prompts the user for medication details such as name, description, dosage instructions,
     * initial stock level, and low stock alert level.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void addNewMedication(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();

        System.out.print("Enter medication description: ");
        String description = sc.nextLine();

        System.out.print("Enter dosage instruction : ");
        String dosageInstructions = sc.nextLine();

        System.out.print("Enter initial stock level: ");
        int stockLevel = sc.nextInt();

        System.out.print("Enter low stock alert level: ");
        int alertLevel = sc.nextInt();
        sc.nextLine(); // Consume newline

        Medication newMed = new Medication(name, description, dosageInstructions);
        ac.addMedication(newMed);
        ac.addMedicationStock(newMed, stockLevel);
        ac.setMedicationStockAlert(newMed, alertLevel);
        System.out.println("Medication added successfully!");
    }

    /**
     * Updates the stock level of a specified medication.
     *
     * <p>Prompts the user to input the medication name and the new stock level to be set.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void updateStockLevel(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();

        Optional<Medication> medOpt = ac.getMedicationByName(name);

        if (medOpt.isEmpty()) {
            System.out.println("Medication not found!");
            return;
        }

        System.out.print("Enter new stock level: ");
        int newLevel = sc.nextInt();
        sc.nextLine(); // Consume newline

        Medication med = medOpt.get();
        ac.setMedicationStock(med, newLevel);
        System.out.println("Stock level updated successfully!");
    }

    /**
     * Updates the low stock alert level for a specified medication.
     *
     * <p>Prompts the user to specify the medication and the new alert level.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void updateLowStockAlertLevel(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();

        Optional<Medication> medOpt = ac.getMedicationByName(name);

        if (medOpt.isEmpty()) {
            System.out.println("Medication not found!");
            return;
        }

        System.out.print("Enter new low stock alert level: ");
        int newLevel = sc.nextInt();
        sc.nextLine(); // Consume newline

        Medication med = medOpt.get();
        ac.setMedicationStockAlert(med, newLevel);
        System.out.println("Low stock alert level updated successfully!");
    }

    /**
     * Approves pending replenishment requests.
     *
     * <p>Displays pending requests and allows the user to approve them, which updates the stock
     * levels accordingly.
     *
     * @param sc A Scanner object for reading user input.
     */
    private void approveReplenishmentRequests(Scanner sc) {
        List<ReplenishmentRequest> requests = ac.getPendingReplenishmentRequests();

        if (requests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }

        System.out.println("\nPending Replenishment Requests:");
        System.out.println("ID\tMedication\t\tRequested Amount\tRequested By");
        System.out.println(
                "-----------------------------------------------------------------------------");
        for (ReplenishmentRequest request : requests) {
            System.out.printf(
                    "%d\t%-23s %-23d %s%n",
                    requests.indexOf(request) + 1,
                    request.getMedication().getName(),
                    request.getRequestedQuantity(),
                    request.getPharmacist().getName());
        }

        System.out.print("\nEnter request ID to approve (or 0 to cancel): ");
        int requestId = sc.nextInt();
        if (requestId == 0) {
            return;
        }

        ReplenishmentRequest request = requests.get(requestId - 1);
        ac.approveReplenishmentRequest(request);

        System.out.println("Request approved successfully! Stock level updated.");
    }
}
