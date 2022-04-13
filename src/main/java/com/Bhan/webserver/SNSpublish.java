package com.Bhan.webserver;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SNSpublish {
    private static final Logger logger = LoggerFactory.getLogger(SNSpublish.class);

    private final AmazonSNS amazonSNS;
    private final Appconfig appconfig;

    public void publishSNSmessage(String message) {

        logger.info("Published SNS message: " + message);
        PublishResult result = amazonSNS.publish(appconfig.getSnstopic(), message);
    }

}
