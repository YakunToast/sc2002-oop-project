package hms;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import hms.model.user.*;
import hms.controller.UserController;

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
