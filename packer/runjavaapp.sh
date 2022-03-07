#!/bin/bash

java -Dexternal.sys.properties=file:system.properties 
-Dexternal.app.properties=file:application.properties -jar your-application.jar

,    
        {
            "type": "shell",
            "scripts": [
            "installsql.sh"
            ]
        }
         {
            "type":        "file",
            "source":      "webserver-0.0.1-SNAPSHOT.jar",
            "destination": "/home/ec2-user/webserver-0.0.1-SNAPSHOT.jar"
         }