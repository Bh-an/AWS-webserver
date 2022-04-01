#!/bin/bash

sudo systemctl stop csye6225app.service

backup=${PWD##*/}
sudo mv ~/javaapp/webserver-0.0.1-SNAPSHOT.jar ~/backups/$backup.jar

