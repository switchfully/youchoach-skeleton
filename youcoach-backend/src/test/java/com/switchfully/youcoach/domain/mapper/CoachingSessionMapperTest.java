package com.switchfully.youcoach.domain.mapper;

import com.switchfully.youcoach.datastore.Status;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CoachingSessionMapperTest {
    private final CoachingSessionMapper coachingSessionMapper = new CoachingSessionMapper();

    private User getDefaultUser() {
        return new User(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin","/my/photo.png");
    }

    @Test
    void toCoachingSessionModel(){
        CreateCoachingSessionDto ccsd = new CreateCoachingSessionDto("subject","01/05/2020","13:01","Cafeteria","Bring food", 1L);
        User coach = getDefaultUser();
        User coachee = getDefaultUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse("01/05/2020 13:01", dateTimeFormatter);

        CoachingSession expected = new CoachingSession("subject", dateTime,"Cafeteria","Bring food",coach, coachee, Status.REQUESTED);
        CoachingSession actual = coachingSessionMapper.toModel(ccsd,coach, coachee);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toCoachingSessionDto(){
        CreateCoachingSessionDto ccsd = new CreateCoachingSessionDto("subject","01/05/2020","13:01","Cafeteria","Bring food", 1L);
        User coach = getDefaultUser();
        User coachee = getDefaultUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse("01/05/2020 13:01", dateTimeFormatter);

        CoachingSession input = new CoachingSession("subject", dateTime,"Cafeteria","Bring food",coach, coachee, Status.REQUESTED);
        CoachingSessionDto expected = new CoachingSessionDto(0L,ccsd.getSubject(),ccsd.getDate(),ccsd.getTime(),ccsd.getLocation(),
                ccsd.getRemarks(),new CoachingSessionDto.Person(1L, "First Last"),
                new CoachingSessionDto.Person(1L,"First Last"),Status.REQUESTED);

        CoachingSessionDto actual = coachingSessionMapper.toDto(input);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
    @Test
    void toListCoachingSessionDto(){
        CreateCoachingSessionDto ccsd = new CreateCoachingSessionDto("subject","01/05/2020","13:01","Cafeteria","Bring food", 1L);
        User coach = getDefaultUser();
        User coachee = getDefaultUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse("01/05/2020 13:01", dateTimeFormatter);

        List<CoachingSession> input = List.of(new CoachingSession("subject", dateTime,"Cafeteria",
                "Bring food",coach, coachee, Status.REQUESTED));
        List<CoachingSessionDto> expected = List.of(new CoachingSessionDto(0L,ccsd.getSubject(),ccsd.getDate(),ccsd.getTime(),ccsd.getLocation(),
                ccsd.getRemarks(),new CoachingSessionDto.Person(1L, "First Last"),
                new CoachingSessionDto.Person(1L,"First Last"),Status.REQUESTED));

        List<CoachingSessionDto> actual = coachingSessionMapper.toDto(input);

        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void extractPerson(){
        CoachingSessionDto.Person expected = new CoachingSessionDto.Person(1L, "First Last");
        CoachingSessionDto.Person actual = coachingSessionMapper.extractPerson(getDefaultUser());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
