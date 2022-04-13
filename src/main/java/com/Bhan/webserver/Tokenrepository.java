package com.Bhan.webserver;

import com.amazonaws.services.glue.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class Tokenrepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbenhancedClient ;

    // Store the order item in the database
    public void savetoken(Unverifieduser uvuser) {
        DynamoDbTable<Unverifieduser> uvusertable = getTable();
        uvusertable.putItem(uvuser);
    }

    // Retrieve a single order item from the database
    public Unverifieduser gettoken( String username) {
        DynamoDbTable<Unverifieduser> uvusertable = getTable();
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(username)
                .build();

        Unverifieduser uvuser = uvusertable.getItem(key);

        return uvuser;
    }

    private DynamoDbTable<Unverifieduser> getTable() {
        // Create a tablescheme to scan our bean class order
        DynamoDbTable<Unverifieduser> uvusertable =
                dynamoDbenhancedClient.table("Unverifieduser",
                        TableSchema.fromBean(Unverifieduser.class));
        return uvusertable;
    }

}


//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class Tokenrepository {
//
//    @Autowired
//    private DynamoDBMapper dynamoDBMapper;
//
//    public void saveToken(Unverifieduser user) {
//        dynamoDBMapper.save(user);
//    }
//
//    public Unverifieduser getToken(String uname) {
//        return dynamoDBMapper.load(Unverifieduser.class, uname);
//    }
//}
//
////    public String deleteCustomerById(String customerId) {
////        dynamoDBMapper.delete(dynamoDBMapper.load(Customer.class, customerId));
////        return "Customer Id : "+ customerId +" Deleted!";
////    }
