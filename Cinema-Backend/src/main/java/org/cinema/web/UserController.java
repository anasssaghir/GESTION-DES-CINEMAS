package org.cinema.web;

import org.cinema.dao.RoleRepository;
import org.cinema.dao.UserRepository;
import org.cinema.entities.Role;
import org.cinema.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping("/updateUserProfile")
    @Transactional
    public void updateUserProfile(@RequestBody UserProfile userProfile) {
        User user = userRepository.getOne(userProfile.getId());
        user.setFirstName(userProfile.getFirstName());
        user.setLastName(userProfile.getLastName());
        if (userProfile.getPassword() != null && userProfile.getPassword().length() > 0)
            user.setPassword(passwordEncoder.encode(userProfile.getPassword()));
        userRepository.save(user);
    }

    @PutMapping("/toggleEnaleUser")
    @Transactional
    public void toggleEnableUser(@RequestBody UserProfile userProfile) {
        User user = userRepository.getOne(userProfile.getId());
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @PostMapping("/register")
    @Transactional
    public void register(@RequestBody UserProfile userProfile) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userProfile.getPassword()));
        user.setLastName(userProfile.getLastName());
        user.setFirstName(userProfile.getFirstName());
        user.setEmail(userProfile.getEmail());
        userRepository.save(user);
    }

    @PutMapping("/updateRoles")
    @Transactional
    public void updateRoles(@RequestBody UserRoles userRoles) {
        User user = userRepository.getOne(userRoles.getUserId());
        List<Role> roles = new ArrayList<>();
        for (Long roleId : userRoles.getRoles()) {
            roles.add(roleRepository.getOne(roleId));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @PutMapping("/updateUser")
    @Transactional
    public void updateUser(@RequestBody UserProfile userProfile) {
        User user = userRepository.getOne(userProfile.getId());
        List<Role> roles = new ArrayList<>();
        for (Long roleId : userProfile.getRoles()) {
            roles.add(roleRepository.getOne(roleId));
        }
        if(userProfile.getPassword()!= null && !userProfile.getPassword().isEmpty())
        user.setPassword(passwordEncoder.encode(userProfile.getPassword()));
        user.setLastName(userProfile.getLastName());
        user.setFirstName(userProfile.getFirstName());
        user.setEmail(userProfile.getEmail());
        user.setRoles(roles);
        userRepository.save(user);
    }
}

@Data
@NoArgsConstructor
class UserProfile {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<Long> roles = new ArrayList<>();
}

@Data
@NoArgsConstructor
class UserRoles {
    private List<Long> roles = new ArrayList<>();
    private Long userId;
}
