package com.company.checkin.help.domain.jpa_version;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@SpringBootTest
@Disabled //check: 통합이라 잠시 끔. 다시 작동 예정.
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
class CheckInJPATest {


    private final CheckInJPASpringJPARepository db;

    @Test
    void save() {
        CheckInJPA.DTO dto =
                CheckInJPA.DTO.builder()
                        .id(1200L)
                        .start(LocalDateTime.now())
                        .end(LocalDateTime.now().plusMinutes(20))
                        .title("gogo")
                        .placeId("pl")
                        .reward(100L)
                        .status(new ProgressJPA.Created())
                        .photoPath(null)
                        .helpRegisterId(1L)
                        .helperId(null)
                        .completed(false)
                        .build();
        CheckInJPA jpa = CheckInJPA.from(dto);


        Long id = jpa.getId();
        Long id2 = jpa.getId();
        System.out.println(id == id2);

        db.save(jpa);

        assertEquals(jpa, db.findById(jpa.getId()).get());
    }

}