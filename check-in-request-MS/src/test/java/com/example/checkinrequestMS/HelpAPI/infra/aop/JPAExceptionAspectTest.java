package com.example.checkinrequestMS.HelpAPI.infra.aop;

import com.example.checkinrequestMS.HelpAPI.infra.exceptions.jpa.JPAException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.HelpJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.progress.ProgressJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JPAExceptionAspectTest {

    @Autowired
    private ProgressJPARepository progressJPARepository;

    @Autowired
    private HelpJPARepository helpJPARepository;

    @Test
    void save() {
        Exception exception = assertThrows(JPAException.class, () -> progressJPARepository.save(null));

        assertEquals(JPAException.class, exception.getClass());
        assertEquals("JPA 저장 작업 중 에러", exception.getMessage());
    }

    @Test
    void findById() {

            Exception exception = assertThrows(JPAException.class, () -> helpJPARepository.findById(null));

            assertEquals(JPAException.class, exception.getClass());
            assertEquals("JPA 조회 작업 중 에러", exception.getMessage());
    }
}