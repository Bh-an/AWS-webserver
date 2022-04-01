#!/bin/bash

sudo systemctl stop csye6225app.service

backup=${PWD##*/}
sudo mv /home/ec2-user/javaapp/webserver-0.0.1-SNAPSHOT.jar /home/ec2-user/backups/$backup.jar

