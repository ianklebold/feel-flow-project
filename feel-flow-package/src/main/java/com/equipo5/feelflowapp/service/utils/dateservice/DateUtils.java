package com.equipo5.feelflowapp.service.utils.dateservice;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static boolean isAfterToToday(Timestamp date){
        ZonedDateTime today = ZonedDateTime.now( ZoneId.of( "America/Argentina/Buenos_Aires" ) );
        return today.isAfter( getDateFormatedToZoneDateTime(date) );
    }

    public static boolean isBeforeToToday(Timestamp date){
        ZonedDateTime today = ZonedDateTime.now( ZoneId.of( "America/Argentina/Buenos_Aires" ) );
        return today.toLocalDateTime().isBefore( getDateFormatedToZoneDateTime(date).toLocalDateTime() );
    }

    public static boolean isEqualToToday(Timestamp date){
        ZonedDateTime today = ZonedDateTime.now( ZoneId.of( "America/Argentina/Buenos_Aires" ) );
        return today.isEqual( getDateFormatedToZoneDateTime(date) );
    }

    private static ZonedDateTime  getDateFormatedToZoneDateTime(Timestamp date){
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
    }

    public static boolean isBeforeToOtherDate(Timestamp date, Timestamp otherDate){
        ZonedDateTime zonedDateTime = getDateFormatedToZoneDateTime(date);

        ZonedDateTime otherZonedDateTime;
        if( otherDate == null ){
            otherZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));

        }else{
            otherZonedDateTime = getDateFormatedToZoneDateTime(otherDate);
        }
        return zonedDateTime.isBefore(otherZonedDateTime);

    }

    public static boolean isEqualOrMoreThanOneDayAgo(Timestamp dateTime) {
        ZonedDateTime zonedDateTime = getDateFormatedToZoneDateTime(dateTime);
        ZonedDateTime now = ZonedDateTime.now( ZoneId.of( "America/Argentina/Buenos_Aires" ) );
        long daysBetween = ChronoUnit.DAYS.between(zonedDateTime, now);
        return daysBetween >= 1;
    }


}
