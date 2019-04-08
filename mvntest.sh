#!/usr/bin/env bash

mvn eclipse:eclipse  -Dcurrent=$1 -Dprevious=$2 clean test
