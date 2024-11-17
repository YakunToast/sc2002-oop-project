package hms.view;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.Optional;

import hms.model.user.*;
import hms.model.medication.*;
import hms.model.appointment.*;
import hms.repository.RepositoryManager;
import hms.model.schedule.TimeSlot;

public class AdministratorView {
    private Administrator administrator;
    private final RepositoryManager repositoryManager;

    public AdministratorView(Administrator administrator) {
        this.administrator = administrator;
        this.repositoryManager = RepositoryManager.getInstance();
    }

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
        //TODO: find a way to get all staff
        List<Staff> staffList = repositoryManager.getUserRepository().getAllStaff();
        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
            return;
        }

        System.out.println("\nStaff List:");
        System.out.println("ID\tRole\tName");
        System.out.println("----------------------------------------");
        for (Staff staff : staffList) {
            System.out.printf("%s\t%s\t%s %s\t%s",
                staff.getId(),
                staff.getRole(),
                staff.getFirstName(),
                staff.getLastName()
                );
        }
    }

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
            newStaff = new Doctor(UUID.randomUUID().toString(), username, firstName, lastName, "password", email, number);
        } else {
            newStaff = new Pharmacist(UUID.randomUUID().toString(), username, firstName, lastName, "password", email, number, role);
        }
        //TODO: find a way to add a staff
        repositoryManager.getUserRepository().add(newStaff);
        System.out.println("Staff member added successfully! ID: " + newStaff.getId());
    }

    private void updateStaffMember(Scanner sc) {
        System.out.print("Enter staff username to update: ");
        String staffUsername = sc.nextLine();
        
        //TODO: find a way to get staff by username
        Optional<Staff> staffOpt = repositoryManager.getUserRepository().getStaffByUsername(staffUsername);
        if (staffOpt.isEmpty()) {
            System.out.println("Staff member not found!");
            return;
        }
        
        Staff staff = staffOpt.get();
        System.out.println("Current Information:");
        System.out.printf("Name: %s %s%nRole: %s%n",
            staff.getFirstName(), staff.getLastName(), staff.getRole());
        
        System.out.print("Enter new first name (or press Enter to keep current): ");
        String firstName = sc.nextLine();
        //TODO: find a better way to set the name, or need to add methods on staff
        if (!firstName.isEmpty()) {
            staff.setFirstName(firstName);
        }
        
        System.out.print("Enter new last name (or press Enter to keep current): ");
        String lastName = sc.nextLine();
        if (!lastName.isEmpty()) {
            staff.setLastName(lastName);
        }
        //TODO: find a way to update staff
        repositoryManager.getUserRepository().update(staff);
        System.out.println("Staff member updated successfully!");
    }

    private void removeStaffMember(Scanner sc) {
        System.out.print("Enter staff username to remove: ");
        String staffUsername = sc.nextLine();
        //TODO: find a way to get staff by username, can't convert from Optional<User> to Optional<Staff>
        Optional<Staff> staffOpt = repositoryManager.getUserRepository().getStaffByUsername(staffUsername);
        if (staffOpt.isEmpty()) {
            System.out.println("Staff member not found!");
            return;
        }
        //TODO: find a way to remove the staff, can use adminController?
        System.out.print("Are you sure you want to remove this staff member? (y/n): ");
        if (sc.nextLine().toLowerCase().startsWith("y")) {
            repositoryManager.getUserRepository().remove(staffOpt.get());
            System.out.println("Staff member removed successfully!");
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    //not sure this is needed, just for additional feature
    private void filterStaffList(Scanner sc) {
        System.out.println("\nFilter by:");
        System.out.println("1. Role");
        System.out.println("2. Name");
        
        System.out.print("Choose filter option: ");
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        
        List<Staff> filteredList;
        switch (option) {
            case 1 -> {
                System.out.print("Enter role (DOCTOR/PHARMACIST): ");
                UserRole role = UserRole.valueOf(sc.nextLine().toUpperCase());
                //TODO: find a way to get a staff by role
                filteredList = repositoryManager.getUserRepository().getStaffByRole(role);
            }
            case 2 -> {
                System.out.print("Enter name to search: ");
                String name = sc.nextLine();
                //TODO: find a way to get a staff by name
                filteredList = repositoryManager.getUserRepository().getStaffByName(name);
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
            System.out.printf("%s\t%s\t%s %s\t%n",
                staff.getId(),
                staff.getRole(),
                staff.getFirstName(),
                staff.getLastName());
        }
    }

    private void viewAppointmentDetails(Scanner sc) {
        System.out.println("\n=== Appointment Details ===");
        List<Appointment> appointments = repositoryManager.getAppointmentRepository().getAllAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        
        System.out.println("Patient ID\tDoctor ID\tStart\t\tEnd\tStatus");
        System.out.println("--------------------------------------------------------");
        for (Appointment appointment : appointments) {
            //TODO: How get a single timeslot in each appointment
            TimeSlot slot = appointment.getTimeSlot();
            System.out.printf("%s\t\t%s\t\t%s\t%s\t%s%n",
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                slot.getStart(),
                slot.getEnd(),
                appointment.getStatus());
        }
    }

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
        Inventory inventory = repositoryManager.getInventoryRepository().getInventory();
        List<Medication> medications = inventory.getMedications();
        
        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }
        //TODO: How can we get the medication details?
        System.out.println("\nCurrent Inventory:");
        System.out.println("Name\tStock Level\tLow Stock Alert Level");
        System.out.println("----------------------------------------");
        for (Medication med : medications) {
            System.out.printf("%s\t%d\t\t%d%n",
                med.getName(),
                med.getStockLevel(),
                med.getLowStockAlertLevel());
        }
    }

    private void addNewMedication(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();
        
        System.out.print("Enter medication description: ");
        int description = sc.nextInt();

        System.out.print("Enter dosage instruction : ");
        int dosageInstructions = sc.nextInt();

        //I think we need this
        System.out.print("Enter initial stock level: ");
        int stockLevel = sc.nextInt();
        
        System.out.print("Enter low stock alert level: ");
        int alertLevel = sc.nextInt();
        sc.nextLine(); // Consume newline
        
        //is medication sideEffects really needed?
        Medication newMed = new Medication(name, description, dosageInstructions, []);
        repositoryManager.getInventoryRepository().addMedication(newMed);
        System.out.println("Medication added successfully!");
    }

    //TODO: we should have a stock level right?
    private void updateStockLevel(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();
        
        Optional<Medication> medOpt = repositoryManager.getInventoryRepository().
            .getMedicationByName(name);
            
        if (medOpt.isEmpty()) {
            System.out.println("Medication not found!");
            return;
        }
        
        System.out.print("Enter new stock level: ");
        int newLevel = sc.nextInt();
        sc.nextLine(); // Consume newline
        
        Medication med = medOpt.get();
        med.setStockLevel(newLevel);
        repositoryManager.getInventoryRepository().updateMedication(med);
        System.out.println("Stock level updated successfully!");
    }
    //TODO: we should have an alert for stock level right?
    private void updateLowStockAlertLevel(Scanner sc) {
        System.out.print("Enter medication name: ");
        String name = sc.nextLine();
        
        Optional<Medication> medOpt = repositoryManager.getInventoryRepository()
            .getMedicationByName(name);
            
        if (medOpt.isEmpty()) {
            System.out.println("Medication not found!");
            return;
        }
        
        System.out.print("Enter new low stock alert level: ");
        int newLevel = sc.nextInt();
        sc.nextLine(); // Consume newline
        
        Medication med = medOpt.get();
        med.setLowStockAlertLevel(newLevel);
        repositoryManager.getInventoryRepository().updateMedication(med);
        System.out.println("Low stock alert level updated successfully!");
    }

    private void approveReplenishmentRequests(Scanner sc) {
        List<ReplenishmentRequest> requests = repositoryManager.getInventoryRepository().
            .getPendingReplenishmentRequests();
            
        if (requests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }
        
        System.out.println("\nPending Replenishment Requests:");
        System.out.println("ID\tMedication\tRequested Amount\tRequested By");
        System.out.println("--------------------------------------------------");
        for (ReplenishmentRequest request : requests) {
            System.out.printf("%s\t%s\t%d\t\t%s%n",
                request.getId(),
                request.getMedication().getName(),
                request.getRequestedAmount(),
                request.getRequestedBy().getFirstName());
        }
        
        System.out.print("\nEnter request ID to approve (or 0 to cancel): ");
        String requestId = sc.nextLine();
        
        if (requestId.equals("0")) {
            return;
        }
        
        Optional<ReplenishmentRequest> requestOpt = requests.stream()
            .filter(r -> r.getId().equals(requestId))
            .findFirst();
            
        if (requestOpt.isEmpty()) {
            System.out.println("Request not found!");
            return;
        }
        
        ReplenishmentRequest request = requestOpt.get();
        Medication medication = request.getMedication();
        medication.setStockLevel(medication.getStockLevel() + request.getRequestedAmount());
        
        repositoryManager.getInventoryRepository().updateMedication(medication);
        repositoryManager.getInventoryRepository().approveReplenishmentRequest(request);
        
        System.out.println("Request approved successfully! Stock level updated.");
    }
}