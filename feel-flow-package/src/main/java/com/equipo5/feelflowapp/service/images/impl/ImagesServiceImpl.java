package com.equipo5.feelflowapp.service.images.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.images.MediaImage;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.images.ImagesDto;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.images.ImagesMapper;
import com.equipo5.feelflowapp.repository.enterprise.EnterpriseRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.enterprise.EnterpriseService;
import com.equipo5.feelflowapp.service.images.ImagesService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final EnterpriseRepository enterpriseRepository;

    private final ImagesMapper imagesMapper;

    private final EnterpriseService enterpriseService;

    private final TeamRepository teamRepository;

    @Override
    public ImagesDto getImageOfTheCurrentUser() {
        String username = userService.getUsernameByCurrentUser();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {

            //Maper de imagen
            if( user.get().getMediaImage() != null ){
                return imagesMapper.mediaImageToImageDto( user.get().getMediaImage() );
            }

        }

        return null;
    }

    @Override
    public ImagesDto getImageByUserId(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        return user.map(value -> imagesMapper.mediaImageToImageDto(value.getMediaImage())).orElse(null);
    }

    @Override
    public ImagesDto getImageByTeamId(UUID uuid) {
        Optional<Team> team = teamRepository.findById(uuid);
        return team.map(value -> imagesMapper.mediaImageToImageDto(value.getLogo())).orElse(null);
    }

    @Override
    public ImagesDto getImageOfTheCurrentEnterprise() {
        Optional<? extends GrantedAuthority> role = userService.getRoleByCurrentUser();

        if (role.isEmpty()) {
            return null;
        }

        Optional<EnterPrise> enterprise = enterpriseService.getEnterpriseByCurrentUser(role.get());

        if (enterprise.isPresent()) {

            if( enterprise.get().getLogo() != null ){
                return imagesMapper.mediaImageToImageDto( enterprise.get().getLogo() );
            }

        }

        return null;
    }

    @Override
    public ImagesDto getImageOfTheCurrentTeam() {
        String username = userService.getUsernameByCurrentUser();
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            Team team = teamRepository.findTeamByUUIDUser(user.get().getUuid().toString());

            if( team != null ){
                return imagesMapper.mediaImageToImageDto( team.getLogo() );
            }

        }

        return null;
    }

    @Override
    public void saveImageOfTheUser(MultipartFile imageFile) throws IOException {
        String username = userService.getUsernameByCurrentUser();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            saveImageForUser(user.get(), imageFile);
        }

    }

    @Override
    public void saveImageOfTheUser(UUID id, MultipartFile imageFile) throws IOException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            saveImageForUser(user.get(), imageFile);
        }else{
            throw new NotFoundException("User not found");
        }
    }

    private void saveImageForUser(User user, MultipartFile imageFile) throws IOException {
        MediaImage mediaImage = MediaImage.builder()
                .name(imageFile.getOriginalFilename())
                .fileType(imageFile.getContentType())
                .fileData(Base64.getEncoder().encodeToString(imageFile.getBytes()))
                .build();

        user.setMediaImage( mediaImage );
        userRepository.save( user );
    }

    @Override
    public void saveImageOfTheEnterprise(MultipartFile imageFile) throws IOException {
        Optional<EnterPrise> enterprise = enterpriseService.getEnterpriseByCurrentUser();

        if (enterprise.isPresent()) {
            saveImageForEnterprise(enterprise.get(), imageFile);
        }
    }

    @Override
    public void saveImageOfTheEnterprise(UUID id, MultipartFile imageFile) throws IOException {
        Optional<EnterPrise> enterPrise = enterpriseRepository.findById(id);

        if (enterPrise.isPresent()) {
            saveImageForEnterprise(enterPrise.get(), imageFile);
        }else {
            throw new NotFoundException("Enterprise not found");
        }

    }

    @Override
    public void saveImageOfTheTeam(UUID id, MultipartFile imageFile) throws IOException {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            saveImageForTeam(team.get(), imageFile);
        }else{
            throw new NotFoundException("Team not found");
        }

    }

    private void saveImageForEnterprise(EnterPrise enterPrise, MultipartFile imageFile) throws IOException {
        MediaImage mediaImage = MediaImage.builder()
                .name(imageFile.getOriginalFilename())
                .fileType(imageFile.getContentType())
                .fileData(Base64.getEncoder().encodeToString(imageFile.getBytes()))
                .build();

        enterPrise.setLogo( mediaImage );
        enterpriseRepository.save( enterPrise );
    }

    private void saveImageForTeam(Team team, MultipartFile imageFile) throws IOException {
        MediaImage mediaImage = MediaImage.builder()
                .name(imageFile.getOriginalFilename())
                .fileType(imageFile.getContentType())
                .fileData(Base64.getEncoder().encodeToString(imageFile.getBytes()))
                .build();

        team.setLogo( mediaImage );
        teamRepository.save( team );
    }


}
