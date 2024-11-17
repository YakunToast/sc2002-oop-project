package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.UserController;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginSystemTest {
    private UserController userController;
    private Patient testPatient;
    private Doctor testDoctor;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();
        testPatient = TestUtils.createTestPatient();
        testDoctor = TestUtils.createTestDoctor();
        userController = new UserController();
    }

    @Test
    @DisplayName("Test Case 25: First-Time Login and Password Change")
    void testFirstTimeLoginAndPasswordChange() {
        // Test first-time login with default password
        User user = userController.login(testPatient.getId(), "password");
        assertNotNull(user);
        assertTrue(user instanceof Patient);

        // Test password change
        String newPassword = "newSecurePassword123";
        boolean changed =
                userController.changePassword(testPatient.getId(), "password", newPassword);
        assertTrue(changed);

        // Test login with new password
        user = userController.login(testPatient.getId(), newPassword);
        assertNotNull(user);
        assertEquals(testPatient.getId(), user.getId());

        // Test login with old password (should fail)
        User failedLogin = userController.login(testPatient.getId(), "password");
        assertNull(failedLogin);
    }

    @Test
    @DisplayName("Test Case 26: Login with Incorrect Credentials")
    void testLoginWithIncorrectCredentials() {
        // Test login with incorrect password
        User failedLogin = userController.login(testPatient.getId(), "wrongpassword");
        assertNull(failedLogin);

        // Test login with non-existent user ID
        failedLogin = userController.login("nonexistentID", "password");
        assertNull(failedLogin);

        // Test login with null credentials
        assertThrows(IllegalArgumentException.class, () -> userController.login(null, "password"));
        assertThrows(
                IllegalArgumentException.class,
                () -> userController.login(testPatient.getId(), null));

        // Test login with empty credentials
        User emptyLogin = userController.login("", "");
        assertNull(emptyLogin);
    }
}
