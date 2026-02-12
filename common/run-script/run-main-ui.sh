#!/bin/bash
#######################################################################################################
# write by kimdy
#######################################################################################################
#######################################################################################################

JAVA_HOME="/usr"

BOARD_VERION=2.0.0

PRODUCTTYPE="prod"
SERVERPORT=64380

TARGETROOT="/data/board"
TARGETDIR="$TARGETROOT"

LOGPATH="$TARGETDIR/logs"

BOARD_OPTS="$BOARD_OPTS -Dspring.profiles.active=$PRODUCTTYPE"
BOARD_OPTS="$BOARD_OPTS -Dlogging.dir=$LOGPATH"

BOARD_OPTS="$BOARD_OPTS -Dserver.port=$SERVERPORT"

PROGID="board-main-ui"
PROGNAME="BOARD MAIN UI"

PROC_PATTERN="$PROGID-$BOARD_VERION.jar"
#######################################################################################################
#####
#######################################################################################################
function start() {
	echo "Start Board Main UI"
       	cd $TARGETDIR
        $JAVA_HOME/bin/java $BOARD_OPTS -jar $TARGETDIR/$PROC_PATTERN &
}

function stop() {
	echo "Stop Board Main UI"
	PID=`pgrep -f $PROC_PATTERN`
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