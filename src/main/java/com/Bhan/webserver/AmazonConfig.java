package com.Bhan.webserver;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Bean
    public AmazonS3 amazonS3() {
        InstanceProfileCredentialsProvider provider
                = new InstanceProfileCredentialsProvider(true);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(provider)
                .build();

    }
    @Bean
    public AmazonSNS amazonSNS(){
        InstanceProfileCredentialsProvider snsprovider
                = new InstanceProfileCredentialsProvider(true);
        return AmazonSNSClientBuilder.standard()
                .withCredentials(snsprovider)
                .withRegion("us-east-1")
                .build();
    }
}