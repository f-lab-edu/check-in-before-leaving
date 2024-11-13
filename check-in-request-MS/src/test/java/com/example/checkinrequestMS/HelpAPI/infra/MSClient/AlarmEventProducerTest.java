package com.example.checkinrequestMS.HelpAPI.infra.MSClient;

import com.example.checkinrequestMS.HelpAPI.domain.model.alarm.HelpAlarmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlarmEventProducerTest {

    @Mock
    KafkaTemplate<Long, String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    AlarmEventProducer eventProducer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(eventProducer, "topic", "alarm-events");
    }

    @Test
    void sendLibraryEvent() throws JsonProcessingException, ExecutionException, InterruptedException {

        //given
        Long helpAlarmId = 123L;
        MSClient.AlarmForm alarmForm = new MSClient.AlarmForm(1.0, 2.0, "title", "message");
        HelpAlarmEvent libraryEvent = new HelpAlarmEvent(helpAlarmId, alarmForm);
        String record = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Long, String> producerRecord = new ProducerRecord("library-events", libraryEvent.helpRegisterId(), record);
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("library-events", 1),
                1, 1, System.currentTimeMillis(), 1, 2);

        SendResult<Long, String> sendResult = new SendResult<Long, String>(producerRecord, recordMetadata);

        var future = CompletableFuture.supplyAsync(() -> sendResult);

        when(kafkaTemplate.send(isA(ProducerRecord.class))).
                thenReturn(future);
        //when
        var completableFuture = eventProducer.sendLibraryEvent(libraryEvent);

        //then
        SendResult<Long, String> sendResult1 = completableFuture.get();
        assert sendResult1.getRecordMetadata().
                partition() == 1;
    }

}