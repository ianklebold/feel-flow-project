package com.equipo5.feelflowapp.service.auth;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()){
            throw new UsernameNotFoundException(String.format("Usuario incorrecto"));
        }

        User userExist = user.get();

        List<GrantedAuthority> authorities = new ArrayList<>(
                userExist.getAuthorities()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getTeamRoles().toString()))
                        .toList()
        );

        return new org.springframework.security.core.userdetails.User(
                userExist.getEmail(),
                userExist.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
