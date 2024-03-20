package com.example.icecreamfactory;

import com.example.icecreamfactory.dao.UserDto;
import com.example.icecreamfactory.entity.Role;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.RoleRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("password");
        userDto.setRoles(new ArrayList<Role>());

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new User());

        userService.saveUser(userDto);

        verify(userRepository).save(any());
        verify(passwordEncoder).encode(eq("password"));
    }

    @Test
    public void testFindUserByEmail() {
        String email = "john@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.findUserByEmail(email);

        assertEquals(user, result);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("Imam Hosssain");
        user1.setPassword("1234");
        users.add(user1);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> result = userService.findAllUsers();

        assertEquals(users.size(), result.size());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    public void testFindByUserId() {
        Long userId = 1L;
        User user = new User();
        user.setName("imam");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.findByUserId(userId);

        assertNotNull(result);
    }

    @Test
    public void testUpdateUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("john@example.com");
        userDto.setPassword("encodedPassword");

        User existingUser = new User();
        existingUser.setEmail("john@example.com");

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(existingUser);
        when(userRepository.save(any())).thenReturn(existingUser);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        UserDto result = userService.updateUser(userDto);

        assertNotNull(result);
        verify(userRepository).findByEmail(eq(userDto.getEmail()));
        verify(userRepository).save(any());
        verify(passwordEncoder).encode(any());
    }
}
