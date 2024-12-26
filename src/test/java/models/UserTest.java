package models;


import com.example.militanshop.models.Role;
import com.example.militanshop.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class UserTest {

    @Test
    public void testUserRoles() {
        User user = new User();
        user.setRoles(Set.of(Role.USER, Role.ADMIN));

        assertTrue(user.getRoles().contains(Role.USER));
        assertTrue(user.getRoles().contains(Role.ADMIN));
    }

    @Test
    public void testUserProperties() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true);

        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isActive());
    }
}
