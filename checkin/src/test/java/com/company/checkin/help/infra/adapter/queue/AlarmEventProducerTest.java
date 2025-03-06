package com.company.checkin.help.infra.adapter.queue;

import com.company.checkin.help.domain.model.alarm.HelpAlarmEvent;
import com.company.checkin.help.infra.adapter.queue.kafka.AlarmEventProducer;
import com.company.checkin.help.infra.adapter.rest.okhttp.MSClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
class AlarmEventProducerTest {

    @Mock
    KafkaTemplate<Long, String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    AlarmEventProducer eventProducer;

    @Test
    void sendAlarmEvent() throws JsonProcessingException {

        //given
        Long helpAlarmId = 123L;
        MSClient.AlarmForm alarmForm = new MSClient.AlarmForm(1.0, 2.0, "title", "message");
        HelpAlarmEvent alarmEvent = new HelpAlarmEvent(helpAlarmId, alarmForm);
        String data = objectMapper.writeValueAsString(alarmEvent);

        ProducerRecord<Long, String> producerRecord = new ProducerRecord("alarm-events", alarmEvent.helpRegisterId(), data);
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("alarm-events", 1),
                1, 1, System.currentTimeMillis(), 1, 2);

        SendResult<Long, String> sendResult = new SendResult<Long, String>(producerRecord, recordMetadata);

        var future = CompletableFuture.supplyAsync(() -> sendResult);

        when(kafkaTemplate.send(isA(ProducerRecord.class))).
                thenReturn(future);
        //when
        var completableFuture = eventProducer.sendAlarmEvent(alarmEvent);

        //then
        completableFuture.thenApply(result -> (SendResult<Long, String>) result)
                .thenAccept(result -> {
                    assertEquals(1L, result.getRecordMetadata().partition());
                    System.out.println("Received result: " + sendResult);
                })
                .exceptionally(ex -> {
                    System.err.println("Error occurred: " + ex.getMessage());
                    return null;
                });
    }


}