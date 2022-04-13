package com.Bhan.webserver;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Tokenrepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void saveToken(Unverifieduser user) {
        dynamoDBMapper.save(user);
    }

    public Unverifieduser getToken(String uname) {
        return dynamoDBMapper.load(Unverifieduser.class, uname);
    }
}

//    public String deleteCustomerById(String customerId) {
//        dynamoDBMapper.delete(dynamoDBMapper.load(Customer.class, customerId));
//        return "Customer Id : "+ customerId +" Deleted!";
//    }
