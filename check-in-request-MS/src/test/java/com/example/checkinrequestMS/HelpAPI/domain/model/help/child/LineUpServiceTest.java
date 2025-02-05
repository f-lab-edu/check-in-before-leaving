package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineUpServiceTest {

    @InjectMocks
    private LineUpService lineUpService;

    @Mock
    private LineUpRepository lineUpRepository;

    @Mock
    private AlarmService alarmService;


    public LineUpServiceTest() {
        // LocalValidatorFactoryBean을 사용해 @Validated 동작 활성화
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        ProxyFactory proxyFactory = new ProxyFactory(new LineUpService(lineUpRepository, alarmService));
        proxyFactory.addAdvice(new MethodValidationInterceptor(validatorFactory));
        this.lineUpService = (LineUpService) proxyFactory.getProxy();
    }



    @Test
    @DisplayName("체크인 등록 성공")
    void register() {
        //given
        LineUpService.Registration dto = LineUpService.Registration.createForTest();
        when(lineUpRepository.save(any(LineUp.class))).thenReturn(1L);

        //when
        Long id = lineUpService.register(dto);

        //then
        assertEquals(1L, id);
    }

    @Nested
    @SpringBootTest
    class hi {
        @Test
        void hi(){
            lineUpService.register(null);
        }

    }

}