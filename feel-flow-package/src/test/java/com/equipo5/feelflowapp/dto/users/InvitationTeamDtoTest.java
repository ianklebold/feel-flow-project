package com.equipo5.feelflowapp.dto.users;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static com.equipo5.feelflowapp.service.users.invitation.impl.InvitationServiceImpl.DAYS_OF_EXPIRATION;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class InvitationTeamDtoTest {

    @Test
    void test_date_is_not_expired(){
        Date d1 = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, DAYS_OF_EXPIRATION);

        log.info("Date of today " + d1);
        log.info("Date of expiration " + calendar.getTime());

        assertThat(d1.compareTo(calendar.getTime()) >= 0).isFalse();
    }

    @Test
    void test_date_is_expired(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, DAYS_OF_EXPIRATION);

        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        calendar2.add(Calendar.DAY_OF_YEAR, DAYS_OF_EXPIRATION);
        Date d1 = calendar2.getTime();


        log.info("Date of today " + d1);
        log.info("Date of expiration " + calendar.getTime());

        assertThat(d1.compareTo(calendar.getTime()) >= 0).isTrue();
    }

    @Test
    void test_date_is_expired_2(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, DAYS_OF_EXPIRATION);

        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        calendar2.add(Calendar.DAY_OF_YEAR, DAYS_OF_EXPIRATION);
        Date d1 = calendar2.getTime();


        log.info("Date of today " + d1);
        log.info("Date of expiration " + calendar.getTime());

        assertThat(d1.compareTo(calendar.getTime()) >= 0).isTrue();
    }


}
