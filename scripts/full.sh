#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/build.sh
if [ $? -ne 0 ]; then
    echo "--------------------------"
    echo ""
    echo "Build failed"
fi


$DIR/copy-to-pi.sh
if [ $? -ne 0 ]; then
    echo "--------------------------"
    echo ""
    echo "Deploy to remote host failed"
fi
