package com.equipo5.feelflowapp.mappers.images;

import com.equipo5.feelflowapp.domain.images.MediaImage;
import com.equipo5.feelflowapp.dto.images.ImagesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ImagesMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "fileType",target = "fileType")
    @Mapping(source = "fileData",target = "fileData", qualifiedByName = "convertBase64ToBytes")
    ImagesDto mediaImageToImageDto(MediaImage image);


    @Named("convertBase64ToBytes")
    public static byte[] convertBase64ToBytes(String base64) {
        return java.util.Base64.getDecoder().decode(base64);
    }


}
