#!/bin/sh

# build_everything.sh
# - a script that updates everything (images/web interfaces)
# - and builds a runnable jar + a Windows Installer for it

##### Constants

IMG_DIR=ImageResources
IMG_UPDATE_FILE=create_image_ressources.py

WEB_DIR=WebInterfaces
WEB_UPDATE_FILE=create_website_page_resources.py

JAVA_DIR=DesktopClient

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
	mvn clean install
	cd ../
}

# update/build the main Java project runnable jar with dependencies
build_win_installer()
{
	echo "Build Windows Installer:"
	cd $WIN_INST_DIR
	python $WIN_INST_UPDATE_FILE
	cd ../
}


##### Main

echo "Welcome to the automatic build of the whole MusicVideoManager :D"
echo "-----------------------------------------------------------------"

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

build_project

for i in "$@" ; do
	if [[ $i == "win" ]]
	then
		build_win_installer
	fi
done

read -n1 -r -p "Press any key to continue..." key