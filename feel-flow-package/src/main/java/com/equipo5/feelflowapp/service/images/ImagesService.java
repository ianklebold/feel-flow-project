package com.equipo5.feelflowapp.service.images;

import com.equipo5.feelflowapp.dto.images.ImagesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImagesService {
    ImagesDto getImageOfTheCurrentUser();

    ImagesDto getImageOfTheCurrentEnterprise();

    ImagesDto getImageOfTheCurrentTeam();


    void saveImageOfTheUser(MultipartFile imageFile) throws IOException;

    void saveImageOfTheUser(UUID id, MultipartFile imageFile) throws IOException;

    void saveImageOfTheEnterprise(MultipartFile imageFile) throws IOException;

    void saveImageOfTheEnterprise(UUID id, MultipartFile imageFile) throws IOException;

    void saveImageOfTheTeam(UUID id, MultipartFile imageFile) throws IOException;

}
