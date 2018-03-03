#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PDIR="$(pwd)"

cd $DIR/..
mvn package
RES=$?
cd $PDIR

exit $RES
