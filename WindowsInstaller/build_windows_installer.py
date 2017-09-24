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

EXPORTED_FILE_PATH = r"../" + ARTIFACT_ID + \
    "-" + VERSION + "-jar-with-dependencies.jar"
RENAMED_FILE_PATH = r"../" + ARTIFACT_ID + ".jar"
RENAMED_FILE_PATH_2 = r"../" + ARTIFACT_ID + "-portable-" + VERSION + ".jar"
if os.path.isfile(EXPORTED_FILE_PATH):
    if os.path.isfile(RENAMED_FILE_PATH):
        os.remove(RENAMED_FILE_PATH)
    os.rename(EXPORTED_FILE_PATH, RENAMED_FILE_PATH)
    print(EXPORTED_FILE_PATH + " renamed to " + RENAMED_FILE_PATH)
elif os.path.isfile(RENAMED_FILE_PATH_2):
    os.rename(RENAMED_FILE_PATH_2, RENAMED_FILE_PATH)
    print(RENAMED_FILE_PATH_2 + " renamed to " + RENAMED_FILE_PATH)
else:
    print("No .jar file to create installer from found!")
    sys.exit()


PRO = subprocess.Popen([COMMAND_NSIS, SCRIPT_FILE], stdout=subprocess.PIPE)
while PRO.poll() is None:
    print('', end='')

# print out the resulting code
print(PRO.communicate()[0].decode("utf-8"))

if os.path.isfile(RENAMED_FILE_PATH):
    if os.path.isfile(RENAMED_FILE_PATH_2):
        os.remove(RENAMED_FILE_PATH_2)
    os.rename(RENAMED_FILE_PATH, RENAMED_FILE_PATH_2)
    print(RENAMED_FILE_PATH + " renamed to " + RENAMED_FILE_PATH_2)

INSTALLER_NAME = r"../" + ARTIFACT_ID + "_windows_installer.exe"
RENAME_INSTALLER = r"../" + ARTIFACT_ID + "-win-installer-" + VERSION + ".exe"

if os.path.isfile(INSTALLER_NAME):
    if os.path.isfile(RENAME_INSTALLER):
        os.remove(RENAME_INSTALLER)
    os.rename(INSTALLER_NAME, RENAME_INSTALLER)
    print(INSTALLER_NAME + " renamed to " + RENAME_INSTALLER)
    print("- \"" + SCRIPT_FILE + "\" was executed an built the windows installler \"" +
      "" + RENAME_INSTALLER + "\"")
else:
    print("Something went wrong!!!!!")
