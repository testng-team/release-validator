#!/usr/bin/env bash

mvn eclipse:eclipse  -Dversion=$1 -Dcurrent=$1 -Dprevious=$2 clean test
