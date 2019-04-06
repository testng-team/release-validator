#!/usr/bin/env bash

mvn -Dversion=$1 versions:compare-dependencies -DremotePom=org.testng:testng:$2 dependency:resolve -Dclassifier=javadoc dependency:sources clean test
