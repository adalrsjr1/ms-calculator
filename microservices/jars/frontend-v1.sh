#!/bin/bash
java -Ddst_host="localhost" -Ddst_port=8880 -Dport=$1 -Dmspackage="com.github.adalrsjr1.ms_calculator.frontend.v1" -jar microservice.jar
