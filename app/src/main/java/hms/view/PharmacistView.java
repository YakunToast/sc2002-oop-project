package hms.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.model.medication.Prescription;
import hms.model.medication.PrescriptionStatus;
import hms.model.user.Pharmacist;
import hms.repository.RepositoryManager;

public class PharmacistView {
    private Pharmacist pharmacist;
    private final RepositoryManager repositoryManager;

    public PharmacistView(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.repositoryManager = RepositoryManager.getInstance();
    }

    void start(Scanner sc) {
        while (true) {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            
            try {
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1 -> viewAppointmentOutcomeRecords(sc);
                    case 2 -> updatePrescriptionStatus(sc);
                    case 3 -> viewMedicationInventory(sc);
                    case 4 -> submitReplenishmentRequest(sc);
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid option! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    void viewAppointmentOutcomeRecords(Scanner sc) {
        System.out.println("\n=== Appointment Outcome Records ===");
        List<AppointmentOutcome> outcomes = repositoryManager.getAppointmentRepository().getAllAppointmentOutcomes();
        
        if (outcomes.isEmpty()) {
            System.out.println("No appointment outcomes found.");
            return;
        }

        for (AppointmentOutcome outcome : outcomes) {
            if (outcome.getPrescription() != null && !outcome.getPrescription().isEmpty()) {
                System.out.println("\nAppointment ID: " + outcome.getAppointment().getId());
                System.out.println("Date: " + outcome.getAppointment().getStart());
                System.out.println("Prescriptions:");
                for (Prescription prescription : outcome.getPrescriptions()) {
                    System.out.println("- Medication: " + prescription.getMedications().getName());
                    System.out.println("  Status: " + prescription.getPrescriptionStatus());
                }
                System.out.println("------------------------");
            }
        }
    }

    void updatePrescriptionStatus(Scanner sc) {
        System.out.println("\n=== Update Prescription Status ===");
        
        // First show all pending prescriptions
        List<AppointmentOutcome> outcomes = repositoryManager.getAppointmentRepository().getAllAppointmentOutcomes();
        boolean hasPendingPrescriptions = false;
        
        System.out.println("Pending Prescriptions:");
        for (AppointmentOutcome outcome : outcomes) {
            if (outcome.getPrescription() != null) {
                for (Prescription prescription : outcome.getPrescriptions()) {
                    if (prescription.getStatus() == PrescriptionStatus.PENDING) {
                        hasPendingPrescriptions = true;
                        System.out.println("Appointment ID: " + outcome.getAppointment().getId());
                        System.out.println("Medication: " + prescription.getMedications().getName());
                        System.out.println("Current Status: " + prescription.getPrescriptionStatus());
                        System.out.println("------------------------");
                    }
                }
            }
        }

        if (!hasPendingPrescriptions) {
            System.out.println("No pending prescriptions found.");
            return;
        }

        System.out.print("Enter Appointment ID to update: ");
        String appointmentId = sc.nextLine();

        Optional<AppointmentOutcome> outcomeOpt = outcomes.stream()
            .filter(o -> o.getAppointment().getId().equals(appointmentId))
            .findFirst();

        if (outcomeOpt.isPresent()) {
            AppointmentOutcome outcome = outcomeOpt.get();
            for (Prescription prescription : outcome.getPrescriptions()) {
                if (prescription.getStatus() == PrescriptionStatus.PENDING) {
                    System.out.println("\nMedication: " + prescription.getMedications().getName());
                    System.out.println("1. Mark as Dispensed");
                    System.out.println("2. Skip");
                    System.out.print("Choose an option: ");
                    
                    try {
                        int choice = Integer.parseInt(sc.nextLine());
                        if (choice == 1) {
                            prescription.setDispensed();
                            //how to save this?
                            repositoryManager.getAppointmentRepository().save();
                            System.out.println("Prescription status updated successfully.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Skipping this prescription.");
                    }
                }
            }
        } else {
            System.out.println("Appointment not found.");
        }
    }
    //TODO: add stock level in medication
    void viewMedicationInventory(Scanner sc) {
        System.out.println("\n=== Medication Inventory ===");
        List<Medication> medications = repositoryManager.getInventoryRepository().getAllMedications();
        
        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }

        for (Medication medication : medications) {
            System.out.println("\nName: " + medication.getName());
            System.out.println("Stock Level: " + medication.getStockLevel());
            System.out.println("Low Stock Alert Line: " + medication.getLowStockAlertLine());
            System.out.println("------------------------");
        }
    }

    void submitReplenishmentRequest(Scanner sc) {
        System.out.println("\n=== Submit Replenishment Request ===");
        
        // Show current inventory first
        List<Medication> medications = repositoryManager.getInventoryRepository().getAllMedications();
        
        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }

        System.out.println("Current Inventory:");
        for (int i = 0; i < medications.size(); i++) {
            Medication med = medications.get(i);
            System.out.println((i + 1) + ". " + med.getName() + 
                             " (Current Stock: " + med.getStockLevel() + 
                             ", Alert Line: " + med.getLowStockAlertLine() + ")");
        }

        System.out.print("Enter medication number to request replenishment: ");
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            int choice = Integer.parseInt(sc.nextLine()) - 1;
            if (choice >= 0 && choice < medications.size()) {
                Medication selectedMed = medications.get(choice);
                
                System.out.print("Enter requested quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());
                
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    return;
                }

                ReplenishmentRequest request = new ReplenishmentRequest(
                    selectedMed,
                    dateTime,
                    quantity,
                    pharmacist
                );
                
                repositoryManager.getInventoryRepository().addReplenishmentRequest(request);
                System.out.println("Replenishment request submitted successfully.");
            } else {
                System.out.println("Invalid medication number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}