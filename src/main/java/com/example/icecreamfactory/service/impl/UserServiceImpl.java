package com.example.icecreamfactory.service.impl;

import com.example.icecreamfactory.dao.UserDto;
import com.example.icecreamfactory.entity.Role;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.RoleRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(userDto.getRoles());
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findByUserId(Long id) {
        User user = userRepository.findById(id).orElse(null);
        UserDto userDto = mapToUserDto(user);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        System.out.println("Userdto string"+userDto.toString());
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        existingUser.setName(userDto.getFirstName()+ ' ' +userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        if(!userDto.getPassword().isEmpty() && userDto.getPassword() != null){
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if(!userDto.getRoles().isEmpty() && userDto.getRoles() != null) {
            existingUser.setRoles(userDto.getRoles());
        }
        return mapToUserDto(userRepository.save(existingUser));
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
