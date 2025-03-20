package com.equipo5.feelflowapp.service.notification.nikoniko.impl;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.images.ImagesDto;
import com.equipo5.feelflowapp.dto.modules.NikoNikoNoteDto;
import com.equipo5.feelflowapp.mappers.images.ImagesMapper;
import com.equipo5.feelflowapp.service.notification.nikoniko.NikoNikoNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NikoNikoNotificationServiceImpl implements NikoNikoNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    private final ImagesMapper imagesMapper;

    @Override
    public void sendNikoNikoNote(String note, Survey survey) {
        if (!note.isEmpty()){
            ImagesDto imagesDto = imagesMapper.mediaImageToImageDto(survey.getRegularUser().getMediaImage());
            NikoNikoNoteDto nikoNikoNoteDto = new NikoNikoNoteDto(
                    survey.getRegularUser().getName(),
                    survey.getRegularUser().getSurname(),
                    note,
                    imagesDto
            );

            String uuidEnterprise = survey.getSurveyModule().getTeam().getEnterPrise().getUuid().toString();
            String uuidTeam = survey.getSurveyModule().getTeam().getUuid().toString();
            messagingTemplate.convertAndSend("/topic/niko-niko/note/enterprise/"+uuidEnterprise, nikoNikoNoteDto);
            messagingTemplate.convertAndSend("/topic/niko-niko/note/team/"+uuidTeam, nikoNikoNoteDto);
        }
    }
}
