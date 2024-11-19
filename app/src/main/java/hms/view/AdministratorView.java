package hms.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
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

public class AdministratorView {
    private Administrator administrator;
    private AdministratorController ac;

    public AdministratorView(Administrator administrator) {
        this.administrator = administrator;
        this.ac = new AdministratorController(administrator);
       
    }

    /**
     * @param sc
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
                    case 2 -> viewAppointmentDetails(sc);
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
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Administrator Menu ===");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("0. Logout");
    }

    /**
     * @param sc
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

    private void viewStaffList() {
        // TODO: find a way to get all staff
        List<Staff> staffList = ac.getStaffs();
        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
            return;
        }

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
     * @param sc
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
                    new Doctor(
                            UUID.randomUUID().toString(),
                            username,
                            firstName,
                            lastName,
                            "password",
                            email,
                            number);
        } else {
            newStaff =
                    new Pharmacist(
                            UUID.randomUUID().toString(),
                            username,
                            firstName,
                            lastName,
                            "password",
                            email,
                            number);
        }
        ac.addUser(newStaff);
        System.out.println("Staff member added successfully! ID: " + newStaff.getId());
    }

    /**
     * @param sc
     */
    private void updateStaffMember(Scanner sc) {
        System.out.print("Enter staff username to update: ");
        String staffUsername = sc.nextLine();

        // TODO: find a way to get staff by username
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
     * @param sc
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
     * @param sc
     */
    // not sure this is needed, just for additional feature
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
        System.out.println("ID\tRole\tName");
        System.out.println("----------------------------------------");
        for (Staff staff : filteredList) {
            System.out.printf(
                    "%s\t%s\t%s %s\t%n",
                    staff.getId(), staff.getRole(), staff.getFirstName(), staff.getLastName());
        }
    }

    /**
     * @param sc
     */
    private void viewAppointmentDetails(Scanner sc) {
        System.out.println("\n=== Appointment Details ===");
        List<Appointment> appointments = ac.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        System.out.println("Patient ID\tDoctor ID\tStart\t\t\tEnd\t\t\tStatus");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < appointments.size(); i++) {
            // TODO: How get a single timeslot in each appointment
            System.out.printf(
                    "%s\t\t%s\t\t%s\t%s\t%s%n",
                    appointments.get(i).getPatient().getId(),
                    appointments.get(i).getDoctor().getId(),
                    appointments.get(i).getStart(),
                    appointments.get(i).getEnd(),
                    appointments.get(i).getStatus());
        }
    }

    /**
     * @param sc
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

    private void viewInventory() {
        Inventory inventory = ac.getInventory();
        List<Medication> medications = inventory.getMedications();

        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }
        // TODO: How can we get the medication details?
        System.out.println("\nCurrent Inventory:");
        System.out.println("Name\tStock Level\tLow Stock Alert Level");
        System.out.println("----------------------------------------");
        for (Medication med : medications) {
            System.out.printf(
                    "%s\t%d\t\t%d%n",
                    med.getName(),
                    ac.getMedicationStock(med),
                    ac.getMedicationStockAlert(med));
        }
    }

    /**
     * @param sc
     */
    private void addNewMedication(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();

        System.out.print("Enter medication description: ");
        String description = sc.nextLine();

        System.out.print("Enter dosage instruction : ");
        String dosageInstructions = sc.nextLine();

        // I think we need this
        System.out.print("Enter initial stock level: ");
        int stockLevel = sc.nextInt();

        System.out.print("Enter low stock alert level: ");
        int alertLevel = sc.nextInt();
        sc.nextLine(); // Consume newline

        // is medication sideEffects really needed?
        Medication newMed = new Medication(name, description, dosageInstructions);
        ac.addMedication(newMed);
        ac.addMedicationStock(newMed, stockLevel);
        ac.setMedicationStockAlert(newMed, alertLevel);
        System.out.println("Medication added successfully!");
    }

    /**
     * @param sc
     */
    // TODO: we should have a stock level right?
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
     * @param sc
     */
    // TODO: we should have an alert for stock level right?
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
     * @param sc
     */
    private void approveReplenishmentRequests(Scanner sc) {
        List<ReplenishmentRequest> requests =
                ac.getPendingReplenishmentRequests();

        if (requests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }

        System.out.println("\nPending Replenishment Requests:");
        System.out.println("ID\tMedication\tRequested Amount\tRequested By");
        System.out.println("--------------------------------------------------");
        for (ReplenishmentRequest request : requests) {
            System.out.printf(
                    "%d\t%s\t%d\t\t%s%n",
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

        ReplenishmentRequest request = requests.get(requestId);
        Medication medication = request.getMedication();
        ac.addMedicationStock(medication, request.getRequestedQuantity());

        System.out.println("Request approved successfully! Stock level updated.");
    }
}
