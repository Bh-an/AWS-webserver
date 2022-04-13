//package com.Bhan.webserver;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.InstanceProfileCredentialsProvider;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DynamoDBConfiguration {
//
//    @Value("${aws.dynamodb.endpoint}")
//    private String dynamodbEndpoint;
//
//    @Bean
//    public DynamoDBMapper dynamoDBMapper() {
//        return new DynamoDBMapper(buildAmazonDynamoDB());
//    }
//
//    private AmazonDynamoDB buildAmazonDynamoDB() {
//
//        InstanceProfileCredentialsProvider dbprovider
//                = new InstanceProfileCredentialsProvider(true);
//
//        return AmazonDynamoDBClientBuilder
//                .standard()
//                .withEndpointConfiguration(
//                        new AwsClientBuilder.EndpointConfiguration(dynamodbEndpoint,"us-east-1"))
//                .withCredentials(dbprovider)
//                .build();
//    }
//}