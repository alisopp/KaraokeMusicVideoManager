echo "Welcome to the automatic build of the whole MusicVideoManager :D"
echo "-----------------------------------------------------------------"
echo "Update Images:"
cd ImageResources
python create_image_ressources.py
cd ../
echo "Update Web Interfaces:"
cd WebInterfaces
python create_website_page_resources.py
cd ../
cd DesktopClient
echo "Building jars:"
mvn clean install
echo "-- created \"..\\MusicVideoManager-x.x.x.jar\""
echo "-- created \"..\\MusicVideoManager-x.x.x-jar-with-dependencies.jar\""
cd ../
echo "Build Windows Installer:"
cd WindowsInstaller
python build_windows_installer.py
cd ../
read -n1 -r -p "Press any key to continue..." key