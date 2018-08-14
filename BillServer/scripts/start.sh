#!/bin/bash
echo "before start bill"
pushd /opt/bill
java -server -jar Bill-1.0-SNAPSHOT.jar > ./start.log
popd
echo "after start bill"




