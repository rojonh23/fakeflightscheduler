package com.fake.flightscheduler.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fake.flightscheduler.security.dto.request.LoginRequestDto;
import com.fake.flightscheduler.security.dto.request.UserRequestDto;
import com.fake.flightscheduler.security.dto.response.MessageResponseDto;
import com.fake.flightscheduler.security.dto.response.UserResponseDto;
import com.fake.flightscheduler.security.entity.Role;
import com.fake.flightscheduler.security.entity.RoleType;
import com.fake.flightscheduler.security.entity.User;
import com.fake.flightscheduler.security.jwt.JwtUtil;
import com.fake.flightscheduler.security.repo.RoleRepository;
import com.fake.flightscheduler.security.repo.UserRepository;
import com.fake.flightscheduler.security.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	  private final AuthenticationManager authenticationManager;

	  private final UserRepository userRepository;

	  private final RoleRepository roleRepository;

	  private final PasswordEncoder encoder;

	  private final JwtUtil jwtUtil;

	  @PostMapping("/signup")
	  public ResponseEntity<MessageResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
	    if (userRepository.existsByUsername(userRequestDto.getUsername())) {
	      return ResponseEntity.badRequest().body(new MessageResponseDto("Error: Username is already taken!"));
	    }

	    // Create new user
	    User user = new User(userRequestDto.getUsername(),
	                         encoder.encode(userRequestDto.getPassword()));

	    Set<String> strRoles = userRequestDto.getRoles();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {
	        if("admin".equals(role)) {
	          Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(adminRole);
	        } else {
	        	Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
	  	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	  	          roles.add(userRole);
	        }
	      });
	    }

	    user.setRoles(roles);
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponseDto("User registered successfully!"));
	  }

	  @PostMapping("/login")
	  public ResponseEntity<UserResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {

	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);

	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(GrantedAuthority::getAuthority)
	        .collect(Collectors.toList());

	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
	        .body(new UserResponseDto(userDetails.getId(),
	                                   userDetails.getUsername(),
	                                   roles));
	  }

	  @PostMapping("/logout")
	  public ResponseEntity<MessageResponseDto> logoutUser() {
	    ResponseCookie cookie = jwtUtil.getCleanJwtCookie();
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
	        .body(new MessageResponseDto("You've been logged out!"));
	  }

}
