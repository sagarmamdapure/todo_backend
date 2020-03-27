package com.example.todospringboot.controller;

import com.example.todospringboot.entity.Role;
import com.example.todospringboot.entity.User;
import com.example.todospringboot.models.ERole;
import com.example.todospringboot.payload.request.LoginRequest;
import com.example.todospringboot.payload.request.SignupRequest;
import com.example.todospringboot.payload.response.JwtResponse;
import com.example.todospringboot.payload.response.MessageResponse;
import com.example.todospringboot.security.UserDetailsImpl;
import com.example.todospringboot.security.jwt.JwtUtils;
import com.example.todospringboot.service.RoleRepositoryService;
import com.example.todospringboot.service.UserRepositoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  final AuthenticationManager authenticationManager;

  final UserRepositoryService userRepositoryService;

  final RoleRepositoryService roleRepositoryService;

  final PasswordEncoder encoder;

  final JwtUtils jwtUtils;

  public AuthController(
          AuthenticationManager authenticationManager,
          UserRepositoryService userRepositoryService,
          RoleRepositoryService roleRepositoryService,
          PasswordEncoder encoder,
          JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.userRepositoryService = userRepositoryService;
    this.roleRepositoryService = roleRepositoryService;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

    try {
      SecurityContext ctx = SecurityContextHolder.createEmptyContext();
      SecurityContextHolder.setContext(ctx);
      ctx.setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles =
              userDetails.getAuthorities().stream()
                      .map(GrantedAuthority::getAuthority)
                      .collect(Collectors.toList());

      return ResponseEntity.ok(
              new JwtResponse(
                      jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepositoryService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepositoryService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user =
            new User(
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole =
              roleRepositoryService
                      .findByName(ERole.ROLE_USER)
                      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(
              role -> {
                switch (role) {
                  case "admin":
                    Role adminRole =
                            roleRepositoryService
                                    .findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                  case "mod":
                    Role modRole =
                            roleRepositoryService
                                    .findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                  default:
                    Role userRole =
                            roleRepositoryService
                                    .findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
              });
    }

    user.setRoles(roles);
    userRepositoryService.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
