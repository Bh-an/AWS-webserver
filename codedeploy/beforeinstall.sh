#!/bin/bash

sudo systemctl stop csye6225app.service

[ ! -f /home/ec2-user/javaapp/webserver-0.0.1-SNAPSHOT.jar ] || sudo mv /home/ec2-user/javaapp/webserver-0.0.1-SNAPSHOT.jar /home/ec2-user/backups/$(date +"%Y_%m_%d_%I_%M_%p").jar

