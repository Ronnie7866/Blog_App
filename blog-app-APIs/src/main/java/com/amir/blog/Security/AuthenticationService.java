package com.amir.blog.Security;

import com.amir.blog.Authentication.AuthenticationRequest;
import com.amir.blog.Authentication.AuthenticationResponse;
import com.amir.blog.Authentication.RegisterRequest;
import com.amir.blog.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import com.amir.blog.Entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
//                .password(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword())
                .email(request.getEmail())
                .about(request.getAbout())
                .build();
        userRepo.save(user);
        System.out.println("real password " + request.getPassword());
        System.out.println("encoded password " + passwordEncoder.encode(request.getPassword()));

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepo.findByEmail(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
