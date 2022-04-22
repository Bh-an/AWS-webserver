#!/bin/bash

java -Djavax.net.ssl.trustStore=/home/ec2-user/javaapp/mytruststore.jks -Djavax.net.ssl.trustStorePassword=hesoyam -Djavax.net.ssl.keyStore=/home/ec2-user/javaapp/mytruststore.jks -Djavax.net.ssl.keyStorePassword=hesoyam -jar webserver-0.0.1-SNAPSHOT.jar