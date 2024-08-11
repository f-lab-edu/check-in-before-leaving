package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
public class CheckInSelectServiceTest_Integrated {

    @Autowired
    CheckInSelectService sut;

     @Test
    void selectCheckIn() {
         //given
         Long id = 2L; //ID 확인해야함. Auto Increment로 되어있어 바뀜.

         //when
         CheckIn checkIn = sut.selectCheckIn(id);

         System.out.println(checkIn.getId());
         System.out.println(checkIn.getMemberId());
         System.out.println(checkIn.getTitle());
     }

}
