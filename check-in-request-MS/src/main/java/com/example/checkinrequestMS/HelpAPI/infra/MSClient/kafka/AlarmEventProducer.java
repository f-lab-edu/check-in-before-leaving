package com.example.checkinrequestMS.HelpAPI.infra.MSClient.kafka;

import com.example.checkinrequestMS.HelpAPI.domain.model.alarm.HelpAlarmEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventProducer {

    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // @Value("${spring.kafka.topic}")
    public String topic;

    public CompletableFuture<SendResult<Long, String>> sendAlarmEvent(HelpAlarmEvent helpAlarmEvent) {
        try {
            Long key = helpAlarmEvent.helpRegisterId();
            String value = objectMapper.writeValueAsString(helpAlarmEvent);

            ProducerRecord<Long, String> producerRecord = buildProducerRecord(key, value, topic);
            var completableFuture = kafkaTemplate.send(producerRecord);
            return completableFuture
                    .whenComplete((sendResult, throwable) -> {
                        if (throwable != null) {
                            handleFailure(throwable);
                        } else {
                            handleSuccess(key, value, sendResult);
                        }
                    });
        } catch (Exception e) {
            handleFailure(e);
        }
        return null;
    }

    private ProducerRecord<Long, String> buildProducerRecord(Long key, String value, String topic) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<Long, String>(topic, null, key, value, recordHeaders);
    }

    private void handleFailure(Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
    }

    private void handleSuccess(Long key, String value, SendResult<Long, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }


}
