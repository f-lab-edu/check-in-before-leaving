package com.membercontext.memberAPI.infrastructure.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.infrastructure.kafka.consumer.record.HelpAlarmEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AlarmEventConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = {"alarm-events"}
            , groupId = "alarm-events-listener-group")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("ConsumerRecord : {}", consumerRecord);
        try {
            HelpAlarmEvent helpAlarmEvent = objectMapper.readValue(consumerRecord.value(), HelpAlarmEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
