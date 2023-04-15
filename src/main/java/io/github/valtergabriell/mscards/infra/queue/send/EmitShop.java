package io.github.valtergabriell.mscards.infra.queue.send;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mscards.application.domain.dto.BuyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmitShop {
    private final RabbitTemplate rabbitTemplate;
    private final Queue createCardQueue;

    public void requestCard(BuyRequest buyRequest) throws JsonProcessingException {
        try {
            var json = convertToJsonString(buyRequest);
            rabbitTemplate.convertAndSend(createCardQueue.getName(), json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private String convertToJsonString(BuyRequest buyRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(buyRequest);
    }

}
