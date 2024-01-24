package com.auction.biddingService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin.NewTopics topics456() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("newRoom")
                        .build(),
                TopicBuilder.name("endBidNotice")
                        .build(),
                TopicBuilder.name("newBidNotice")
                        .build());
    }
}
