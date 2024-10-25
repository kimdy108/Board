#!/bin/bash

TARGETDIR="/data/board"
API_FILE="board-main-api-1.0.0.jar"

LOGDIR="data/board/logs"
LOG_FILE="board-main-api_`date +'%Y%m%d%H%M%S_%N'`"

BOARD_OPTS="$BOARD_OPTS -Dspring.jpa.hibernate.ddl-auto=none"
BOARD_OPTS="$BOARD_OPTS -Dserver.port=7777"


#########################################################################
function start() {
	echo "Start Board API"
       	cd $TARGETDIR
        /usr/bin/java $BOARD_OPTS -jar $TARGETDIR/$API_FILE > $LOGDIR/$LOG_FILE &
	tail -f $LOGDIR/$LOG_FILE
}

function stop() {
	echo "Stop Board API"
	PID=`pgrep -f $API_FILE`
        kill $PID
}

case $1 in
stop)
        stop
;;
start)
        start
;;
restart)
        stop
        start
;;
*)
        echo "Parameter is (start,stop,restart)"
;;
esac
