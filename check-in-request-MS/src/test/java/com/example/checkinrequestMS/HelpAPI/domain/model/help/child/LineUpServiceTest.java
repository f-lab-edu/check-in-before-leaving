package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
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
        LineUp lineUp = LineUp.createForTest();
        when(lineUpRepository.save(any(LineUp.class))).thenReturn(lineUp);

        //when
        LineUp.DTO result = lineUpService.register(dto);

        //then
        assertEquals(lineUp.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + LineUp.LINE_UP_TITLE, result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(Progress.DEFAULT.getStatus(), result.getStatus());
        assertEquals(Progress.DEFAULT.getHelperId(), result.getHelperId());
        assertEquals(Progress.DEFAULT.getPhotoPath(), result.getPhotoPath());
        assertEquals(Progress.DEFAULT.isCompleted(), result.isCompleted());
        
    }

}