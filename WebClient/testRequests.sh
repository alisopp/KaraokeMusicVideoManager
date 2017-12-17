#!/bin/sh


while read command; do
	COMMAND="$command"
	COMMAND=${COMMAND%$'\r'}
	echo ">> ${command}"
    curl -s -X $COMMAND
	echo -e "\n"
done < testCommands.txt
