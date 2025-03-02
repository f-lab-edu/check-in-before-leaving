package com.company.checkin.help.infra.adapter.queue.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.topic}")
    public String topic;

    //  @Bean
    public NewTopic libraryEvents() {
        return TopicBuilder.name(topic)
                .partitions(3)
                .replicas(3)
                .build();
    }
}
