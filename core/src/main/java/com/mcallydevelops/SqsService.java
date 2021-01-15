package com.mcallydevelops;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqsService {

    private final AmazonSQS sqs;
    private final ObjectMapper objectMapper;

    public SqsService(AmazonSQS sqs, ObjectMapper objectMapper) {
        this.sqs = sqs;
        this.objectMapper = objectMapper;
    }

    public void create() {
        CreateQueueRequest create_request = new CreateQueueRequest("sample_queue")
                .addAttributesEntry("DelaySeconds", "60")
                .addAttributesEntry("MessageRetentionPeriod", "86400");

        try {
            sqs.createQueue(create_request);
            log.error("create queue successful");
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
    }

    public void send(NumberRow i) throws JsonProcessingException {
        String messageToSend = objectMapper.writeValueAsString(i);
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(getQueueUrl())
                .withMessageBody(messageToSend)
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);
    }

    public String getQueueUrl() {
        return sqs.getQueueUrl("sample_queue").getQueueUrl();
    }

    public void delete() {
        String url = getQueueUrl();
        sqs.deleteQueue(url);
    }
}
