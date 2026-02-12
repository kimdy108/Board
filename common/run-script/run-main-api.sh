#!/bin/bash
#######################################################################################################
# write by kimdy
#######################################################################################################
#######################################################################################################

JAVA_HOME="/usr"

BOARD_VERION=2.0.0

PRODUCTTYPE="prod"
SERVERPORT=64280

TARGETROOT="/data/board"
TARGETDIR="$TARGETROOT"

LOGPATH="$TARGETDIR/logs"

MIN_HEAP_SIZE="2G"
MAX_HEAP_SIZE="2G"

BOARD_OPTS="$BOARD_OPTS -Xms$MIN_HEAP_SIZE -Xmx$MAX_HEAP_SIZE"

BOARD_OPTS="$BOARD_OPTS -Dspring.profiles.active=$PRODUCTTYPE"
BOARD_OPTS="$BOARD_OPTS -Dspring.jpa.hibernate.ddl-auto=none"
BOARD_OPTS="$BOARD_OPTS -Dlogging.dir=$LOGPATH"

BOARD_OPTS="$BOARD_OPTS -Dserver.port=$SERVERPORT"

PROGID="board-main-api"
PROGNAME="BOARD MAIN API"

PROC_PATTERN="$PROGID-$BOARD_VERION.jar"
#######################################################################################################
#####
#######################################################################################################
function start() {
	echo "Start Board Main API"
       	cd $TARGETDIR
        $JAVA_HOME/bin/java $BOARD_OPTS -jar $TARGETDIR/$PROC_PATTERN &
}

function stop() {
	echo "Stop Board Main API"
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