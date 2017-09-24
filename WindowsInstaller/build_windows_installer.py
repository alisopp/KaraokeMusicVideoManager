#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
Build windows installer for the exported jar file
'''

import os
import subprocess
import sys
from xml.dom import minidom


# command for running NSIS makensis
COMMAND_NSIS = "makensis"
# path to NSIS script
SCRIPT_FILE = "create_windows_installer.nsi"

# path to pom.xml file where version and name of project are saved
XML_FILE = r"../DesktopClient/pom.xml"


# pares the xml file
XML_TREE = minidom.parse(XML_FILE)

# extract from xml file name and version
ARTIFACT_ID = XML_TREE.getElementsByTagName("artifactId")[0].firstChild.data
VERSION = XML_TREE.getElementsByTagName("version")[0].firstChild.data

# specify the name of the jar file for the NSIS script and the final name
INSTALLER_FILE_NAME = r"../" + ARTIFACT_ID + ".jar"
CORRECT_FINAL_FILE_NAME = r"../" + ARTIFACT_ID + "-portable-" + VERSION + ".jar"

# Change the file name of the jar if it isn't the one the NSIS script needs
if os.path.isfile(CORRECT_FINAL_FILE_NAME):
    os.rename(CORRECT_FINAL_FILE_NAME, INSTALLER_FILE_NAME)
    print(CORRECT_FINAL_FILE_NAME + " renamed to " +
          INSTALLER_FILE_NAME + " for NSIS script")
elif os.path.isfile(INSTALLER_FILE_NAME):
    print("The filename for the installer already exists")
else:
    print("No .jar file to create installer from found! -> Script will be stopped!")
    sys.exit()


# run the NSIS script to create the Windows installer
PRO = subprocess.Popen([COMMAND_NSIS, SCRIPT_FILE], stdout=subprocess.PIPE)
# wait till the installer was built
while PRO.poll() is None:
    print('', end='')
# print out the output of the command (debugging)
print(PRO.communicate()[0].decode("utf-8"))


# rename the runnable jar back to the "correct" portable name:
if os.path.isfile(INSTALLER_FILE_NAME):
    # check if the correct filename exists
    if os.path.isfile(CORRECT_FINAL_FILE_NAME):
        # remove this 'old' file then
        os.remove(CORRECT_FINAL_FILE_NAME)
    # and rename the just used jar to the portable name
    os.rename(INSTALLER_FILE_NAME, CORRECT_FINAL_FILE_NAME)
    print(INSTALLER_FILE_NAME +
          " renamed back to correct file name: " + CORRECT_FINAL_FILE_NAME)

# rename the created installer to specify the correct version and use case
INSTALLER_NAME = r"../" + ARTIFACT_ID + "_windows_installer.exe"
CORRECT_INSTALLER_NAME = r"../" + ARTIFACT_ID + \
    "-win-installer-" + VERSION + ".exe"

# rename the installer executable to include the version number:
if os.path.isfile(INSTALLER_NAME):
    # check if a file with the correct filename already exists
    if os.path.isfile(CORRECT_INSTALLER_NAME):
        # if yes remove this file before renaming
        os.remove(CORRECT_INSTALLER_NAME)
    os.rename(INSTALLER_NAME, CORRECT_INSTALLER_NAME)
    print(INSTALLER_NAME + " renamed to " + CORRECT_INSTALLER_NAME)
    print("- \"" + SCRIPT_FILE + "\" was executed an built the windows installler \"" +
          "" + CORRECT_INSTALLER_NAME + "\"")
else:
    # if no installer file was found point that out
    print("Something went wrong!!!!!")
