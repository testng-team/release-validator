#!/usr/bin/env bash

mvn dependency:resolve -Dclassifier=javadoc dependency:sources  -Dcurrent=$1 -Dprevious=$2 clean test
