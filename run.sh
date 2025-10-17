#! /bin/bash

if [ -z "$1" ]; then
  sbt "run 10"
else
  sbt "run $1"
fi


