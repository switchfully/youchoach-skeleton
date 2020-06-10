package com.switchfully.youcoach.session.api;

import com.switchfully.youcoach.session.Status;
import com.switchfully.youcoach.session.Session;
import com.switchfully.youcoach.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SessionMapperTest {
    private final SessionMapper sessionMapper = new SessionMapper();

    private Member getDefaultUser() {
        return new Member(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin","/my/photo.png");
    }

    @Test
    void toCoachingSessionModel(){
        CreateSessionDto ccsd = new CreateSessionDto("subject","01/05/2020","13:01","Cafeteria","Bring food", 1L);
        Member coach = getDefaultUser();
        Member coachee = getDefaultUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse("01/05/2020 13:01", dateTimeFormatter);

        Session expected = new Session("subject", dateTime,"Cafeteria","Bring food",coach, coachee, Status.REQUESTED);
        Session actual = sessionMapper.toModel(ccsd,coach, coachee);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toCoachingSessionDto(){
        CreateSessionDto ccsd = new CreateSessionDto("subject","01/05/2020","13:01","Cafeteria","Bring food", 1L);
        Member coach = getDefaultUser();
        Member coachee = getDefaultUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse("01/05/2020 13:01", dateTimeFormatter);

        Session input = new Session("subject", dateTime,"Cafeteria","Bring food",coach, coachee, Status.REQUESTED);
        SessionDto expected = new SessionDto(0L,ccsd.getSubject(),ccsd.getDate(),ccsd.getTime(),ccsd.getLocation(),
                ccsd.getRemarks(),new SessionDto.Person(1L, "First Last"),
                new SessionDto.Person(1L,"First Last"),Status.REQUESTED);

        SessionDto actual = sessionMapper.toDto(input);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
    @Test
    void toListCoachingSessionDto(){
        CreateSessionDto ccsd = new CreateSessionDto("subject","01/05/2020","13:01","Cafeteria","Bring food", 1L);
        Member coach = getDefaultUser();
        Member coachee = getDefaultUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse("01/05/2020 13:01", dateTimeFormatter);

        List<Session> input = List.of(new Session("subject", dateTime,"Cafeteria",
                "Bring food",coach, coachee, Status.REQUESTED));
        List<SessionDto> expected = List.of(new SessionDto(0L,ccsd.getSubject(),ccsd.getDate(),ccsd.getTime(),ccsd.getLocation(),
                ccsd.getRemarks(),new SessionDto.Person(1L, "First Last"),
                new SessionDto.Person(1L,"First Last"),Status.REQUESTED));

        List<SessionDto> actual = sessionMapper.toDto(input);

        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void extractPerson(){
        SessionDto.Person expected = new SessionDto.Person(1L, "First Last");
        SessionDto.Person actual = sessionMapper.extractPerson(getDefaultUser());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
