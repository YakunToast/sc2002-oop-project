/*
 * This source file was generated by the Gradle 'init' task
 */
package hms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import hms.controller.DoctorController;
import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.repository.RepositoryManager;
import hms.view.MainView;

public class App {
    public static RepositoryManager rm;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Prepare repositories
        RepositoryManager.load();
        rm = RepositoryManager.getInstance();

        loadPatientsFromExcel("Patient_List.xlsx");
        loadMedicinesFromExcel("Medicine_List.xlsx");
        loadStaffsFromExcel("Staff_List.xlsx");

        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                new Runnable() {
                                    public void run() {
                                        rm.save();
                                    }
                                }));

        // Create sample users
        Patient p1 = new Patient("P1", "pat", "first", "patient", "password", "abc@xyz.com", "+1234");
        Doctor d1 = new Doctor("D1", "doc", "first", "doctor", "password", "cba@xyz.com", "+1234");
        Pharmacist h1 =
                new Pharmacist("H1", "pha", "pha", "doctor", "password", "cba@xyz.com", "+1234");
        Administrator a1 =
                new Administrator("A1", "adm", "first", "doctor", "password", "cba@xyz.com", "+1234");

        new DoctorController(d1)
                .addAppointmentHourly(
                        LocalDateTime.of(2024, 11, 19, 07, 00),
                        LocalDateTime.of(2024, 11, 19, 19, 00));

        // Save sample users
        if (rm.getUserRepository().getUserById("P1").isEmpty()) {
            System.out.println("Creating Patient pat...");
            rm.getUserRepository().addUser(p1);
        }
        if (rm.getUserRepository().getUserById("D1").isEmpty()) {
            System.out.println("Creating Doctor doc...");
            rm.getUserRepository().addUser(d1);
        }
        if (rm.getUserRepository().getUserById("H1").isEmpty()) {
            System.out.println("Creating Pharmacist pha...");
            rm.getUserRepository().addUser(h1);
        }
        if (rm.getUserRepository().getUserById("A1").isEmpty()) {
            System.out.println("Creating Admin adm...");
            rm.getUserRepository().addUser(a1);
        }

        // Initialise view
        MainView mv = new MainView();
        mv.start();
    }

    
    /** 
     * @param filePath
     */
    public static void loadPatientsFromExcel(String filePath) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            // Skip first row
            rowIterator.next();

            // Loop through all rows
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                String patientID = cellIterator.next().getStringCellValue();
                String name = cellIterator.next().getStringCellValue();
                String firstName = name.split(" ")[0]; // TODO: Naive approach
                String lastName = name.split(" ")[1];
                String dateOfBirth = cellIterator.next().getStringCellValue();
                String gender = cellIterator.next().getStringCellValue();
                String bloodType = cellIterator.next().getStringCellValue();
                String contactInformation = cellIterator.next().getStringCellValue();

                // Create new "Patient"
                Patient patient =
                        new Patient(
                                patientID,
                                patientID,
                                firstName,
                                lastName,
                                "defaultPassword",
                                contactInformation,
                                contactInformation,
                                dateOfBirth,
                                gender,
                                bloodType);

                // Check if exists, if not add
                if (RepositoryManager.getInstance().getUserRepository().getUserById(patientID)
                        == null) {
                    RepositoryManager.getInstance().getUserRepository().addUser(patient);
                }
            }
            file.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @param filePath
     */
    public static void loadMedicinesFromExcel(String filePath) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            // Skip first row
            rowIterator.next();

            // Loop through all rows
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                String name = cellIterator.next().getStringCellValue();
                int initialStock = (int) cellIterator.next().getNumericCellValue();
                int lowStock = (int) cellIterator.next().getNumericCellValue();

                // Create new "Medication"
                Medication medication = new Medication(name, "", "", null, null);

                // Check if exists, if not add
                Inventory inventory = rm.getInventoryRepository().getInventory();
                Optional<Medication> medicationOpt =
                        rm.getInventoryRepository().getInventory().getMedicationByName(name);
                if (!medicationOpt.isPresent()) {
                    rm.getInventoryRepository().getInventory().addMedication(medication);
                } else {
                    medication = medicationOpt.get();
                }
                inventory.setMedicationStock(medication, initialStock);
                inventory.setMedicationStockAlert(medication, lowStock);
            }
            file.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @param filePath
     */
    public static void loadStaffsFromExcel(String filePath) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            // Skip first row
            rowIterator.next();

            // Loop through all rows
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                String staffId = cellIterator.next().getStringCellValue();
                String name = cellIterator.next().getStringCellValue();
                String firstName = name.split(" ")[0]; // TODO: Naive approach
                String lastName = name.split(" ")[1];
                String role = cellIterator.next().getStringCellValue();
                String gender = cellIterator.next().getStringCellValue();
                int age = (int) cellIterator.next().getNumericCellValue();

                // TODO: need to somehow solid this
                switch (role) {
                    case "Doctor" -> {
                        Doctor doctor =
                                new Doctor(
                                        staffId,
                                        staffId,
                                        firstName,
                                        lastName,
                                        "pass",
                                        "null@email",
                                        "");

                        // Check if exists, if not add
                        if (rm.getUserRepository().getUserById(staffId) == null) {
                            rm.getUserRepository().addUser(doctor);
                        }
                    }
                    case "Pharmacist" -> {
                        Pharmacist pharmacist =
                                new Pharmacist(
                                        staffId,
                                        staffId,
                                        firstName,
                                        lastName,
                                        "pass",
                                        "null@email",
                                        "");

                        // Check if exists, if not add
                        if (rm.getUserRepository().getUserById(staffId) == null) {
                            rm.getUserRepository().addUser(pharmacist);
                        }
                    }
                    case "Administrator" -> {
                        Administrator administrator =
                                new Administrator(
                                        staffId,
                                        staffId,
                                        firstName,
                                        lastName,
                                        "pass",
                                        "null@email",
                                        "");

                        // Check if exists, if not add
                        if (rm.getUserRepository().getUserById(staffId) == null) {
                            rm.getUserRepository().addUser(administrator);
                        }
                    }
                }
            }
            file.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
