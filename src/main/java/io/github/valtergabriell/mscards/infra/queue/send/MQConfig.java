package io.github.valtergabriell.mscards.infra.queue.send;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public Queue createQueue() {
        String queue = "shopping-queue";
        return new Queue(queue, true);
    }
}
