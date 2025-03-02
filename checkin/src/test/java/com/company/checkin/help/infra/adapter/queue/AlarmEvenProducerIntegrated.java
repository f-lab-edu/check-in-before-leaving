package com.company.checkin.help.infra.adapter.queue;


import com.company.checkin.help.application.alarm.AlarmService;
import com.company.checkin.help.domain.model.alarm.HelpAlarmEvent;
import com.company.checkin.help.infra.adapter.rest.okhttp.MSClient;
import com.company.checkin.place.domain.Place;
import com.company.checkin.place.web.restAPI.SearchCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"alarm-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
@RequiredArgsConstructor
@Disabled
public class AlarmEvenProducerIntegrated {

    private final TestRestTemplate restTemplate;

    private final EmbeddedKafkaBroker embeddedKafkaBroker;


    private final ObjectMapper objectMapper;

    @Autowired
    AlarmService alarmService;

    private Consumer<Integer, String> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumer = new DefaultKafkaConsumerFactory<>(configs, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void getLibraryEvent() {
        //given

        //when

        //then
    }


    @Test
    void postLibraryEvent() throws JsonProcessingException, InterruptedException {
        //given

        HelpAlarmEvent helpAlarmEvent = new HelpAlarmEvent(123L, new MSClient.AlarmForm(1.0, 2.0, "title", "message"));
        Place place = Place.builder()
                .id(1L)
                .category(SearchCategory.FD6)
                .placeName("test")
                .roadAddressName("test")
                .phone("test")
                .x(1.0)
                .y(1.0)
                .build();
        alarmService.sendAlarmToUsersNearby(1L, place);

        Thread.sleep(1000);
        ConsumerRecords<Integer, String> consumerRecords = KafkaTestUtils.getRecords(consumer);

        System.out.println("result");
        System.out.println(consumerRecords.count());
        assert consumerRecords.count() == 1;

//        consumerRecords.forEach(record -> {
//            HelpAlarmEvent libraryEventActual = null;
//            try {
//                libraryEventActual = objectMapper.readValue(record.value(), HelpAlarmEvent.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            assertEquals(helpAlarmEvent, libraryEventActual);
//        });


    }
}