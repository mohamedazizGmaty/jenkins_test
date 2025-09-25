package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")   // 👈 pour charger application-test.properties
class StudentManagementApplicationTests {

    @Test
    void contextLoads() {
        // Vérifie simplement que le contexte démarre
    }
}
