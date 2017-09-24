#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
Build windows installer for the exported jar file
'''

import os
import subprocess
import sys
from xml.dom import minidom

COMMAND_NSIS = "makensis"
SCRIPT_FILE = "create_windows_installer.nsi"
XML_FILE = r"../DesktopClient/pom.xml"

XML_TREE = minidom.parse(XML_FILE)

# doc.getElementsByTagName returns NodeList
ARTIFACT_ID = XML_TREE.getElementsByTagName("artifactId")[0].firstChild.data
VERSION = XML_TREE.getElementsByTagName("version")[0].firstChild.data

INSTALLER_FILE_NAME = r"../" + ARTIFACT_ID + ".jar"
CORRECT_FINAL_FILE_NAME = r"../" + ARTIFACT_ID + "-portable-" + VERSION + ".jar"

if os.path.isfile(CORRECT_FINAL_FILE_NAME):
    os.rename(CORRECT_FINAL_FILE_NAME, INSTALLER_FILE_NAME)
    print(CORRECT_FINAL_FILE_NAME + " renamed to " + INSTALLER_FILE_NAME + " for NSIS script")
elif os.path.isfile(INSTALLER_FILE_NAME):
    print("The filename for the installer already exists")
else:
    print("No .jar file to create installer from found! -> Script will be stopped!")
    sys.exit()


PRO = subprocess.Popen([COMMAND_NSIS, SCRIPT_FILE], stdout=subprocess.PIPE)
while PRO.poll() is None:
    print('', end='')

# print out the resulting code
print(PRO.communicate()[0].decode("utf-8"))

# rename jar file back to the "correct" portable name:
if os.path.isfile(INSTALLER_FILE_NAME):
    # check if the correct filename exists
    if os.path.isfile(CORRECT_FINAL_FILE_NAME):
        # remove this 'old' file then
        os.remove(CORRECT_FINAL_FILE_NAME)
    # and rename the just used jar to the portable name
    os.rename(INSTALLER_FILE_NAME, CORRECT_FINAL_FILE_NAME)
    print(INSTALLER_FILE_NAME + " renamed back to correct file name: " + CORRECT_FINAL_FILE_NAME)

# rename the created installer to specify the correct version and use case
INSTALLER_NAME = r"../" + ARTIFACT_ID + "_windows_installer.exe"
CORRECT_INSTALLER_NAME = r"../" + ARTIFACT_ID + "-win-installer-" + VERSION + ".exe"

if os.path.isfile(INSTALLER_NAME):
    if os.path.isfile(CORRECT_INSTALLER_NAME):
        os.remove(CORRECT_INSTALLER_NAME)
    os.rename(INSTALLER_NAME, CORRECT_INSTALLER_NAME)
    print(INSTALLER_NAME + " renamed to " + CORRECT_INSTALLER_NAME)
    print("- \"" + SCRIPT_FILE + "\" was executed an built the windows installler \"" +
      "" + CORRECT_INSTALLER_NAME + "\"")
else:
    print("Something went wrong!!!!!")
