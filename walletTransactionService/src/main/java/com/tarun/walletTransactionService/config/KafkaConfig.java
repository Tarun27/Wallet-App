package com.tarun.walletTransactionService.config;

import com.tarun.walletTransactionService.dto.TransactionStatusEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // --- Producer Configuration ---
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> pConfig = new HashMap<>();
        pConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        pConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Optional: disable type info headers so consumers rely solely on the default type in configuration
        pConfig.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(pConfig);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // --- Consumer Configuration for TransactionStatusEvent ---
    @Bean
    public DefaultKafkaConsumerFactory<String, TransactionStatusEvent> transactionStatusConsumerFactory() {
        Map<String, Object> cConfig = new HashMap<>();
        cConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        cConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "transaction-group");
        cConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        cConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        // Use the correct fully qualified class name for TransactionStatusEvent:
        cConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TransactionStatusEvent.class.getName());
        // Instruct the deserializer to ignore type headers and use the configured default type
        cConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(cConfig);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionStatusEvent> transactionStatusKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionStatusEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transactionStatusConsumerFactory());
        return factory;
    }
}
