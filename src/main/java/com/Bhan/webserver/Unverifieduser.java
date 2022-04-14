package com.Bhan.webserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
public class Unverifieduser {
    private String username;
    private String token;
    private long expire;

    public Unverifieduser(String userName, String Token, long timeStamp) {
        this.username = userName;
        this.token = Token;
        this.expire = timeStamp;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("username")
    public String getusername() {return this.username;}
    public void setusername(String username) {this.username = username;}

    @DynamoDbAttribute("token")
    public String gettoken() {
        return this.token;
    }
    public void settoken(String token) {this.token=token;}

    @DynamoDbAttribute("expire")
    public long getexpire() {
        return this.expire;
    }
    public void setexpire(long expire) {
        this.expire = expire;
    }
}


//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@DynamoDBTable(tableName = "Users")
//public class Unverifieduser {
//
//    @DynamoDBHashKey
//    private String username;
//
//    @DynamoDBAttribute
//    private String token;
//
//    @DynamoDBAttribute
//    private long expire;
//}
