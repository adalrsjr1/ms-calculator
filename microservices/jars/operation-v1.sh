#!/bin/sh
java -Dport=$1 -Dmspackage="com.github.adalrsjr1.ms_calculator.operations.v1.$2" -jar microservice.jar
