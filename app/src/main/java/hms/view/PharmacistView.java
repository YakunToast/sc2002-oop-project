package hms.view;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import hms.controller.PharmacistController;
import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.medication.PrescriptionStatus;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Pharmacist;
import hms.repository.RepositoryManager;

public class PharmacistView {
    private Pharmacist pharmacist;
    private PharmacistController pharmacistController;
    private final RepositoryManager repositoryManager;

    public PharmacistView(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.pharmacistController = new PharmacistController(pharmacist);
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
        List<AppointmentOutcome> outcomes = pharmacistController.getAppointmentOutcomes();

        if (outcomes.isEmpty()) {
            System.out.println("No appointment outcomes found.");
            return;
        }

        for (AppointmentOutcome outcome : outcomes) {
            if (outcome.getPrescription() != null && !outcome.getPrescription().isEmpty()) {
                System.out.println("\nAppointment ID: " + outcome.getAppointment().getId());
                System.out.println("Date: " + outcome.getAppointment().getStart());
                System.out.println("Prescriptions:");
                for (Prescription prescription : pharmacistController.getPendingPrescriptions()) {
                    for (Medication m : prescription.getMedications().keySet()) {
                        System.out.println("- Medication: " + m);
                    }
                    System.out.println("Status: " + prescription.getPrescriptionStatus());
                }
                System.out.println("------------------------");
            }
        }
    }

    void updatePrescriptionStatus(Scanner sc) {
        System.out.println("\n=== Update Prescription Status ===");

        // First show all pending prescriptions
        List<AppointmentOutcome> outcomes = pharmacistController.getAppointmentOutcomes();
        boolean hasPendingPrescriptions = false;

        System.out.println("Pending Prescriptions:");
        for (AppointmentOutcome outcome : outcomes) {
            if (outcome.getPrescription() != null) {
                Optional<Prescription> prescriptionOpt = outcome.getPrescription();
                if (!prescriptionOpt.isPresent()) {
                    continue;
                }

                Prescription prescription = prescriptionOpt.get();
                if (prescription.getPrescriptionStatus() == PrescriptionStatus.PENDING) {
                    hasPendingPrescriptions = true;
                    System.out.println("Appointment ID: " + outcome.getAppointment().getId());
                    for (Medication m : prescription.getMedications().keySet()) {
                        System.out.println("- Medication: " + m);
                    }
                    System.out.println("Current Status: " + prescription.getPrescriptionStatus());
                    System.out.println("------------------------");
                }
            }
        }

        if (!hasPendingPrescriptions) {
            System.out.println("No pending prescriptions found.");
            return;
        }

        System.out.print("Enter Appointment ID to update: ");
        String appointmentId = sc.nextLine();

        Optional<AppointmentOutcome> outcomeOpt =
                outcomes.stream()
                        .filter(o -> o.getAppointment().getId().equals(appointmentId))
                        .findFirst();

        if (outcomeOpt.isPresent()) {
            AppointmentOutcome outcome = outcomeOpt.get();
            Optional<Prescription> prescriptionOpt = outcome.getPrescription();
            if (!prescriptionOpt.isPresent()) {
                // TODO: better handle missing appointment ID
                return;
            }

            Prescription prescription = prescriptionOpt.get();
            if (prescription.getPrescriptionStatus() == PrescriptionStatus.PENDING) {
                for (Medication m : prescription.getMedications().keySet()) {
                    System.out.println("- Medication: " + m);
                }
                System.out.println("1. Mark as Dispensed");
                System.out.println("2. Skip");
                System.out.print("Choose an option: ");

                try {
                    int choice = Integer.parseInt(sc.nextLine());
                    if (choice == 1) {
                        pharmacistController.dispensePrescription(prescription);
                        System.out.println("Prescription status updated successfully.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Skipping this prescription.");
                }
            }
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // TODO: add stock level in medication
    void viewMedicationInventory(Scanner sc) {
        System.out.println("\n=== Medication Inventory ===");
        List<Medication> medications = pharmacistController.getMedications();

        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }

        for (Medication medication : medications) {
            System.out.println("\nName: " + medication.getName());
            System.out.println(
                    "Stock Level: " + pharmacistController.getMedicationStock(medication));
            System.out.println(
                    "Low Stock Alert Line: "
                            + pharmacistController.getMedicationStockAlert(medication));
            System.out.println("------------------------");
        }
    }

    void submitReplenishmentRequest(Scanner sc) {
        System.out.println("\n=== Submit Replenishment Request ===");

        // Show current inventory first
        List<Medication> medications = pharmacistController.getMedications();

        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }

        System.out.println("Current Inventory:");
        for (int i = 0; i < medications.size(); i++) {
            Medication med = medications.get(i);
            System.out.println(
                    (i + 1)
                            + ". "
                            + med.getName()
                            + " (Current Stock: "
                            + pharmacistController.getMedicationStock(med)
                            + ", Alert Line: "
                            + pharmacistController.getMedicationStockAlert(med)
                            + ")");
        }

        System.out.print("Enter medication number to request replenishment: ");
        try {
            int choice = Integer.parseInt(sc.nextLine()) - 1;
            if (choice >= 0 && choice < medications.size()) {
                Medication selectedMed = medications.get(choice);

                System.out.print("Enter requested quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    return;
                }

                ReplenishmentRequest request =
                        pharmacistController.createReplenishmentRequest(selectedMed, quantity);

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
