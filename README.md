
## Webservice for image storage app hosted on AWS

Prerequisites

    JDK 1.8
    Maven
    Spring Boot
    MySQL 8.0

Run `mvn spring-boot:run`

AMI packer

Run `packer build packer/ami.json` 

**Note: This app is designed to run as a loadbalanced and autoclaled service on AWS infra. Cloudformation templates for supporting infra can be found on this [repo](https://github.com/Bh-an/IAC-webserver-cloudformation).
