#!/bin/bash
scp ./bill.service root@bill.carl-jung.top:/opt/bill
scp ./install-update.sh root@bill.carl-jung.top:/opt/bill
scp -r ../target/lib root@bill.carl-jung.top:/opt/bill
scp ../config/config.json.tmpl root@bill.carl-jung.top:/opt/bill/config
scp ../config/logback.xml.tmpl root@bill.carl-jung.top:/opt/bill/config
scp ./start.sh root@bill.carl-jung.top:/opt/bill
scp ../target/Bill-1.0-SNAPSHOT.jar root@bill.carl-jung.top:/opt/bill
scp -r ../../bill-react/build root@bill.carl-jung.top:/opt/bill

ssh root@bill.carl-jung.top chmod +x /opt/bill/install-update.sh
ssh root@bill.carl-jung.top sh /opt/bill/install-update.sh
