#!/bin/bash

TARGETDIR="/data/board"
UI_FILE="board-main-ui-1.0.0.jar"

LOGDIR="data/board/logs"
LOG_FILE="board-main-ui_`date +'%Y%m%d%H%M%S_%N'`"

BOARD_OPTS="$BOARD_OPTS -Dserver.port=7080"


#########################################################################
function start() {
	echo "Start Board UI"
       	cd $TARGETDIR
        /usr/bin/java $BOARD_OPTS -jar $TARGETDIR/$UI_FILE > $LOGDIR/$LOG_FILE &
	tail -f $LOGDIR/$LOG_FILE
}

function stop() {
	echo "Stop Board UI"
	PID=`pgrep -f $UI_FILE`
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
