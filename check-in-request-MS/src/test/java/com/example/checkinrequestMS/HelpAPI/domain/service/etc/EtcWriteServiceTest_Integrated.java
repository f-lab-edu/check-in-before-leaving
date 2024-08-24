package com.example.checkinrequestMS.HelpAPI.domain.service.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

@SpringBootTest
@Disabled
public class EtcWriteServiceTest_Integrated {

    @Autowired
    private EtcWriteService sut;

    @Autowired
    private PlaceJPARepository placeRepository;

    @Test
    void registerEtc() {
        //given
        Place place = mock(Place.class);
        placeRepository.save(place);
        //Place Name API에서 바로 저장해서 지금은 null
        //Id 는 auto increment로 되어있어서 바뀜.

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);
        Etc etcToRegister =
                Etc.builder()
                        .memberId(1L)
                        .title(null)
                        .place(null)
                        .progress(null)
                        .start(now)
                        .end(oneHourLater)
                        .reward(100L).build();

        //when
        sut.registerEtc(etcToRegister, 1L);
    }
}
