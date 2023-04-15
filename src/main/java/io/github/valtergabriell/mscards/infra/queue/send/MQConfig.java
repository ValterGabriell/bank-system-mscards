package io.github.valtergabriell.mscards.infra.queue.send;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Value("${mq.queues.shopping}")
    private String queue;

    @Bean
    public Queue createQueue() {
        return new Queue(queue, true);
    }
}
