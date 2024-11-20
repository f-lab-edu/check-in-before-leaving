package com.membercontext.memberAPI.infrastructure.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AlarmEventConsumer {

    @KafkaListener(topics = {"alarm-events"}
            , groupId = "alarm-events-listener-group")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("ConsumerRecord : {}", consumerRecord);
    }
}
