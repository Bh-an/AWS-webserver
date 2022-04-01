#!/bin/bash

sudo rm -rf ~/javaapp/webserver-0.0.1-SNAPSHOT.jar

sudo chown ec2-user ~/webserver-0.0.1-SNAPSHOT.jar

sudo mv ~/webserver-0.0.1-SNAPSHOT.jar ~/javaapp/webserver-0.0.1-SNAPSHOT.jar
