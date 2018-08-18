#!/bin/bash
pushd /opt/bill

logFile=./install-update.log
if test -e $logFile;then
    echo "" > $logFile
else
    touch $logFile
fi

function log(){
    echo $1 >> $logFile
}

function isFileDiff(){
    diff $1 $2 > /dev/null
    if [ $? == 0 ] ;then
        return 1
    else
        return 0
    fi
}

newServiceFile=./bill.service
oldServiceFile=/usr/lib/systemd/system/bill.service
if isFileDiff $newServiceFile $oldServiceFile; then
    log "bill.service is change, update it";
    cp $newServiceFile $oldServiceFile
    
    if isFileDiff $newServiceFile $oldServiceFile; then
        log "update bill.service fail"
    else 
    	systemctl daemon-reload
    fi
fi

serverConfig=./config/config.json
serverConfigTmpl=./config/config.json.tmpl
if ! test -e $serverConfig;then
    if test -e $serverConfigTmpl;then
        cp serverConfigTmpl serverConfig
        log "copy config.json.tmpl to config.json"
    else
        log "can not find config.json.tmpl"
    fi
fi
service bill restart
log "execute service bill restart"
popd
exit 0;
