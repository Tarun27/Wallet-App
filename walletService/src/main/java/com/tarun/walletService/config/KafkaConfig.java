package com.tarun.walletService.config;

import com.tarun.walletService.dto.CreditRequestEvent;
import com.tarun.walletService.dto.DebitRequestEvent;
import com.tarun.walletService.dto.RollbackEvent;
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

    // ====================== PRODUCER CONFIGURATION ======================

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Disable type information headers so consumers don't depend on header-based type info
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // ====================== CONSUMER CONFIGURATION (PROPERTY-BASED ONLY) ======================

    /**
     * Returns consumer properties that set the expected target type via configuration only.
     */
    private Map<String, Object> consumerProps(Class<?> targetClass) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Use the same group for all wallet service consumers (adjust if needed)
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "wallet-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Tell Kafka to use JsonDeserializer (it will instantiate its own instance)
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        // Set the expected type solely via configuration (do not use a constructor)
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, targetClass.getName());
        // Instruct the deserializer to ignore any __TypeId__ headers
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return props;
    }

    /**
     * Create a consumer factory for the given target type using property-based configuration only.
     */
    @Bean
    public DefaultKafkaConsumerFactory<String, DebitRequestEvent> debitRequestConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(DebitRequestEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DebitRequestEvent> debitRequestKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DebitRequestEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(debitRequestConsumerFactory());
        return factory;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, CreditRequestEvent> creditRequestConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(CreditRequestEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreditRequestEvent> creditRequestKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CreditRequestEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(creditRequestConsumerFactory());
        return factory;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, RollbackEvent> rollbackConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(RollbackEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RollbackEvent> rollbackKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RollbackEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rollbackConsumerFactory());
        return factory;
    }
}
