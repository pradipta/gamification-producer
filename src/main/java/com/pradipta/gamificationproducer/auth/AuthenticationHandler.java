package com.pradipta.gamificationproducer.auth;

import com.pradipta.gamificationproducer.auth.dto.JwtAuthenticationResponse;
import com.pradipta.gamificationproducer.auth.dto.LoginRequest;
import com.pradipta.gamificationproducer.auth.dto.SignUpRequest;
import com.pradipta.gamificationproducer.auth.dto.SignUpResponse;
import com.pradipta.gamificationproducer.exception.AppException;
import com.pradipta.gamificationproducer.models.role.Role;
import com.pradipta.gamificationproducer.models.role.RoleName;
import com.pradipta.gamificationproducer.models.role.RoleRepository;
import com.pradipta.gamificationproducer.models.user.User;
import com.pradipta.gamificationproducer.models.user.UserRepository;
import com.pradipta.gamificationproducer.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Component
public class AuthenticationHandler {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<?> authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    public ResponseEntity<?> signUpUser(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new ResponseEntity(new SignUpResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(
                request.getName(),
                request.getUsername(),
                request.getEmail()
        );

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(()-> new AppException("User Role Not Set"));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new SignUpResponse(true, "User registered"));
    }
}