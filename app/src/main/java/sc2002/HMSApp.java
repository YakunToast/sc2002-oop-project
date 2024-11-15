/*
 * This source file was generated by the Gradle 'init' task
 */
package sc2002;

import sc2002.cli.MainView;
import sc2002.controller.PatientController;
import sc2002.model.role.Patient;
import sc2002.model.role.UserRole;

public class HMSApp {
    public static void main(String[] args) {
        // Create sample users
        PatientController.add(new Patient("abc", "first", "last", "password", "abc@xyz.com", "+1234141", UserRole.PATIENT));

        // Initialise view
        MainView mv = new MainView();
        mv.start();
    }
}
