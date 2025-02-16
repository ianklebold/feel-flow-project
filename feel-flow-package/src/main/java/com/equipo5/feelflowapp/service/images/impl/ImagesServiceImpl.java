package com.equipo5.feelflowapp.service.images.impl;

import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.images.ImagesDto;
import com.equipo5.feelflowapp.mappers.images.ImagesMapper;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.images.ImagesService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final ImagesMapper imagesMapper;

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


}
