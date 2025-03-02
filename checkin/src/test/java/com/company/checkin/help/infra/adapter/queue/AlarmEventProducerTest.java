package com.company.checkin.help.infra.adapter.queue;

import com.company.checkin.help.infra.adapter.queue.kafka.AlarmEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
@Disabled
class AlarmEventProducerTest {

    @Mock
    KafkaTemplate<Long, String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    AlarmEventProducer eventProducer;

    @BeforeEach
    void setUp() {
        //   ReflectionTestUtils.setField(eventProducer, "topic", "alarm-events");
    }

    @Test
    void sendAlarmEvent() throws JsonProcessingException, ExecutionException, InterruptedException {
//
//        //given
//        Long helpAlarmId = 123L;
//        MSClient.AlarmForm alarmForm = new MSClient.AlarmForm(1.0, 2.0, "title", "message");
//        HelpAlarmEvent alarmEvent = new HelpAlarmEvent(helpAlarmId, alarmForm);
//        String record = objectMapper.writeValueAsString(alarmEvent);
//
//        ProducerRecord<Long, String> producerRecord = new ProducerRecord("alarm-events", alarmEvent.helpRegisterId(), record);
//        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("alarm-events", 1),
//                1, 1, System.currentTimeMillis(), 1, 2);
//
//        SendResult<Long, String> sendResult = new SendResult<Long, String>(producerRecord, recordMetadata);
//
//        var future = CompletableFuture.supplyAsync(() -> sendResult);
//
//        when(kafkaTemplate.send(isA(ProducerRecord.class))).
//                thenReturn(future);
//        //when
//        var completableFuture = eventProducer.sendAlarmEvent(alarmEvent);
//
//        //then
//        completableFuture.thenApply(result -> (SendResult<Long, String>) result)
//                .thenAccept(result -> {
//                    assert result.getRecordMetadata().
//                            partition() == 1;
//                    System.out.println("Received result: " + sendResult);
//                })
//                .exceptionally(ex -> {
//                    System.err.println("Error occurred: " + ex.getMessage());
//                    return null;
//                });
//    }

    }
}