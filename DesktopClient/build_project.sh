#!/bin/sh

# build_project.sh
# - a script that builds a runnable jar


##### Constants

RENAME_FILE_NAME=format_exported_jar.py


##### Main

echo "Build runnable jar:"

# build the runnable jar with maven
mvn clean install

echo "Rename runnable jar:"

# rename the exported file to another one
python $RENAME_FILE_NAME

# Do not close the window when the script get's started without any argument (with a double click)
if [ $# -eq 0 ] 
then
    read -n1 -r -p "Press any key to continue..." key
fi
