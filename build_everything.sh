#!/bin/sh

# build_everything.sh
# - a script that updates everything (images/web interfaces)
# - and builds a runnable jar + a Windows Installer for it


##### Constants

IMG_DIR=ImageResources
IMG_UPDATE_FILE=create_image_resources.py

WEB_DIR=WebInterfaces
WEB_UPDATE_FILE=create_website_resources.py

JAVA_DIR=DesktopClient
JAVA_UPDATE_FILE=build_project.sh

WIN_INST_DIR=WindowsInstaller
WIN_INST_UPDATE_FILE=build_windows_installer.py


##### Functions

# update/create all images
update_images()
{
    echo "Update/Create all images and icons: (wait some seconds)"
    cd $IMG_DIR
    python $IMG_UPDATE_FILE
    cd ../
}

# update/create all web interfaces
update_web_interfaces()
{
    echo "Update/Create all web interfaces:"
    cd $WEB_DIR
    python $WEB_UPDATE_FILE
    cd ../
}

# update/build the main Java project runnable jar with dependencies
build_project()
{
    echo "Build \"portable\" runnable jar:"
    cd $JAVA_DIR
    . $JAVA_UPDATE_FILE do_nothing
    cd ../
}

# update/build a windows installer for the runnable jar
build_win_installer()
{
    echo "hi"
    echo "Build Windows Installer:"
    cd $WIN_INST_DIR
    python $WIN_INST_UPDATE_FILE
    cd ../
}


##### Main

echo "Welcome to the automatic build of the whole MusicVideoManager :D"
echo "-----------------------------------------------------------------"

# Check if one argument is "img" or "web"
for i in "$@" ; do
    if [[ $i == "img" ]]
    then
        update_images
    fi
    if [[ $i == "web" ]]
    then
        update_web_interfaces
    fi
done

# Always build the runnable jar
build_project

# Check if one argument is "win"
for i in "$@" ; do
    if [[ $i == "win" ]]
    then
        build_win_installer
    fi
done

# Do not close the window -> In case of bugs/failures show the history of everything => Debugging
read -n1 -r -p "Press any key to continue..." key
