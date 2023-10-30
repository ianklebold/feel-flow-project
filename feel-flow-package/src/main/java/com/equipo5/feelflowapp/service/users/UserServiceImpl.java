package com.equipo5.feelflowapp.service.users;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.enterprise.EnterpriseInfoHomeDTO;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    private final TeamLeaderRepository teamLeaderRepository;

    private final RegularUserRepository regularUserRepository;

    @Override
    @Transactional
    public Optional<UserDTO> updateUser(UUID uuidUser, UserUpdateDTO userUpdateDTO) {

        Optional<User> userDTO = userRepository.findById(uuidUser);

        if (userDTO.isPresent()){

            User user = userDTO.get();

            user.setName(userUpdateDTO.getName());
            user.setSurname(userUpdateDTO.getSurname());
            user.setUsername(userUpdateDTO.getUsername());


            userRepository.save(user);

            return Optional.of(userMapper.userToUserDto(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> getUserById(UUID uuid) {
        Optional<? extends GrantedAuthority> role = getRoleByCurrentUser();

        if (role.isPresent()){
            if (TeamRoles.ADMIN.name().equals(role.get().getAuthority())){
                Optional<Admin> admin = adminRepository.findById(uuid);

                if (admin.isPresent()){
                    UserDTO userDTO = userMapper.userToUserDto(admin.get());
                    populateEnterprise(admin.get(),userDTO);
                    return Optional.of(userDTO);
                }

            }else if (TeamRoles.TEAM_LEADER.name().equals(role.get().getAuthority())){
                Optional<TeamLeader> teamLeader = teamLeaderRepository.findById(uuid);

                if (teamLeader.isPresent()){
                    UserDTO userDTO = userMapper.userToUserDto(teamLeader.get());
                    populateEnterprise(teamLeader.get().getTeam(),userDTO);
                    return Optional.of(userDTO);
                }

            }else {
                Optional<RegularUser> regularUser = regularUserRepository.findById(uuid);

                if (regularUser.isPresent()){
                    UserDTO userDTO = userMapper.userToUserDto(regularUser.get());
                    populateEnterprise(regularUser.get().getTeam(),userDTO);
                    return Optional.of(userDTO);
                }

            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<? extends GrantedAuthority> getRoleByCurrentUser(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst();
    }

    @Override
    public String getUsernameByCurrentUser() {
         return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void populateEnterprise(Admin admin, UserDTO userTarget){
        userTarget.setEnterpriseInfoHomeDTO(EnterpriseInfoHomeDTO.builder()
                .uuid(admin.getEnterPrise().getUuid())
                .name(admin.getEnterPrise().getName())
                .build()
        );
    }

    private void populateEnterprise(Team team, UserDTO userTarget){
        userTarget.setEnterpriseInfoHomeDTO(EnterpriseInfoHomeDTO.builder()
                .uuid(team.getEnterPrise().getUuid())
                .name(team.getEnterPrise().getName())
                .build()
        );
    }


}
