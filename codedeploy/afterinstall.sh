#!/bin/bash

sudo rm -rf /home/ec2-user/javaapp/webserver-0.0.1-SNAPSHOT.jar

sudo chown ec2-user /home/ec2-user/webserver-0.0.1-SNAPSHOT.jar

sudo mv /home/ec2-user/webserver-0.0.1-SNAPSHOT.jar /home/ec2-user/javaapp/webserver-0.0.1-SNAPSHOT.jar
