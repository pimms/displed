#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JAR="displed-1.0-SNAPSHOT.jar"
JARPATH="$DIR/../target/$JAR"

REMHOST="raspberrypi"
REMUSER="$(whoami)"

if [ ! -f $JARPATH ]; then
    echo "Could not find file '$JARPATH'."
    exit 1
fi

scp $JARPATH $REMUSER@$REMHOST:~/
exit $?
