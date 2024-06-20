package com.nvs.th_java.service.impl;

import com.nvs.th_java.enumerable.ERole;
import com.nvs.th_java.model.User;
import com.nvs.th_java.repository.RoleRepository;
import com.nvs.th_java.repository.UserRepository;
import com.nvs.th_java.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.findByUsername(username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    @Override
    public User findByUsername(String username) throws
            UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void setDefaultRole(String username) {
        User user = findByUsername(username);
        user.getRoles().add(roleRepository.findById(ERole.USER.getValue())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with Id: " + ERole.USER.getValue())));
        userRepository.save(user);
    }

    @Override
    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}
