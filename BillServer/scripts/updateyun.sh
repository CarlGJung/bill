#!/bin/bash
scp -r ../target/lib root@bill.carl-jung.top:/opt/bill
scp -r ../config root@bill.carl-jung.top:/opt/bill
scp ./start.sh root@bill.carl-jung.top:/opt/bill
scp ../target/Bill-1.0-SNAPSHOT.jar root@bill.carl-jung.top:/opt/bill

